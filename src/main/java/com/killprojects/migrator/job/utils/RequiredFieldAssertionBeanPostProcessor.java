package com.killprojects.migrator.job.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class RequiredFieldAssertionBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof RequiredFieldAssertable) {
            ((RequiredFieldAssertable) bean).checkRequiredFields();
        }
        return bean;
    }
}
