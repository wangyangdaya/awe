package com.awe.net.service;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * description
 *
 * @author wangy QQ 837195190
 * <p>Created by admin on 2018/7/20.</p>
 */
public class MessageDecoder extends LengthFieldBasedFrameDecoder{

    // 头部信息 byte+byte+long+int = 1+1+8+4 = 14
    private static final int HEADER_SIZE = 14;


    public MessageDecoder(int maxFrameLength,
                          int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (null == in) {
            return null;
        }

        if (in.readableBytes() <= HEADER_SIZE) {
            return null;
        }

        in.markReaderIndex();

        byte magic = in.readByte();
        byte type = in.readByte();

        long requestId = in.readLong();
        int dataLength = in.readInt();

        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return null;
        }

        byte[] data = new byte[dataLength];
        in.readBytes(data);

        String body = new String(data, "UTF-8");
        Message message = new Message(magic, type, requestId, body);
        return message;
    }
}
