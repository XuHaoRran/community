package com.learnspringboot.study2.dto;

public class AccessTokenDTO {
    private String client_id;
    private String clien_secret;
    private String code;
    private  String redirect_url;
    private  String state;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClien_secret() {
        return clien_secret;
    }

    public void setClien_secret(String clien_secret) {
        this.clien_secret = clien_secret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRedirect_url() {
        return redirect_url;
    }

    public void setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
