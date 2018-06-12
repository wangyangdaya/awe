package com.awe.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * description spring context utils
 *
 * @author wangy QQ 837195190
 * <p>Created by DELL-5490 on 2018/6/9.</p>
 */
public abstract class SpringUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(SpringUtils.class);

    /**
     * applicationContext
     */
    private static ApplicationContext applicationContext;

    private SpringUtils() {}

    /**
     * applicationContext
     * @param applicationContext
     * @throws BeansException
     */
    public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtils.applicationContext == null) {
            SpringUtils.applicationContext = applicationContext;
            LOGGER.info("load applicationContext.");
        }
    }

    /**
     * 通过name获取 Bean.
     * @param name beanName
     * @return bean
     * @throws BeansException
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * 通过class获取 Bean.
     * @param clazz beanName
     * @param <T> class
     * @return bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

}
