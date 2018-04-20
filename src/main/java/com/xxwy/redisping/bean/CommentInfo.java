package com.xxwy.redisping.bean;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

/**
 * * From  xxwy
 */
public class CommentInfo implements Serializable {


    @NotBlank
    private String id;
    @NotBlank
    private String from_id;
    @NotBlank
    private String to_id;
    @NotBlank
    private String comment_message;
    private String comment_place;
    private Date comment_time;
    private boolean hide;
    private int floor_num;

    public int getFloor_num() {
        return floor_num;
    }

    public void setFloor_num(int floor_num) {
        this.floor_num = floor_num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public String getTo_id() {
        return to_id;
    }

    public void setTo_id(String to_id) {
        this.to_id = to_id;
    }

    public String getComment_message() {
        return comment_message;
    }

    public void setComment_message(String comment_message) {
        this.comment_message = comment_message;
    }

    public String getComment_place() {
        return comment_place;
    }

    public void setComment_place(String comment_place) {
        this.comment_place = comment_place;
    }

    public Date getComment_time() {
        return comment_time;
    }

    public void setComment_time(Date comment_time) {
        this.comment_time = comment_time;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public CommentInfo() {
    }
}
