package com.learnspringboot.study2.Enums;

public enum NotificationTypeEnum {
    REPLY_QUESTION(1,"回复问题"),
    REPLY_COMMENT(2,"回复了评论")
    ;

    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    NotificationTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }
}
