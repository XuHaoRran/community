package com.learnspringboot.study2.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND("问题不在得了啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊");

    @Override
    public String getMessage() {
        return message;
    }


    CustomizeErrorCode(String message){
        message = message;
    }
}
