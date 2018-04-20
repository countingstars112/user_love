package com.xxwy.redisping.bean;

import com.xxwy.redisping.entity.LoveCommentEntity;
import com.xxwy.redisping.entity.LoveInfoEntity;
import com.xxwy.redisping.entity.UserInfoEntity;

import java.io.Serializable;

/**
 * * From  xxwy
 */
public class CommentAndUser implements Serializable {



    private LoveCommentEntity loveComment;
    private UserInfoEntity form_userInfo;
    private UserInfoEntity to_userInfo;

    public LoveCommentEntity getLoveCommentEntity() {
        return loveComment;
    }

    public void setLoveCommentEntity(LoveCommentEntity loveCommentEntity) {
        this.loveComment = loveCommentEntity;
    }

    public UserInfoEntity getForm_userInfo() {
        return form_userInfo;
    }

    public void setForm_userInfo(UserInfoEntity form_userInfo) {
        this.form_userInfo = form_userInfo;
    }

    public UserInfoEntity getTo_userInfo() {
        return to_userInfo;
    }

    public void setTo_userInfo(UserInfoEntity to_userInfo) {
        this.to_userInfo = to_userInfo;
    }
}
