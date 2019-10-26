package com.learnspringboot.study2.model;

public class Notification {
    private int id;
    private int notifier;
    private  int receiver;
    private int outerid;
    private int type;
    private String gmt_create;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNotifier() {
        return notifier;
    }

    public void setNotifier(int notifier) {
        this.notifier = notifier;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public int getOuterid() {
        return outerid;
    }

    public void setOuterid(int outerid) {
        this.outerid = outerid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
}
