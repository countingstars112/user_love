package com.xxwy.redisping.repository;

import com.xxwy.redisping.entity.LoveCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * * From  xxwy
 */
public interface LoveCommentRepository extends JpaRepository<LoveCommentEntity,String>{

    List<LoveCommentEntity> findAllByLoveId(String love_id);

    @Modifying
    void deleteByLoveId(String loveId);

    @Modifying
    void deleteByComemntId(String comment_id);

}
