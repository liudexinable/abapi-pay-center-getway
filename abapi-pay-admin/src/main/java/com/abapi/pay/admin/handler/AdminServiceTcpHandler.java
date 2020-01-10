package com.mh.sviwo.gps.tcpser.handler;

import com.abapi.cloud.common.utils.JsonUtil;
import com.abapi.cloud.socket.annotation.TcpEndpointHandler;
import com.abapi.cloud.socket.mapping.AbstractTcpEndpointHandler;
import com.abapi.cloud.socket.pojo.TcpSession;
import com.mh.sviwo.gps.tcpser.connect.ConnectManage;
import com.mh.sviwo.gps.tcpser.connect.Session;
import com.mh.sviwo.gps.tcpser.jt808.codec.JT808MessageDecoder;
import com.mh.sviwo.gps.tcpser.jt808.common.MessageId;
import com.mh.sviwo.gps.tcpser.jt808.dto.Message;
import com.mh.sviwo.gps.tcpser.jt808.dto.base.AbstractBody;
import com.mh.sviwo.gps.tcpser.jt808.dto.base.AbstractMessage;
import com.mh.sviwo.gps.tcpser.jt808.mapping.HandlerEndpointContext;
import com.mh.sviwo.gps.tcpser.jt808.mapping.HandlerEndpointFactory;
import com.mh.sviwo.gps.tcpser.jt808.service.endpoint.AbstractEndpointService;
import io.netty.buffer.ByteBuf;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author ldx
 * @Date 2019/10/16 14:56
 * @Description
 * @Version 1.0.0
 */
@TcpEndpointHandler
@Component
public class Jt808TcpHandler extends AbstractTcpEndpointHandler {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    HandlerEndpointFactory handlerEndpointFactory;

    static Jt808TcpHandler jt808TcpHandler;

    @PostConstruct
    public void init() {
        jt808TcpHandler = this;
        jt808TcpHandler.handlerEndpointFactory = this.handlerEndpointFactory;
    }

    private static String captureName(String s) {
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    @Override
    public void doOnOpen(TcpSession tcpSession) {
        logger.info("连接成功>>>>"+tcpSession.id().asLongText());
        Session session = new Session();
        session.setTcpSession(tcpSession);
        ConnectManage.clientMap.put(tcpSession.id().asLongText(),session);
    }

    @Override
    public void doOnMessage(TcpSession tcpSession, Object msg) {
        logger.info("收到消息>>>>"+tcpSession.id().asLongText());
        ByteBuf buf = (ByteBuf) msg;
        AbstractMessage<AbstractBody> decode = JT808MessageDecoder.decode(buf);
        logger.info("解析数据完成>>>"+ JsonUtil.writeValueAsString(decode));
        Session session = ConnectManage.clientMap.get(tcpSession.id().asLongText());
        System.out.println(decode.getType()+"---");
        AbstractEndpointService instance;
        try {
            Class clz = HandlerEndpointContext.handlerClassMap.get(decode.getType());
            if(clz == null){
                logger.info("---未找到对应的处理器 开启查找通用处理器---"+ JsonUtil.writeValueAsString(decode));
                instance =jt808TcpHandler.handlerEndpointFactory.getInstance(captureName(HandlerEndpointContext.handlerClassMap.get(MessageId.type_0x2710).getSimpleName()));
            }else{
                instance =jt808TcpHandler.handlerEndpointFactory.getInstance(captureName(HandlerEndpointContext.handlerClassMap.get(decode.getType()).getSimpleName()));
            }
            Message messageResult = instance.handler(session, decode);
            if(messageResult != null){
                tcpSession.channel().writeAndFlush(messageResult).sync();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doOnError(TcpSession tcpSession, Throwable throwable) {
        logger.info("异常错误>>>>"+tcpSession.id());
    }

    @Override
    public void doOnClose(TcpSession tcpSession) {
        logger.info("连接关闭>>>>"+tcpSession.id().asLongText());
        ConnectManage.clientMap.remove(tcpSession.id().asLongText());
    }

    @Override
    public void doOnEvent(TcpSession tcpSession, Object evt) {
        IdleStateEvent event = (IdleStateEvent) evt;
        if (event.state() == IdleState.READER_IDLE) {
            logger.error("READ超时 服务器主动断开连接:{}", tcpSession.id().asLongText());
            tcpSession.close();
        }else if (event.state() == IdleState.WRITER_IDLE) {
            logger.error("WRITER超时 服务器主动断开连接:{}", tcpSession.id().asLongText());
            tcpSession.close();
        }else if (event.state() == IdleState.ALL_IDLE) {
            logger.error("ALL超时 服务器主动断开连接:{}", tcpSession.id().asLongText());
            tcpSession.close();
        }
    }

}
