package com.xxwy.redisping.bean;

import com.xxwy.redisping.entity.UserInfoEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

/**
 * * From  xxwy
 */
public class LoveInfoAndComment implements Comparable ,Serializable{

    Logger logger = LoggerFactory.getLogger(LoveInfoAndComment.class);

    private List<CommentAndUser> CommentAndUser;
    private List<String> imageUrls;
    private LoveInfo loveInfo;
    private UserInfoEntity userInfo;
    private String likeNum;
    private String unlikeNum;

    public LoveInfoAndComment(LoveInfo loveInfo, UserInfoEntity userInfo) {
        this.loveInfo = loveInfo;
        this.userInfo = userInfo;
    }

    public List<com.xxwy.redisping.bean.CommentAndUser> getCommentAndUser() {
        return CommentAndUser;
    }

    public void setCommentAndUser(List<com.xxwy.redisping.bean.CommentAndUser> commentAndUser) {
        CommentAndUser = commentAndUser;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public LoveInfo getLoveInfo() {
        return loveInfo;
    }

    public void setLoveInfo(LoveInfo loveInfo) {
        this.loveInfo = loveInfo;
    }

    public String getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(String likeNum) {
        this.likeNum = likeNum;
    }

    public String getUnlikeNum() {
        return unlikeNum;
    }

    public void setUnlikeNum(String unlikeNum) {
        this.unlikeNum = unlikeNum;
    }

    @Override
    public int compareTo(Object object) {
        LoveInfoAndComment o = (LoveInfoAndComment) object;
        int likeThis = 0;
        int unlikeThis = 0;
        int likeObject = 0;
        int unlikeObject = 0;
        try {
            if(this.likeNum != null){
                likeThis = Integer.parseInt(this.likeNum);
            }
            if(this.unlikeNum != null){
                unlikeThis = Integer.parseInt(this.unlikeNum);
            }
            if(o.getLikeNum() != null){
                likeObject = Integer.parseInt(o.getLikeNum());
            }
            if(o.getUnlikeNum() != null){
                unlikeObject = Integer.parseInt(o.getUnlikeNum());
            }
        } catch (Exception e) {
            logger.error("like转换报错,likeNum为null");
        }
        int sizeThis = this.CommentAndUser.size();
        int sizeObject = o.getCommentAndUser().size();
        int totalThis = likeThis + unlikeThis + sizeThis;
        int totalObject = likeObject + unlikeObject + sizeObject;
        int i = Integer.compare(totalThis, totalObject);
        if(i == 0){
            return 1;
        }
        else {
            return i;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoveInfoAndComment that = (LoveInfoAndComment) o;

        if (loveInfo != null ? !loveInfo.equals(that.loveInfo) : that.loveInfo != null) return false;
        return userInfo != null ? userInfo.equals(that.userInfo) : that.userInfo == null;
    }

    @Override
    public int hashCode() {
        int result = loveInfo != null ? loveInfo.hashCode() : 0;
        result = 31 * result + (userInfo != null ? userInfo.hashCode() : 0);
        return result;
    }

    public UserInfoEntity getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoEntity userInfo) {
        this.userInfo = userInfo;
    }
}
