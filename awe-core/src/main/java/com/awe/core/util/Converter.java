package com.awe.core.util;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.GenericConversionService;

/**
 * description 类型转换
 *
 * @author wangy QQ 837195190
 * <p>Created by DELL-5490 on 2018/6/11.</p>
 */

public class Converter implements ConversionService {


    private GenericConversionService genericConversionService;



    @Override
    public boolean canConvert(Class<?> sourceType, Class<?> targetType) {
        return false;
    }

    @Override
    public boolean canConvert(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return false;
    }

    @Override
    public <T> T convert(Object source, Class<T> targetType) {
        return genericConversionService.convert(source, targetType);
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return null;
    }
}
