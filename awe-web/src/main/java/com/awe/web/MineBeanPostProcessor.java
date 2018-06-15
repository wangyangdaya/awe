package com.awe.web;

import com.awe.core.inspection.MethodDescriptor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Optional;

/**
 * description bean 后处理器
 *
 * @author wangy QQ 837195190
 * <p>Created by DELL-5490 on 2018/6/9.</p>
 */
@Component
public class MineBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        Class<?> clazz = bean.getClass();
        Optional.ofNullable(clazz.getAnnotation(Service.class)).ifPresent(annotation -> ReflectionUtils.doWithLocalMethods(bean.getClass(), method -> processMethod(beanName, method.getName(), method)));
        return bean;
    }

    private void processMethod(String beanName, String service, Method method) {
        // 只读取PUBLIC方法
        if (Modifier.isPublic(method.getModifiers())) {
            MethodDescriptor methodDescriptor = new MethodDescriptor(method);
            MethodDescriptor.addMethod(beanName, service, methodDescriptor);
        }
    }
}
