package com.xxwy.redisping.repository;

import com.xxwy.redisping.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * * From  xxwy
 */
public interface UserRepository extends JpaRepository<UserInfoEntity,String>{
}
