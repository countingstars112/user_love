package com.xxwy.redisping.redis;

import com.xxwy.redisping.bean.LoveInfoAndComment;
import com.xxwy.redisping.redis.bean.LoginRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

/**
 * * From  xxwy
 */
@Repository
public class RedisDao {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;



    /**
     * session_id的保存或者替换
     * @param loginRedis
     */
    public void saveLogin(LoginRedis loginRedis){
        ValueOperations<String, Object> opsvalue = redisTemplate.opsForValue();
        //如果得到的值
        Object o = opsvalue.get(loginRedis.getOpen_id());
        if(o != null){
            //删除
            redisTemplate.delete(loginRedis.getSession_id());
        }
        opsvalue.set(loginRedis.getSession_id(),loginRedis);
        opsvalue.set(loginRedis.getOpen_id(),loginRedis);
    }


    /**
     * 得到open_id
     * @param sessio_id
     * @return
     */
    public String getOpne_id(String sessio_id){
        ValueOperations<String, Object> opsvalue = redisTemplate.opsForValue();
        LoginRedis loginRedis =(LoginRedis) opsvalue.get(sessio_id);
        if(loginRedis!= null)
            return loginRedis.getOpen_id();
        else
            return null;
    }

    /**
     * 得到session_id
     */
    public String getSession_id(String open_id){
        ValueOperations<String, Object> opsvalue = redisTemplate.opsForValue();
        LoginRedis loginRedis =(LoginRedis) opsvalue.get(open_id);
        if(loginRedis!= null)
            return loginRedis.getSession_id();
        else
            return null;
    }
    /**
     * 保存图片
     * @param love_id
     * @param imagsUrl
     */
    public void saveImages(String love_id, List<String> imagsUrl) {
        ListOperations listOperations = redisTemplate.opsForList();
        listOperations.leftPushAll(love_id+"_imags",imagsUrl);
    }

    /**
     * 获取图片
     * @param love_id
     * @return
     */
    public List<String> putImages(String love_id){
        ListOperations listOperations = redisTemplate.opsForList();
        List<String> imagsUrl = listOperations.range(love_id + "_imags", 0, -1);
        return imagsUrl;
    }

    public void delectImages(String love_id){
        ListOperations listOperations = redisTemplate.opsForList();
        listOperations.remove(love_id + "_imags",0,-1);
    }

    /**
     * 存储点赞
     * @param id
     */
    public void saveLikeNum(String id) {
        stringRedisTemplate.boundValueOps(id+"_like_num").increment(1);
    }
    public void saveUnLikeNum(String id) {
        stringRedisTemplate.boundValueOps(id+"_unlike_num").increment(1);
    }
    public void updateLikeNum(String id){
        stringRedisTemplate.boundValueOps(id+"_like_num").increment(-1);
    }
    public void updateUnlikeNum(String id){
        stringRedisTemplate.boundValueOps(id+"_unlike_num").increment(-1);
    }
    /**
     * 获取点赞
     * @param id
     * @return
     */
    public String getlikeNum(String id){
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        return stringStringValueOperations.get(id+"_like_num");
    }
    public String getUnlikeNum(String id){
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        return stringStringValueOperations.get(id+"_unlike_num");
    }

    /**
     * 删除点赞，点屎
     * @param id
     */
    public void delectlikeNum(String id){
        stringRedisTemplate.delete(id+"_like_num");
    }
    public void delectUnlikeNum(String id){
        stringRedisTemplate.delete(id+"_unlike_num");
    }

    /**
     * 收藏的增加，查看，删除
     * @param session_id
     * @param id
     * @return
     */
    public boolean saveUserCollection(String session_id, String id) {
        SetOperations setOperations = redisTemplate.opsForSet();
        String opne_id = getOpne_id(session_id);
        if(opne_id!= null) {
            setOperations.add(opne_id+"_humorous", id);
            return true;
        }
        return false;

    }

    public Set getUserCollection(String session_id) {
        SetOperations setOperations = redisTemplate.opsForSet();
        String opne_id = getOpne_id(session_id);
        if(opne_id!= null){
            Set members = setOperations.members(opne_id + "_humorous");
            return members;
        }
        return null;
    }

    public boolean deleteCollection(String session_id,String id){
        SetOperations setOperations = redisTemplate.opsForSet();
        String opne_id = getOpne_id(session_id);
        if(opne_id!= null){
            setOperations.remove(opne_id + "_humorous",id);
            return true;
        }
        return false;
    }

    /**
     * 保存传入是时间数据
     */
    public void saveLoveHotMessage(String date, List<LoveInfoAndComment> loveInfoAndComments){
        ValueOperations<String, Object> opsvalue = redisTemplate.opsForValue();
        opsvalue.set(date+"_love",loveInfoAndComments,1, TimeUnit.DAYS);
    }

    public Object getLoveHotMessage(String date){
        ValueOperations<String, Object> opsvalue = redisTemplate.opsForValue();
        Object o = opsvalue.get(date + "_love");
        return o;
    }
}
