package com.xxwy.redisping.bean;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * * From  xxwy
 */
public class HumorousInfo implements Serializable {

    @NotEmpty
    private String session_id;
    @NotBlank
    private String humorous_message;
    private Date humorous_time;
    private String humorous_place;
    private boolean hide;
    private boolean images;


    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getHumorous_message() {
        return humorous_message;
    }

    public void setHumorous_message(String humorous_message) {
        this.humorous_message = humorous_message;
    }

    public Date getHumorous_time() {
        return humorous_time;
    }

    public void setHumorous_time(Date humorous_time) {
        this.humorous_time = humorous_time;
    }

    public String getHumorous_place() {
        return humorous_place;
    }

    public void setHumorous_place(String humorous_place) {
        this.humorous_place = humorous_place;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public boolean isImages() {
        return images;
    }

    public void setImages(boolean images) {
        this.images = images;
    }

    public HumorousInfo() {
    }
}
