package com.paratera.sgri.entity;

/**
 * 文本消息类型
 * 
 * @author guoxiaohu
 *
 */
public class TextEntity extends BaseEntity {
    private String Content;

    public TextEntity() {}

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

}
