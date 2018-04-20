package com.xxwy.redisping.controller;

import com.xxwy.redisping.bean.LoveInfo;
import com.xxwy.redisping.entity.LoveInfoEntity;
import com.xxwy.redisping.redis.RedisDao;
import com.xxwy.redisping.service.LoveCommentService;
import com.xxwy.redisping.service.LoveInfoService;
import com.xxwy.redisping.tools.ResultUtil;
import com.xxwy.redisping.tools.bean.ResultData;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.*;
import java.util.*;

/**
 * * From  xxwy
 */

@RestController
@RequestMapping("/api/love")
public class LoveController {

    @Autowired
    RedisDao redisDao;

    @Autowired
    LoveInfoService loveInfoService;

    @Autowired
    LoveCommentService loveCommentService;
    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 信息
     * @param loveinfo
     * @param bindingResult
     * @return
     */
    @ApiOperation("上传告白信息")
    @PostMapping("/postLoveMessage")
    public ResultData postLoveMessage(@Valid @RequestBody LoveInfo loveinfo, BindingResult bindingResult) {
        logger.info(loveinfo.toString());
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(bindingResult);
        }
        LoveInfoEntity save = loveInfoService.save(loveinfo);
        if (save != null) {
            Map<String, String> ma = new HashMap<>();
            ma.put("love_id", save.getLoveId());
            return ResultUtil.success(ma);
        }
        return ResultUtil.error("传入session_id有误");
    }

    /**
     * 上传图片 ,图片的name都叫files,上传到服务器开着的端口的位置上
     */
    @ApiOperation("上传告白图片")
    @PostMapping("/postLoveImages")
    public ResultData uploadImags(HttpServletRequest request) {
        try {
            String love_id = (String) request.getParameter("love_id");
            String uploadDir = "//opt//weixi//images";
            //String uploadDir = request.getSession().getServletContext().getRealPath("/")+"/upload";
            logger.info(uploadDir);
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdir();
            }
            if (love_id != null) {
                if (loveInfoService.findLoveIdAndImges(love_id)) {
                    List<String> imagsUrl = new ArrayList<>();
                    List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("files");
                    if (files != null) {
                        for (MultipartFile file : files) {
                            //文件后缀名
                            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                            File localFile = new File(uploadDirFile, UUID.randomUUID() + suffix);
                            file.transferTo(localFile);
                            logger.info(localFile.getName());
                            imagsUrl.add("///https:i.smallrabbit.xin/"+localFile.getName());
                        }
                        //保存到redis里面
                        redisDao.saveImages(love_id, imagsUrl);
                        return ResultUtil.success(imagsUrl);
                    }
                    return ResultUtil.error("上传图片为空");
                }
                return ResultUtil.error("传入love_id不存在或者不允许上传图片");
            }
            return ResultUtil.error("传入love_id为空");
        } catch (IOException e) {
            logger.error(e.getMessage());
            return ResultUtil.error("上传图片保存异常");
        }
    }

    /**
     * 返回图片的位置就好了
     */
    @ApiOperation("得到图片服务器位置，直接请求：域名/图片名")
    @GetMapping("getLoveImages")
    public ResultData getLoveImags(@RequestParam(name = "love_id") String love_id) {
        List<String> images = redisDao.putImages(love_id);
        if (images != null) {
            return ResultUtil.success(images);
        }
        return ResultUtil.error("传入love_id有误");

    }

    /**
     * 删除,删除评论
     */
    @ApiOperation("删除板块信息，评论，图片，点赞数")
    @DeleteMapping("delectLoveInfo")
    public ResultData deleteLoveInfoAll(@RequestParam String id){
        try {
            loveInfoService.deleteLoveInfo(id);
            return ResultUtil.success();

        }catch (Exception e){
            logger.error(e.getMessage());
            return ResultUtil.error("删除错误");
        }

    }
}

