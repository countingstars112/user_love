package com.xxwy.redisping.service;

import com.xxwy.redisping.bean.CommentAndUser;
import com.xxwy.redisping.bean.LoveInfo;
import com.xxwy.redisping.bean.LoveInfoAndComment;
import com.xxwy.redisping.bean.UserInfo;
import com.xxwy.redisping.entity.LoveCommentEntity;
import com.xxwy.redisping.entity.LoveInfoEntity;
import com.xxwy.redisping.entity.UserInfoEntity;
import com.xxwy.redisping.redis.RedisDao;
import com.xxwy.redisping.repository.LoveCommentRepository;
import com.xxwy.redisping.repository.LoveInfoRepository;
import com.xxwy.redisping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

/**
 * * From  xxwy
 */
@Service
public class HotMesageService {

    @Autowired
    LoveInfoRepository loveInfoRepository;

    @Autowired
    LoveCommentRepository loveCommentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RedisDao redisDao;

    /**
     * 获取指定时间的热点信息
     *
     * @param date
     */
    public void findTimeInfo(Date date) {
        //loveInfoRepository.find
    }

    /**
     * 获取的热点信息,保存在reedis里面
     * 获取其评论
     */
    public TreeSet<LoveInfoAndComment> findLoveInfo(Date date) {
        List<LoveInfoEntity> infoEntities = loveInfoRepository.findLoveInfoEntitiesBylove_time(date);
        List<LoveInfoAndComment> loveInfoAndComments = loveAndComment(infoEntities,true);
        TreeSet<LoveInfoAndComment> sortInof = new TreeSet<>(loveInfoAndComments);
        return sortInof;
    }

    /**
     * 获取当前
     * @return
     */
    public List<LoveInfoAndComment> findLoveInfoTD(Boolean comment) {
        List<LoveInfoEntity> infoEntities = loveInfoRepository.findLoveInfoEntitiesByNow();
        return loveAndComment(infoEntities,comment);
    }

    /**
     * 每条信息获取评论,图片，点赞数
     * @param infoEntities
     * @return
     */
    private List<LoveInfoAndComment> loveAndComment(List<LoveInfoEntity> infoEntities,boolean comment) {
        List<LoveInfoAndComment> loveInfoAndComments = new ArrayList<>();
        if (infoEntities != null) {
            for (LoveInfoEntity infoOne : infoEntities) {
                if (infoOne != null) {
                    String love_id = infoOne.getLoveId();
                    //得到评论
                    List<CommentAndUser> commentAndUsers = null;
                    if(comment) {
                        List<LoveCommentEntity> oneComments = loveCommentRepository.findAllByLoveId(love_id);
                        commentAndUsers = commentAndUsers(oneComments);
                    }
                    //保存上
                    //查询session_id保存上,和个人信息
                    String openId = infoOne.getOpenId();
                    String session_id = redisDao.getSession_id(openId);
                    //String session_id, Date love_time, String love_place, String love_message, boolean hide, boolean image
                    LoveInfo loveInfo = new LoveInfo(session_id, infoOne.getLoveTime(), infoOne.getLovePlace(), infoOne.getLoveMessage(),
                            infoOne.getHide(), infoOne.getImages(),infoOne.getLoveId());
                    UserInfoEntity one = userRepository.findOne(openId);
                    one.setOpenId(null);
                    LoveInfoAndComment infoAndComment = new LoveInfoAndComment(loveInfo,one);
                    infoAndComment.setCommentAndUser(commentAndUsers);
                    if (infoOne.getImages()) {
                        List<String> imagesUrl = redisDao.putImages(love_id);
                        infoAndComment.setImageUrls(imagesUrl);
                    }
                    //保存点赞，点屎
                    String likeNum = redisDao.getlikeNum(love_id);
                    String unlikeNum = redisDao.getUnlikeNum(love_id);
                    if (likeNum == null){
                        infoAndComment.setLikeNum("0");
                    }else {
                        infoAndComment.setLikeNum(likeNum);
                    }
                    if(unlikeNum == null){
                        infoAndComment.setUnlikeNum("0");
                    }else {
                        infoAndComment.setUnlikeNum(unlikeNum);
                    }
                    loveInfoAndComments.add(infoAndComment);
                }
            }
            return loveInfoAndComments;
        }
        return null;
    }

    /**
     * 每条评论获取用户信息
     * @param comments
     * @return
     */
    public List<CommentAndUser> commentAndUsers(List<LoveCommentEntity> comments){
        List<CommentAndUser> commentAndUsers = new ArrayList<>();
        if(comments!= null) {
            for (LoveCommentEntity oneComment : comments) {
                if(oneComment != null) {
                    CommentAndUser commentAndUser = new CommentAndUser();
                    UserInfoEntity form_user = userRepository.findOne(oneComment.getFromId());
                    //UserInfoEntity to_user = userRepository.findOne(oneComment.getToId());
                    commentAndUser.setForm_userInfo(form_user);
                    //commentAndUser.setTo_userInfo(to_user);
                    commentAndUser.setLoveCommentEntity(oneComment);
                    commentAndUsers.add(commentAndUser);
                }
            }
        }
        return commentAndUsers;
    }

}
