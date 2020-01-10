package org.abapi.pay.examples.service.impl;

import org.abapi.pay.examples.service.AliPayBackService;
import org.springframework.stereotype.Service;

/**
 * @Author ldx
 * @Date 2019/11/1 16:53
 * @Description
 * @Version 1.0.0
 */
@Service
public class AliPayBackServiceImpl implements AliPayBackService {

    @Override
    public void back() {
        System.out.println("------------ali back-----------");
    }
}
