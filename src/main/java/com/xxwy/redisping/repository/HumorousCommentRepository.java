package com.xxwy.redisping.repository;

import com.xxwy.redisping.entity.HumorousCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

/**
 * * From  xxwy
 */
public interface HumorousCommentRepository extends JpaRepository<HumorousCommentEntity, String>{

    List<HumorousCommentEntity> findAllByHumorousId(String humorous);

    @Modifying
    void deleteByComemntId(String comment_id);
}
