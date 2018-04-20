package com.xxwy.redisping.entity;

import net.bytebuddy.asm.Advice;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import java.util.Date;
import java.util.Objects;

/**
 * * From  xxwy
 */
@Table(name = "humorous_info")
@Entity
public class HumorousEntity {

    @Id
    private String humorousId;
    private String openId;
    private String humorousMessage;
    private Date humorousTime;
    private String humorousPlace;
    private Boolean hide;
    private Boolean images;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HumorousEntity that = (HumorousEntity) o;

        if (humorousId != null ? !humorousId.equals(that.humorousId) : that.humorousId != null) return false;
        if (openId != null ? !openId.equals(that.openId) : that.openId != null) return false;
        if (humorousMessage != null ? !humorousMessage.equals(that.humorousMessage) : that.humorousMessage != null)
            return false;
        if (humorousTime != null ? !humorousTime.equals(that.humorousTime) : that.humorousTime != null) return false;
        if (humorousPlace != null ? !humorousPlace.equals(that.humorousPlace) : that.humorousPlace != null)
            return false;
        if (hide != null ? !hide.equals(that.hide) : that.hide != null) return false;
        return images != null ? images.equals(that.images) : that.images == null;
    }

    @Override
    public int hashCode() {
        int result = humorousId != null ? humorousId.hashCode() : 0;
        result = 31 * result + (openId != null ? openId.hashCode() : 0);
        result = 31 * result + (humorousMessage != null ? humorousMessage.hashCode() : 0);
        result = 31 * result + (humorousTime != null ? humorousTime.hashCode() : 0);
        result = 31 * result + (humorousPlace != null ? humorousPlace.hashCode() : 0);
        result = 31 * result + (hide != null ? hide.hashCode() : 0);
        result = 31 * result + (images != null ? images.hashCode() : 0);
        return result;
    }

    public HumorousEntity(String humorousId, String openId, String humorousMessage, Date humorousTime, String humorousPlace, Boolean hide, Boolean images) {
        this.humorousId = humorousId;
        this.openId = openId;
        this.humorousMessage = humorousMessage;
        this.humorousTime = humorousTime;
        this.humorousPlace = humorousPlace;
        this.hide = hide;
        this.images = images;
    }

    public String getHumorousId() {
        return humorousId;
    }

    public void setHumorousId(String humorousId) {
        this.humorousId = humorousId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getHumorousMessage() {
        return humorousMessage;
    }

    public void setHumorousMessage(String humorousMessage) {
        this.humorousMessage = humorousMessage;
    }

    public Date getHumorousTime() {
        return humorousTime;
    }

    public void setHumorousTime(Date humorousTime) {
        this.humorousTime = humorousTime;
    }

    public String getHumorousPlace() {
        return humorousPlace;
    }

    public void setHumorousPlace(String humorousPlace) {
        this.humorousPlace = humorousPlace;
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

    public HumorousEntity() {
    }
}
