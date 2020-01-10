package org.abapi.pay.examples.handler;

import com.abapi.pay.annotation.AbapiPayCallback;
import com.abapi.pay.annotation.AbapiPayHandler;
import com.abapi.pay.enums.PayType;
import org.abapi.pay.examples.service.AliPayBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author ldx
 * @Date 2019/11/1 14:05
 * @Description
 * @Version 1.0.0
 */
@AbapiPayHandler
@Component
public class PayHandler {

    @Autowired
    private AliPayBackService aliPayBackService;

    @AbapiPayCallback(backName = "aliBack",payType = PayType.ALI)
    public void aliBack(){
        System.out.println("aliBack");
        aliPayBackService.back();
    }

    @AbapiPayCallback(backName = "wxBack",payType = PayType.WX)
    public void wxBack(){
        System.out.println("wxBack");
    }

}
