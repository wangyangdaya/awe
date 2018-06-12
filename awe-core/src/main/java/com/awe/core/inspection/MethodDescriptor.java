package com.awe.core.inspection;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description 方法描述
 *
 * @author wangy QQ 837195190
 * <p>Created by DELL-5490 on 2018/6/9.</p>
 */
public class MethodDescriptor {

    protected final Class<?> cls;
    private final Method method;
    private final String methodName;
    private final Class<?>[] paramTypes;
    private final String[] paramNames;

    /**
     * services 方法 service method paramTypes
     */
    public static final Map<String, ConcurrentHashMap<String, List<Class<?>[]>>> SERVICES = new ConcurrentHashMap<>();

    /**
     * services 方法 service method MethodDescriptor
     */
    public static final Map<String, ConcurrentHashMap<String, List<MethodDescriptor>>> SERVICES_DESCRIPTOR = new ConcurrentHashMap<>();


    public MethodDescriptor(Method method) {
        if (method == null) {
            throw new IllegalArgumentException("Method cannot be null");
        }
        this.cls = method.getDeclaringClass();
        this.method = method;
        this.methodName = method.getName();
        this.paramTypes = method.getParameterTypes();
        ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] parameters = parameterNameDiscoverer.getParameterNames(method);
        this.paramNames = parameters;
    }

    /**
     * 维护相应方法声明
     * @param beanName 服务名
     * @param service 方法名
     * @param paramTypes 方法参数
     */
    public static void addMethod(String beanName, String service, Class<?>[] paramTypes) {
        ConcurrentHashMap<String, List<Class<?>[]>> services = SERVICES.get(beanName);
        if (Objects.isNull(services)) {
            services = new ConcurrentHashMap<>();
            SERVICES.put(beanName, services);
        }

        List<Class<?>[]> types = services.get(service);
        if (Objects.isNull(types)) {
            types = new ArrayList<>();
            types.add(paramTypes);
            services.put(service, types);
        } else {
            // 不可能方法完全相同
            for (Class<?>[] clazz : types) {
                // 方法完全相同
                if (Arrays.equals(clazz, paramTypes)) {
                    return;
                }
            }
            types.add(paramTypes);
        }
    }

    /**
     * 维护相应方法声明
     * @param beanName 服务名
     * @param service 方法名
     * @param methodDescriptor 方法描述
     */
    public static void addMethod(String beanName, String service, MethodDescriptor methodDescriptor) {
        ConcurrentHashMap<String, List<MethodDescriptor>> services = SERVICES_DESCRIPTOR.get(beanName);
        if (Objects.isNull(services)) {
            services = new ConcurrentHashMap<>();
            SERVICES_DESCRIPTOR.put(beanName, services);
        }

        List<MethodDescriptor> methodDescriptors = services.get(service);
        if (Objects.isNull(methodDescriptors)) {
            methodDescriptors = new ArrayList<>();
            methodDescriptors.add(methodDescriptor);
            services.put(service, methodDescriptors);
        } else {
            methodDescriptors.add(methodDescriptor);
        }
    }

    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    public String[] getParamNames() {
        return paramNames;
    }

    public Method getMethod() {
        return method;
    }
}
