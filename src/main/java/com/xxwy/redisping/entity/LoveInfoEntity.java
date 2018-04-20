package com.xxwy.redisping.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.*;


/**
 * * From  xxwy
 */

@Table(name = "love_info")
@Entity
public class LoveInfoEntity implements Serializable {

    @Id
    private String loveId;
    private String openId;
    private Date loveTime;
    private String loveMessage;
    private String lovePlace;
    private Boolean hide;
    private Boolean images;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoveInfoEntity that = (LoveInfoEntity) o;

        if (loveId != null ? !loveId.equals(that.loveId) : that.loveId != null) return false;
        if (openId != null ? !openId.equals(that.openId) : that.openId != null) return false;
        if (loveTime != null ? !loveTime.equals(that.loveTime) : that.loveTime != null) return false;
        if (loveMessage != null ? !loveMessage.equals(that.loveMessage) : that.loveMessage != null) return false;
        if (lovePlace != null ? !lovePlace.equals(that.lovePlace) : that.lovePlace != null) return false;
        if (hide != null ? !hide.equals(that.hide) : that.hide != null) return false;
        return images != null ? images.equals(that.images) : that.images == null;
    }

    @Override
    public int hashCode() {
        int result = loveId != null ? loveId.hashCode() : 0;
        result = 31 * result + (openId != null ? openId.hashCode() : 0);
        result = 31 * result + (loveTime != null ? loveTime.hashCode() : 0);
        result = 31 * result + (loveMessage != null ? loveMessage.hashCode() : 0);
        result = 31 * result + (lovePlace != null ? lovePlace.hashCode() : 0);
        result = 31 * result + (hide != null ? hide.hashCode() : 0);
        result = 31 * result + (images != null ? images.hashCode() : 0);
        return result;
    }

    public String getLoveId() {
        return loveId;
    }

    public void setLoveId(String loveId) {
        this.loveId = loveId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getLoveTime() {
        return loveTime;
    }

    public void setLoveTime(Date loveTime) {
        this.loveTime = loveTime;
    }

    public String getLoveMessage() {
        return loveMessage;
    }

    public void setLoveMessage(String loveMessage) {
        this.loveMessage = loveMessage;
    }

    public String getLovePlace() {
        return lovePlace;
    }

    public void setLovePlace(String lovePlace) {
        this.lovePlace = lovePlace;
    }

    public Boolean getHide() {
        return hide;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }

    public Boolean getImages() {
        return images;
    }

    public void setImages(Boolean images) {
        this.images = images;
    }

    public LoveInfoEntity(String loveId, String openId, Date loveTime, String loveMessage, String lovePlace,
                          Boolean hide, Boolean images) {
        this.loveId = loveId;
        this.openId = openId;
        this.loveTime = loveTime;
        this.loveMessage = loveMessage;
        this.lovePlace = lovePlace;
        this.hide = hide;
        this.images = images;
    }

    public LoveInfoEntity() {
    }
}
