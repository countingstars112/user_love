package com.xxwy.redisping.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * * From  xxwy
 */
@Entity
@Table(name = "comment_love")
public class LoveCommentEntity implements Serializable {

    private  Integer floorNum;
    @Id
    private String comemntId;
    private String loveId;
    private String fromId;
    private String toId;
    private String commentMessage;
    private String commentPlace;
    private Date commentTime;
    private Boolean hide;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoveCommentEntity that = (LoveCommentEntity) o;

        if (floorNum != null ? !floorNum.equals(that.floorNum) : that.floorNum != null) return false;
        if (comemntId != null ? !comemntId.equals(that.comemntId) : that.comemntId != null) return false;
        if (loveId != null ? !loveId.equals(that.loveId) : that.loveId != null) return false;
        if (fromId != null ? !fromId.equals(that.fromId) : that.fromId != null) return false;
        if (toId != null ? !toId.equals(that.toId) : that.toId != null) return false;
        if (commentMessage != null ? !commentMessage.equals(that.commentMessage) : that.commentMessage != null)
            return false;
        if (commentPlace != null ? !commentPlace.equals(that.commentPlace) : that.commentPlace != null) return false;
        if (commentTime != null ? !commentTime.equals(that.commentTime) : that.commentTime != null) return false;
        return hide != null ? hide.equals(that.hide) : that.hide == null;
    }

    @Override
    public int hashCode() {
        int result = floorNum != null ? floorNum.hashCode() : 0;
        result = 31 * result + (comemntId != null ? comemntId.hashCode() : 0);
        result = 31 * result + (loveId != null ? loveId.hashCode() : 0);
        result = 31 * result + (fromId != null ? fromId.hashCode() : 0);
        result = 31 * result + (toId != null ? toId.hashCode() : 0);
        result = 31 * result + (commentMessage != null ? commentMessage.hashCode() : 0);
        result = 31 * result + (commentPlace != null ? commentPlace.hashCode() : 0);
        result = 31 * result + (commentTime != null ? commentTime.hashCode() : 0);
        result = 31 * result + (hide != null ? hide.hashCode() : 0);
        return result;
    }

    public Integer getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(Integer floorNum) {
        this.floorNum = floorNum;
    }

    public String getComemntId() {
        return comemntId;
    }

    public void setComemntId(String comemntId) {
        this.comemntId = comemntId;
    }

    public String getLoveId() {
        return loveId;
    }

    public void setLoveId(String loveId) {
        this.loveId = loveId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getCommentMessage() {
        return commentMessage;
    }

    public void setCommentMessage(String commentMessage) {
        this.commentMessage = commentMessage;
    }

    public String getCommentPlace() {
        return commentPlace;
    }

    public void setCommentPlace(String commentPlace) {
        this.commentPlace = commentPlace;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public Boolean getHide() {
        return hide;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }

    public LoveCommentEntity(String comemntId, String loveId, String fromId, String toId,
                             String commentMessage, String commentPlace, Date commentTime,
                             Boolean hide, Integer floorNum) {
        this.floorNum = floorNum;
        this.comemntId = comemntId;
        this.loveId = loveId;
        this.fromId = fromId;
        this.toId = toId;
        this.commentMessage = commentMessage;
        this.commentPlace = commentPlace;
        this.commentTime = commentTime;
        this.hide = hide;
    }

    public LoveCommentEntity() {
    }
}
