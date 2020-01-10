package com.abapi.pay.annotation;

import com.abapi.pay.enums.PayType;

import java.lang.annotation.*;

/**
 * @author liu
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AbapiPayCallback {

    String backName();

    PayType payType();

}
