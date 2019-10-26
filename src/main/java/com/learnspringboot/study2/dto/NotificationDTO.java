package com.learnspringboot.study2.dto;

import com.learnspringboot.study2.model.Question;
import com.learnspringboot.study2.model.User;

public class NotificationDTO {
    private int id;
    private String gmt_create;
    private int status;
    private User notifier;
    private String outerTitle;
    private int type;
    private Question question;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(String gmt_create) {
        this.gmt_create = gmt_create;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getNotifier() {
        return notifier;
    }

    public void setNotifier(User notifier) {
        this.notifier = notifier;
    }

    public String getOuterTitle() {
        return outerTitle;
    }

    public void setOuterTitle(String outerTitle) {
        this.outerTitle = outerTitle;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
