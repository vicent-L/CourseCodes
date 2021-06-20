package com.lzg.multi.datasource.aop;

import com.lzg.multi.datasource.annotation.SwitchDataSource;
import com.lzg.multi.datasource.config.DataSourceName;
import com.lzg.multi.datasource.config.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author lzg
 * @Date 2021-06-20 16:17
 */
@Aspect
@Component
@Slf4j
public class DataSourceAspect implements Ordered {

    @Pointcut("@annotation(com.lzg.multi.datasource.annotation.SwitchDataSource)")
    public void dataSourcePointCut() {

    }

    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        SwitchDataSource ds = method.getAnnotation(SwitchDataSource.class);
        if (ds == null) {
            DynamicDataSource.setDataSource(DataSourceName.MASTER);
        } else {
            DynamicDataSource.setDataSource(ds.name());
        }

        try {
            return point.proceed();
        } finally {
            DynamicDataSource.clearDataSource();
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}

