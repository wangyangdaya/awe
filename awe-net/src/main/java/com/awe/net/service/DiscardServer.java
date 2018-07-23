package com.awe.net.service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description netty demo.
 *
 * @author wangy QQ 837195190
 * <p>Created by DELL-5490 on 2018/7/11.</p>
 */
public class DiscardServer {

    private static final Logger logger = LoggerFactory.getLogger(DiscardServer.class);

    public void start(int port) {
        // Group：群组，Loop：循环，Event：事件，这几个东西联在一起，相比大家也大概明白它的用途了。
        // Netty内部都是通过线程在处理各种数据，EventLoopGroup就是用来管理调度他们的，注册Channel，管理他们的生命周期。
        // NioEventLoopGroup是一个处理I/O操作的多线程事件循环
        // bossGroup作为boss,接收传入连接
        // 因为bossGroup仅接收客户端连接，不做复杂的逻辑处理，为了尽可能减少资源的占用，取值越小越好
        // 用于处理服务器端接收客户端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // 进行网络通信（读写）
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        // 辅助工具类，用于服务器通道的一系列配置
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 绑定两个线程组
            serverBootstrap.group(bossGroup, workerGroup)
                    //指定NIO的模式
                    .channel(NioServerSocketChannel.class)
                    //配置具体的数据处理方式
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    //LineBasedFrameDecoder 解码器
//                                    .addLast(new LineBasedFrameDecoder(1024))
//                                    .addLast(new StringDecoder())
//                                    .addLast(new StringEncoder())
                                    .addLast(new MessageDecoder(1<<20, 10, 4))
                                    .addLast(new MessageEncoder())
                                    // 注册两个OutboundHandler，执行顺序为注册顺序的逆序，所以应该是OutboundHandler2 OutboundHandler1
                                    .addLast(new OutboundHandler1())
                                    .addLast(new OutboundHandler2())
                                    // 注册两个InboundHandler，执行顺序为注册顺序，所以应该是InboundHandler1 InboundHandler2
                                    .addLast(new InboundHandler1())
                                    .addLast(new InboundHandler2());
                        }
                    })
                    /**
                     * 对于ChannelOption.SO_BACKLOG的解释：
                     * 服务器端TCP内核维护有两个队列，我们称之为A、B队列。客户端向服务器端connect时，会发送带有SYN标志的包（第一次握手），服务器端
                     * 接收到客户端发送的SYN时，向客户端发送SYN ACK确认（第二次握手），此时TCP内核模块把客户端连接加入到A队列中，然后服务器接收到
                     * 客户端发送的ACK时（第三次握手），TCP内核模块把客户端连接从A队列移动到B队列，连接完成，应用程序的accept会返回。也就是说accept
                     * 从B队列中取出完成了三次握手的连接。
                     * A队列和B队列的长度之和就是backlog。当A、B队列的长度之和大于ChannelOption.SO_BACKLOG时，新的连接将会被TCP内核拒绝。
                     * 所以，如果backlog过小，可能会出现accept速度跟不上，A、B队列满了，导致新的客户端无法连接。要注意的是，backlog对程序支持的
                     * 连接数并无影响，backlog影响的只是还没有被accept取出的连接
                     */
                    // 设置TCP缓冲区
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 设置发送数据缓冲大小
//                    .option(ChannelOption.SO_SNDBUF, 32 * 1024)
                    // 设置接受数据缓冲大小
                    .option(ChannelOption.SO_RCVBUF, 32 * 1024)
                    // 保持连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        DiscardServer discardServer = new DiscardServer();
        discardServer.start(8889);
    }

    class InboundHandler1 extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            logger.info(String.format("InboundHandler1.channelRead: ctx: %s; msg: %s", ctx, msg));
            ctx.fireChannelRead(msg);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            logger.info("InboundHandler1.channelReadComplete");
            ctx.flush();
        }
    }

    class InboundHandler2 extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            logger.info("InboundHandler2.channelRead: ctx :" + ctx);
            if (msg instanceof Message) {
                Message resp = new Message(((Message) msg).getMagicType(), ((Message) msg).getType(),
                        ((Message) msg).getRequestId(), "Hello world from server");
                ctx.writeAndFlush(resp);

            } else {
                ByteBuf result = (ByteBuf) msg;
                byte[] result1 = new byte[result.readableBytes()];
                result.readBytes(result1);
                String resultStr = new String(result1);
                System.out.println("Client said:" + resultStr);
                result.release();
                ctx.write(msg);
            }

        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            logger.info("InboundHandler2.channelReadComplete");
            ctx.flush();
        }
    }

    class OutboundHandler1 extends ChannelOutboundHandlerAdapter {

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            if (msg instanceof Message) {
                Message resp = new Message(((Message) msg).getMagicType(), ((Message) msg).getType(),
                        ((Message) msg).getRequestId(), "Hello world from server");
                ctx.writeAndFlush(resp);
            } else {
                // 向client发送消息
                logger.info("OutboundHandler1.write");
                String response = "I am ok!";
                ByteBuf encoded = ctx.alloc().buffer(4 * response.length());
                encoded.writeBytes(response.getBytes());
                ctx.write(encoded);
                ctx.flush();
            }
        }
    }

    class OutboundHandler2 extends ChannelOutboundHandlerAdapter {

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            logger.info("OutboundHandler2.write");
            // 执行下一个OutboundHandler
            super.write(ctx, msg, promise);
        }
    }
}
