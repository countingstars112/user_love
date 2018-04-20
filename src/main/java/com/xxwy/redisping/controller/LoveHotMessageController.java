package com.xxwy.redisping.controller;

import com.xxwy.redisping.bean.CommentAndUser;
import com.xxwy.redisping.bean.LoveInfoAndComment;
import com.xxwy.redisping.entity.LoveCommentEntity;
import com.xxwy.redisping.redis.RedisDao;
import com.xxwy.redisping.repository.LoveCommentRepository;
import com.xxwy.redisping.service.HotMesageService;
import com.xxwy.redisping.tools.ResultUtil;
import com.xxwy.redisping.tools.bean.ResultData;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;


/**
 * * From  xxwy
 */
@RestController
@RequestMapping("api/LoveHotMessage")
public class LoveHotMessageController {

    @Autowired
    HotMesageService hotMesageService;

    @Autowired
    RedisDao redisDao;

    @Autowired
    LoveCommentRepository loveCommentRepository;

    Logger logger = LoggerFactory.getLogger(LoveHotMessageController.class);

    /**
     * 展示昨天的热点消息20条
     * 从redis中找，如果没有，就直接从mysql中找到，同步到redis中，这个时候redis用成缓存
     * @return
     */
    @ApiOperation("获取传入日期当天的数据 ,按照热点排序")
    @GetMapping("getTimeMessage")
    public ResultData getTimeMessage(@RequestParam Date date) {
        logger.info(date.toString());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date);
        //现存redis里
        Object loveHotMessage = redisDao.getLoveHotMessage(format);
        if(loveHotMessage != null){
            return ResultUtil.success(loveHotMessage);
        }
        TreeSet<LoveInfoAndComment> loveInfo = hotMesageService.findLoveInfo(date);
        if (loveInfo != null) {
            ArrayList<LoveInfoAndComment> list = new ArrayList<>(loveInfo);
            List<LoveInfoAndComment> loveInfoAndComments = list.subList(0, 20);
            redisDao.saveLoveHotMessage(format,loveInfoAndComments);
            return ResultUtil.success(loveInfoAndComments);
        }
        return ResultUtil.error("当天信息为空");
    }



    @ApiOperation("最新信息20，全部加载, info + comment")
    @GetMapping("getHotMessage")
    public ResultData getTodayMessageAll() {
        List<LoveInfoAndComment> info = hotMesageService.findLoveInfoTD(true);
        if (info != null) {
            return ResultUtil.success(info);
        }
        return ResultUtil.error("当天信息为空");
    }

    @ApiOperation("最新信息，只有loveInfo")
    @GetMapping("getHotMessageLove")
    public ResultData getTodayMessageloveInfo() {
        List<LoveInfoAndComment> info = hotMesageService.findLoveInfoTD(false);
        if (info != null) {
            return ResultUtil.success(info);
        }
        return ResultUtil.error("当天信息为空");
    }

    @ApiOperation("获取某个信息的评论")
    @GetMapping("getHotMessageComment")
    public ResultData getHotMessageComment(@RequestParam String love_id){
        List<LoveCommentEntity> oneComments = loveCommentRepository.findAllByLoveId(love_id);
        if(oneComments != null) {
            List<CommentAndUser> commentAndUsers = hotMesageService.commentAndUsers(oneComments);
            return ResultUtil.success(commentAndUsers);
        }
        else {
            return ResultUtil.error("查询结果为空");
        }

    }

}
