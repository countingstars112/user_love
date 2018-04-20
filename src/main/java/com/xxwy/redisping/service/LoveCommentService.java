package com.xxwy.redisping.service;

import com.xxwy.redisping.bean.CommentInfo;
import com.xxwy.redisping.entity.LoveCommentEntity;
import com.xxwy.redisping.redis.RedisDao;
import com.xxwy.redisping.repository.LoveCommentRepository;
import com.xxwy.redisping.repository.LoveInfoRepository;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * * From  xxwy
 */
@Service
public class LoveCommentService {

    @Autowired
    RedisDao redisDao;

    @Autowired
    LoveCommentRepository loveCommentRepository;

    @Autowired
    LoveInfoRepository loveInfoRepository;

    /**
     * 1.判断id，from_id,to_id存在吗
     * 2.保存
     * @param commentInfo
     * @return
     */
    public boolean saveComment(CommentInfo commentInfo) {
        String id = commentInfo.getId();
        if(loveInfoRepository.findOne(id) != null) {
            String from_id = commentInfo.getFrom_id();
            String to_id = commentInfo.getTo_id();
            String from_open_id = null;
            String to_open_id = null;
            if((from_open_id = redisDao.getOpne_id(from_id))!= null &&
                    (to_open_id =redisDao.getOpne_id(to_id)) != null){
                // String comment_place, String comment_time, boolean hide)
                String comment_id = UUID.randomUUID().toString();
                LoveCommentEntity commentEntity = new LoveCommentEntity(comment_id, id, from_open_id, to_open_id, commentInfo.getComment_message(),
                        commentInfo.getComment_place(), commentInfo.getComment_time(), commentInfo.isHide(),commentInfo.getFloor_num());
                LoveCommentEntity loveCommentEntity = loveCommentRepository.saveAndFlush(commentEntity);
                if(commentEntity.equals(loveCommentEntity)){
                    return true;
                }
            }
        }
        return false;
    }



    public void deleteComment(String comment_id) {
        loveCommentRepository.deleteByComemntId(comment_id);
    }
}
