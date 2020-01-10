package com.mh.sviwo.gps.tcpser.jt808.mapping;

import com.mh.sviwo.gps.tcpser.jt808.service.endpoint.AbstractEndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author ldx
 * @Date 2019/10/21 11:58
 * @Description
 * @Version 1.0.0
 */
@Component
public class HandlerEndpointFactory {

    @Autowired
    private final Map<String, AbstractEndpointService> handlerMap = new ConcurrentHashMap<>();

    public AbstractEndpointService  getInstance(String className){
        handlerMap.forEach((k,v)-> System.out.println(k));
        return handlerMap.get(className);
    }

}
