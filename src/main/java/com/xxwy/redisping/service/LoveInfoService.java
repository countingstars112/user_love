package com.xxwy.redisping.service;

import com.xxwy.redisping.bean.LoveInfo;
import com.xxwy.redisping.entity.LoveInfoEntity;
import com.xxwy.redisping.redis.RedisDao;
import com.xxwy.redisping.redis.bean.LoginRedis;
import com.xxwy.redisping.repository.LoveCommentRepository;
import com.xxwy.redisping.repository.LoveInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * * From  xxwy
 */
@Service
public class LoveInfoService {

    @Autowired
    LoveInfoRepository loveInfoRepository;

    @Autowired
    LoveCommentRepository loveCommentRepository;

    @Autowired
    RedisDao redisDao;

    public LoveInfoEntity save(LoveInfo loveInfo){
        //session_id到open_id的映射
        String open_id = redisDao.getOpne_id(loveInfo.getSession_id());
        if(open_id != null) {
            //得到这个open_id
            String love_id = UUID.randomUUID().toString();
            //String loveId, String open_id, Date love_time, String love_message, String love_place, Boolean hide
            LoveInfoEntity loveInfoEntity = new LoveInfoEntity(love_id, open_id, loveInfo.getLove_time(),
                    loveInfo.getLove_message(), loveInfo.getLove_place(), loveInfo.isHide(),loveInfo.isImages());
            LoveInfoEntity save = loveInfoRepository.saveAndFlush(loveInfoEntity);
            if(loveInfoEntity.equals(save)){
                return loveInfoEntity;
            }
        }
        return null;
    }

    public boolean findLoveIdAndImges(String love_id){
        LoveInfoEntity one = loveInfoRepository.findOne(love_id);
        if( one!= null){
            return one.getImages();
        }
        return false;
    }

    /**
     * 删除评论 + 删除板块信息 + 点赞数+图片
     * @param id
     */
    @Transactional
    public void deleteLoveInfo(String id){
        loveInfoRepository.deleteByLoveId(id);
        loveCommentRepository.deleteByLoveId(id);
        redisDao.delectlikeNum(id);
        redisDao.delectUnlikeNum(id);
    }

}
