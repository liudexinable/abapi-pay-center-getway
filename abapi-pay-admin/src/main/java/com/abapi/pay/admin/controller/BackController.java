package com.abapi.pay.admin.controller;

import cn.hutool.core.util.RandomUtil;
import com.abapi.cloud.common.protocol.Result;
import com.abapi.pay.admin.data.ClientData;
import com.abapi.pay.admin.data.ClientInfo;
import com.abapi.pay.biz.Encode;
import com.abapi.pay.constans.MessageType;
import com.abapi.pay.dto.AliBizDTO;
import com.abapi.pay.dto.MessageDTO;
import com.abapi.pay.enums.PayType;
import io.netty.buffer.ByteBuf;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author ldx
 * @Date 2019/11/1 15:59
 * @Description
 * @Version 1.0.0
 */
@RestController
@RequestMapping("pay/ali/back")
public class BackController {

    @GetMapping("v1/handle")
    public Result back(@RequestParam("passback_params") String passback_params){
        String p = passback_params.split(":")[0];
        String m = passback_params.split(":")[1]+"_"+ PayType.ALI.toString();

        List<ClientInfo> clientInfos = ClientData.clientData.get(p);
        ClientInfo clientInfo = clientInfos.stream().findFirst().get();

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setType(MessageType.ALI_BIZ);
        AliBizDTO dto = new AliBizDTO();
        dto.setPayNoAli(RandomUtil.randomString(16));
        dto.setClassName(clientInfo.getClassName());
        dto.setMethod(m);
        messageDTO.setBody(dto);
        ByteBuf resultBuf = clientInfo.getTcpSession().alloc().buffer();
        resultBuf.writeBytes(Encode.encode(messageDTO));
        clientInfo.getTcpSession().channel().writeAndFlush(resultBuf);

        return Result.success(ClientData.clientData);
    }

}
