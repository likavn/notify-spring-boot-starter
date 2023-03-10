package com.github.likavn.notify.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.lang.Nullable;

import java.util.Objects;
import java.util.Properties;

/**
 * SpringUtil
 *
 * @author likavn
 * @since 2023/01/01
 **/
public class SpringUtil implements ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(SpringUtil.class);
    /**
     * 应用ID
     */
    private static String serviceId;

    /**
     * ctx
     */
    private static ApplicationContext context;

    @Override
    @SuppressWarnings("all")
    public void setApplicationContext(@Nullable ApplicationContext context) throws BeansException {
        SpringUtil.context = context;
    }

    public static <T> T getBean(Class<T> clazz) {
        return clazz == null ? null : context.getBean(clazz);
    }

    @SuppressWarnings("all")
    public static <T> T getBean(String beanId) {
        return beanId == null ? null : (T) context.getBean(beanId);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        if (null != beanName && !"".equals(beanName.trim())) {
            return clazz == null ? null : context.getBean(beanName, clazz);
        } else {
            return null;
        }
    }

    public static ApplicationContext getContext() {
        return context == null ? null : context;
    }

    public static void publishEvent(ApplicationEvent event) {
        if (context != null) {
            try {
                context.publishEvent(event);
            } catch (Exception var2) {
                log.error(var2.getMessage());
            }

        }
    }

    /**
     * 获取应用名称
     */
    public static String getServiceId() {
        if (Objects.nonNull(serviceId)) {
            return serviceId;
        }
        Properties props = System.getProperties();
        serviceId = props.getProperty("spring.application.name");
        if (null == serviceId || serviceId.isEmpty()) {
            serviceId = props.getProperty("sun.java.command");
        }
        return serviceId;
    }
}
