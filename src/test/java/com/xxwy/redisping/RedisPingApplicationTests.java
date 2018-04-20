package com.xxwy.redisping;

import com.xxwy.redisping.entity.LoveCommentEntity;
import com.xxwy.redisping.redis.RedisDao;
import com.xxwy.redisping.repository.LoveCommentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisPingApplicationTests {

	@Autowired
	RedisTemplate redisTemplate;
	
	@Autowired
	LoveCommentRepository loveCommentRepository;

	@Autowired
    RedisDao redisDao;

	@Test
	public void contextLoads() {
        for (int i = 0; i < 10; i++) {
            redisDao.saveUnLikeNum("sdsadasss");
            if(i%2 == 0)
            redisDao.updateUnlikeNum("sdsadasss");
        }
        redisDao.saveUnLikeNum("sdsadasss");
        System.out.println("-------------------------------------");
        System.out.println(redisDao.getUnlikeNum("sdsadasss"));
        redisDao.delectUnlikeNum("sdsadasss");
        System.out.println(redisDao.getUnlikeNum("sdsadasss"));
	}

}
