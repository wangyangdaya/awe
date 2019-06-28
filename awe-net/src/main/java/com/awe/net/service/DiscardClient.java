package com.awe.net.service;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * description
 *
 * @author wangy QQ 837195190
 * <p>Created by admin on 2018/7/20.</p>
 */
public class DiscardClient {

    private static final Logger logger = LoggerFactory.getLogger(DiscardClient.class);

    public void connect(String host, int port) {

        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workGroup).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new MessageDecoder(1 << 20, 10, 4))
                                    .addLast(new MessageEncoder())
                                    .addLast(new clientHandler())
                                    /**
                                     * readerIdleTime 是指在这个参数指定的时间内若没有发生read事件，则发送一条信息维持心跳。0表示禁用该功能
                                     * writerIdleTime 是指在这个参数指定的时间内若没有发生write事件，则发送一条信息维持心跳。0表示禁用该功能
                                     * allIdleTime是指在这个参数指定的时间内若没有发生write或者read事件,则发送一条信息维持心跳。0表示禁用该功能
                                     * TimeUnit是指前面三个变量的时间单位
                                     */
                                    .addLast(new IdleStateHandler(0, 0, 10, TimeUnit.SECONDS));
//                            .addLast(new LineBasedFrameDecoder(1024))
//                            .addLast(new StringDecoder())
//                            .addLast(new StringEncoder())
                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.awaitUninterruptibly(2000, TimeUnit.MILLISECONDS);
//            if (future.channel().isActive()) {
//                for (int i = 0; i < 100; i++) {
//                    String body = "Hello world from client:" + i;
//                    Message msg = new Message((byte) 0XAF, (byte) 0XBF, i, body);
//                    future.channel().writeAndFlush(msg);
//                }
//            }
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        DiscardClient discardClient = new DiscardClient();
        discardClient.connect("127.0.0.1", 8889);
    }

    class clientHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
//            logger.info("clientHandler.channelActive");
//            String msg = "Are you ok?";
//            ByteBuf encoded = ctx.alloc().buffer(4 * msg.length());
//            encoded.writeBytes(msg.getBytes());
            for (int i = 0; i < 100; i++) {
                String body = "Hello world from client:" + i;
                Message msg = new Message((byte) 0XAF, (byte) 0XBF, i, body);
                ctx.writeAndFlush(msg);
            }
//            String body = "Are you ok";
//            Message message = new Message((byte) 0XAF, (byte) 0XBF, 1, body);
//            ctx.writeAndFlush(message);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (msg instanceof Message) {
                System.out.println(String.format("Server said: %s", ((Message) msg).getBody()));
            } else {
                logger.info("clientHandler.channelRead");
                ByteBuf result = (ByteBuf) msg;
                byte[] result1 = new byte[result.readableBytes()];
                result.readBytes(result1);
                result.release();
                ctx.close();
                System.out.println("Server said:" + new String(result1));
            }

        }
    }
}
