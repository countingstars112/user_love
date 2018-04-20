package com.xxwy.redisping.controller;

import com.xxwy.redisping.bean.CommentInfo;
import com.xxwy.redisping.redis.RedisDao;
import com.xxwy.redisping.service.HumorousService;
import com.xxwy.redisping.service.LoveCommentService;
import com.xxwy.redisping.service.LoveInfoService;
import com.xxwy.redisping.tools.ResultUtil;
import com.xxwy.redisping.tools.bean.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;


/**
 * * From  xxwy
 */
@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    LoveCommentService loveCommentService;

    @Autowired
    HumorousService humorousService;

    @Autowired
    RedisDao redisDao;


    /**
     * 评论
     * @param commentInfo
     * @param bindingResult
     * @return
     */
    @ApiOperation("提交 告白板块评论")
    @PostMapping("postLoveComment")
    public ResultData postLoveComment(@Valid @RequestBody CommentInfo commentInfo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResultUtil.error(bindingResult);
        }
        boolean b = loveCommentService.saveComment(commentInfo);
        if(b){
            return ResultUtil.success();
        }
        else {
            return ResultUtil.error("传入id, 或from_id，或者to_id有误");
        }
    }

    @ApiOperation("提交骚话板块评论")
    @PostMapping("postHumorousComment")
    public ResultData postHumorousComment(@Valid @RequestBody CommentInfo commentInfo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResultUtil.error(bindingResult);
        }
        boolean b = humorousService.saveComment(commentInfo);
        if(b){
            return ResultUtil.success();
        }
        else {
            return ResultUtil.error("传入id, 或from_id，或者to_id有误");
        }
    }

    @ApiOperation("删除love评论")
    @DeleteMapping("delectLoveComment")
    public ResultData deleteLoveComment(@RequestParam String comment_id){
        try {
            loveCommentService.deleteComment(comment_id);
            return ResultUtil.success();
        }catch (Exception e) {
            return ResultUtil.error("传入的comment_id不正确");
        }
    }


}
