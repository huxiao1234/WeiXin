package com.paratera.sgri.service;

public interface BaseMessageUtil<T> {
    /**
     * 将回复的消息转为XML返回
     * 
     * @param t
     * @return
     */
    public abstract String messageToXML(T t);

    /**
     * 回复消息封装
     * 
     * @param FromUserName
     * @param ToUserName
     * @return
     */
    public abstract String initMessage(String FromUserName, String ToUserName);
}
