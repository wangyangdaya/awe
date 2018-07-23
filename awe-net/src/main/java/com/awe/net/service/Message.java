package com.awe.net.service;

import java.nio.charset.Charset;

/**
 * description
 *
 * @author wangy QQ 837195190
 * <p>Created by admin on 2018/7/20.</p>
 */
public class Message {

    private final Charset charset = Charset.forName("UTF-8");

    private byte magicType;

    /**
     * 消息类型 0xAF 表示心跳包 0xBF 表示超时包 0xCF 业务信息包
     */
    private byte type;

    /**
     * 请求id
     */
    private long requestId;

    private int length;

    private String body;

    public Message() {
    }

    public Message(byte magicType, byte type, long requestId, byte[] data) {
        this.magicType = magicType;
        this.type = type;
        this.requestId = requestId;
        this.length = data.length;
        this.body = new String(data, charset);
    }

    public Message(byte magicType, byte type, long requestId, String body) {
        this.magicType = magicType;
        this.type = type;
        this.requestId = requestId;
        this.length = body.getBytes(charset).length;
        this.body = body;
    }

    public Charset getCharset() {
        return charset;
    }

    public byte getMagicType() {
        return magicType;
    }

    public void setMagicType(byte magicType) {
        this.magicType = magicType;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
