package com.mh.sviwo.gps.tcpser.jt808.mapping;

import cn.hutool.core.lang.ClassScaner;
import com.google.common.collect.Maps;
import com.mh.sviwo.gps.tcpser.jt808.annotation.EndpointMapping;

import java.util.Map;
import java.util.Set;

/**
 * @Author ldx
 * @Date 2019/10/18 11:21
 * @Description
 * @Version 1.0.0
 */
public class HandlerEndpointContext {

    public static Map<Integer,Class> handlerClassMap;

    static {
        Map<Integer, Class> handleMap = Maps.newHashMap();
        Set<Class<?>> classes = ClassScaner.scanPackageByAnnotation("com.mh.sviwo.gps.tcpser.jt808.service.endpoint.impl", EndpointMapping.class);
        classes.stream().forEach(e->{
            Integer type = e.getAnnotation(EndpointMapping.class).value();
            handleMap.put(type,e);
        });
        handlerClassMap = handleMap;
    }

}
