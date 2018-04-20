package com.xxwy.redisping.service;

import com.xxwy.redisping.bean.UserInfo;
import com.xxwy.redisping.entity.UserInfoEntity;
import com.xxwy.redisping.redis.RedisDao;
import com.xxwy.redisping.redis.bean.LoginRedis;
import com.xxwy.redisping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * * From  xxwy
 */
@Service
public class UserInfoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisDao redisDao;

    public boolean saveUserInfo(UserInfo userInfo){
        String open_id = redisDao.getOpne_id(userInfo.getSession_id());
        //得到open_id
        if(open_id != null) {
            //构造保存对象
            UserInfoEntity userInfoEntity = new UserInfoEntity(open_id, userInfo.getNickName(), userInfo.getAvatarUrl(), userInfo.getGender(),
                    userInfo.getCity(), userInfo.getProvince(), userInfo.getCountry());
            UserInfoEntity save = userRepository.saveAndFlush(userInfoEntity);
            if (userInfoEntity.equals(save)) {
                return true;
            }
        }
        return false;

    }
}
