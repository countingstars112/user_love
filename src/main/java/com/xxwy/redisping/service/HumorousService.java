package com.xxwy.redisping.service;

import com.xxwy.redisping.bean.CommentInfo;
import com.xxwy.redisping.bean.HumorousInfo;
import com.xxwy.redisping.entity.HumorousCommentEntity;
import com.xxwy.redisping.entity.HumorousEntity;
import com.xxwy.redisping.entity.LoveCommentEntity;
import com.xxwy.redisping.entity.LoveInfoEntity;
import com.xxwy.redisping.redis.RedisDao;
import com.xxwy.redisping.repository.HumorousCommentRepository;
import com.xxwy.redisping.repository.HumorousRepository;
import com.xxwy.redisping.tools.ResultUtil;
import com.xxwy.redisping.tools.bean.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * * From  xxwy
 */
@Service
public class HumorousService {


    @Autowired
    HumorousRepository humorousRepository;

    @Autowired
    RedisDao redisDao;

    @Autowired
    HumorousCommentRepository humorousCommentRepository;

    /**
     * 存储humorousInfo
     * @param humorousInfo
     * @return
     */
    public HumorousEntity saveInfo(HumorousInfo humorousInfo) {
        //得到session_id
        String session_id = humorousInfo.getSession_id();
        String opne_id = redisDao.getOpne_id(session_id);
        if(opne_id != null){
            String id = UUID.randomUUID().toString();
            //String humorous_id, String open_id, String humorous_message, Date humorous_time,
            // String humorous_place, Boolean hide, Boolean images
            HumorousEntity entity = new HumorousEntity(id, opne_id, humorousInfo.getHumorous_message(),
                    humorousInfo.getHumorous_time(), humorousInfo.getHumorous_place(),
                    humorousInfo.isHide(), humorousInfo.isImages());
            HumorousEntity save = humorousRepository.save(entity);
            if(entity.equals(save)){
                return entity;
            }

        }
        return null;
    }

    /**
     * 查找找照片是不是有
     * @param humorous_id
     * @return
     */
    public boolean findImges(String humorous_id) {
        HumorousEntity one = humorousRepository.findOne(humorous_id);
        if( one != null){
            return one.getImages();
        }
        return false;
    }

    /**
     * 保存humorousComment
     * @param commentInfo
     * @return
     */
    public  boolean saveComment(CommentInfo commentInfo) {
        String id = commentInfo.getId();
        if(humorousRepository.findOne(id) != null) {
            String from_id = commentInfo.getFrom_id();
            String to_id = commentInfo.getTo_id();
            String from_open_id = null;
            String to_open_id = null;
            if((from_open_id = redisDao.getOpne_id(from_id))!= null &&
                    (to_open_id =redisDao.getOpne_id(to_id)) != null){
                String comment_id = UUID.randomUUID().toString();
                // String comemntId, String humorousId, String fromId, String toId, String commentMessage, String commentPlace, Date commentTime, Boolean hide)
                HumorousCommentEntity commentEntity = new HumorousCommentEntity(comment_id, id, from_open_id, to_open_id, commentInfo.getComment_message(),
                        commentInfo.getComment_place(), commentInfo.getComment_time(), commentInfo.isHide(),commentInfo.getFloor_num());
                HumorousCommentEntity save = humorousCommentRepository.saveAndFlush(commentEntity);
                if(commentEntity.equals(save)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 删除评论
     */
    @Transactional
    public void deletComment(String comment_id){
        humorousCommentRepository.delete(comment_id);
    }
}
