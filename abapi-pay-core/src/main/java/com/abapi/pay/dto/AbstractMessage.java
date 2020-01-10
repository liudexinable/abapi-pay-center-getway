package com.abapi.pay.dto.base;

public abstract class AbstractMessage<T>{

    /**
     * 消息类型
     */
    public abstract String getType();

    protected T body;

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

}