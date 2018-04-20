package com.xxwy.redisping.controller;

import com.xxwy.redisping.redis.RedisDao;
import com.xxwy.redisping.tools.ResultUtil;
import com.xxwy.redisping.tools.bean.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * * From  xxwy
 * 点赞，点屎数，两个板块都可以
 */
@RestController
@RequestMapping("/api")
public class LikeController {

    @Autowired
    RedisDao redisDao;

    @ApiOperation(value = "post 点赞数")
    @GetMapping("postlike")
    public ResultData postLike(@RequestParam  String id){
        redisDao.saveLikeNum(id);
        return ResultUtil.success();
    }

    @ApiOperation(value = "post 点shi数")
    @GetMapping("/postUnlike")
    public ResultData postUnlike(@RequestParam String id){
        redisDao.saveUnLikeNum(id);
        return ResultUtil.success();
    }
    @ApiOperation(value = "update 点赞数")
    @PutMapping("updatelike")
    public ResultData deletcLike(@RequestParam  String id){
        redisDao.updateLikeNum(id);
        return ResultUtil.success();
    }

    @ApiOperation(value = "update 点shi数")
    @PutMapping("/updateUnlike")
    public ResultData deleteUnlike(@RequestParam String id){
        redisDao.updateUnlikeNum(id);
        return ResultUtil.success();
    }
}
