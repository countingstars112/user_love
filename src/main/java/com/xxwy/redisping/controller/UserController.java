package com.xxwy.redisping.controller;

import com.xxwy.redisping.redis.RedisDao;
import com.xxwy.redisping.service.HotMesageService;
import com.xxwy.redisping.tools.ResultUtil;
import com.xxwy.redisping.tools.bean.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * * From  xxwy
 * 写，个人收藏和个人的等级，都保存到redis中
 */

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    RedisDao redisDao;

    @Autowired
    HotMesageService hotMesageService;

    /**
     *
     * @param session_id
     * @param id
     * @return
     */
    @ApiOperation("提交收藏")
    @GetMapping("postCollection")
    public ResultData postCollection(@RequestParam String session_id, @RequestParam String id){
        boolean b = redisDao.saveUserCollection(session_id, id);
        if(b){
            return ResultUtil.success();
        }
        return ResultUtil.error("传入的session_id有误");
    }

    /**
     * 得带为骚话id
     * @param session_id
     * @return
     */
    @GetMapping("getCollection")
    public ResultData getCollection(@RequestParam String session_id){
        Set userCollection = redisDao.getUserCollection(session_id);
        List list = new ArrayList(userCollection);
        if(userCollection!= null){
            return ResultUtil.success(userCollection);
        }
        return ResultUtil.error("传入的session_id查不到对应open_id");
    }

    @ApiOperation("删除个人收藏")
    @DeleteMapping("deleteCollection")
    public ResultData delete(@RequestParam String session_id, @RequestParam String id){
        boolean b = redisDao.deleteCollection(session_id, id);
        if(b){
            return ResultUtil.success();
        }
        return ResultUtil.error("找不到对应的open_id");
    }


}
