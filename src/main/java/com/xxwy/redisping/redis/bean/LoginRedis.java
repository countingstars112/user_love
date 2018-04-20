package com.xxwy.redisping.redis.bean;

import jdk.nashorn.internal.objects.annotations.Getter;

import java.io.Serializable;

/**
 * * From  xxwy
 */
public class LoginRedis implements Serializable{

    private String open_id;
    private String session_id;
    private String session_key;

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public LoginRedis(String open_id, String session_id, String session_key) {
        this.open_id = open_id;
        this.session_id = session_id;
        this.session_key = session_key;
    }

    @Override
    public String toString() {
        return "LoginRedis{" +
                "open_id='" + open_id + '\'' +
                ", session_id='" + session_id + '\'' +
                ", session_key='" + session_key + '\'' +
                '}';
    }
}
