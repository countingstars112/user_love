package com.xxwy.redisping.bean;


import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * * From  xxwy
 */
public class LoveInfo implements Serializable {

    @NotBlank
    private String session_id;
    private Date love_time;
    private String love_place;
    private String love_message;
    private String id;
    private boolean hide;
    private boolean image;

    public boolean isImage() {
        return image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public Date getLove_time() {
        return love_time;
    }

    public void setLove_time(Date love_time) {
        this.love_time = love_time;
    }

    public String getLove_place() {
        return love_place;
    }

    public void setLove_place(String love_place) {
        this.love_place = love_place;
    }

    public String getLove_message() {
        return love_message;
    }

    public void setLove_message(String love_message) {
        this.love_message = love_message;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public boolean isImages() {
        return image;
    }

    public void setImages(boolean images) {
        this.image = images;
    }

    public LoveInfo(String session_id, Date love_time, String love_place, String love_message, boolean hide, boolean image,String id) {
        this.session_id = session_id;
        this.love_time = love_time;
        this.love_place = love_place;
        this.love_message = love_message;
        this.hide = hide;
        this.image = image;
        this.id = id;
    }

    public LoveInfo() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoveInfo loveInfo = (LoveInfo) o;

        if (hide != loveInfo.hide) return false;
        if (image != loveInfo.image) return false;
        if (session_id != null ? !session_id.equals(loveInfo.session_id) : loveInfo.session_id != null) return false;
        if (love_time != null ? !love_time.equals(loveInfo.love_time) : loveInfo.love_time != null) return false;
        if (love_place != null ? !love_place.equals(loveInfo.love_place) : loveInfo.love_place != null) return false;
        return love_message != null ? love_message.equals(loveInfo.love_message) : loveInfo.love_message == null;
    }

    @Override
    public int hashCode() {
        int result = session_id != null ? session_id.hashCode() : 0;
        result = 31 * result + (love_time != null ? love_time.hashCode() : 0);
        result = 31 * result + (love_place != null ? love_place.hashCode() : 0);
        result = 31 * result + (love_message != null ? love_message.hashCode() : 0);
        result = 31 * result + (hide ? 1 : 0);
        result = 31 * result + (image ? 1 : 0);
        return result;
    }
}
