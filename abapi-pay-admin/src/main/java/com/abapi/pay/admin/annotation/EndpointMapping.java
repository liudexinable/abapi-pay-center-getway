package com.mh.sviwo.gps.tcpser.jt808.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EndpointMapping {

    int value();

    String desc() default "";

}