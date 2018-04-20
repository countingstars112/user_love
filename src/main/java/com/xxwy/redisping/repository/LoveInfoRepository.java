package com.xxwy.redisping.repository;

import com.xxwy.redisping.entity.HumorousCommentEntity;
import com.xxwy.redisping.entity.LoveInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * * From  xxwy
 */
public interface LoveInfoRepository extends JpaRepository<LoveInfoEntity,String> {

    @Query(value = "SELECT * FROM  love_info love where DATE(love.love_time) = DATE( :love_time )", nativeQuery = true)
    List<LoveInfoEntity> findLoveInfoEntitiesBylove_time(@Param("love_time") Date date);

    @Query(value = "SELECT * FROM  love_info love where TO_DAYS(love.love_time) = TO_DAYS(NOW()) ORDER BY love.love_time DESC LIMIT 20 ", nativeQuery = true)
    List<LoveInfoEntity> findLoveInfoEntitiesByNow();

    @Modifying
    void deleteByLoveId(String loveId);

}
