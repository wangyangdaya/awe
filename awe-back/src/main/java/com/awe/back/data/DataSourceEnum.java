package com.awe.back.data;

/**
 * description
 *
 * @author wangy QQ 837195190
 * <p>Created by admin on 2018/7/26.</p>
 */
public enum DataSourceEnum {

    WRITE("write"), READ("read");

    private String value;

    DataSourceEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
