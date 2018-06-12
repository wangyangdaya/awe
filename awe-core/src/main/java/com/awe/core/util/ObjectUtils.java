package com.awe.core.util;

import org.springframework.core.convert.TypeDescriptor;

/**
 * description Object工具类
 *
 * @author wangy QQ 837195190
 * <p>Created by DELL-5490 on 2018/6/11.</p>
 */
public abstract class ObjectUtils {

    private ObjectUtils() {
    }

    /**
     * 方法参数类型转换
     * @param type   转换类型
     * @param object 目标对象
     * @return 转换后对象
     */
    public <T> T convert(Class<T> type, Object object) {
        if (null != type) {
            if (type.equals(short.class))
                return (T) Short.valueOf(object.toString());
            else if (type.equals(int.class))
                return (T) Integer.valueOf(object.toString());
            else if (type.equals(long.class))
                return (T) Long.valueOf(object.toString());
            else if (type.equals(float.class))
                return (T) Float.valueOf(object.toString());
            else if (type.equals(double.class))
                return (T) Double.valueOf(object.toString());
            else if (type.equals(byte.class))
                return (T) Byte.valueOf(object.toString());
            else if (type.equals(boolean.class))
                return (T) Boolean.valueOf(object.toString());
            else if (type.equals(char.class))
                return (T) Character.valueOf((char) Integer.parseInt(object.toString()));
            else if (CharSequence.class.isAssignableFrom(type))
                return (T) toString(object, null);
        }
        return null;
    }

    public static String toString(final Object obj, final String nullStr) {
        return obj == null ? nullStr : obj.toString();
    }
}
