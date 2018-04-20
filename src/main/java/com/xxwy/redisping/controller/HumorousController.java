package com.xxwy.redisping.controller;

import com.xxwy.redisping.bean.HumorousInfo;
import com.xxwy.redisping.entity.HumorousEntity;
import com.xxwy.redisping.redis.RedisDao;
import com.xxwy.redisping.service.HumorousService;
import com.xxwy.redisping.tools.ResultUtil;
import com.xxwy.redisping.tools.bean.ResultData;
import io.swagger.annotations.ApiOperation;
import org.hibernate.type.descriptor.sql.VarbinaryTypeDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * * From  xxwy
 */
@RestController
@RequestMapping("api/humorous")
public class HumorousController {


    @Autowired
    HumorousService humorousService;

    @Autowired
    RedisDao redisDao;

    Logger logger = LoggerFactory.getLogger(HumorousController.class);


    @ApiOperation("上传骚话信息")
    @PostMapping("postHumorousInfo")
    public ResultData postHumorousInfo(@Valid @RequestBody HumorousInfo humorousInfo, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultUtil.error(bindingResult);
        }
        HumorousEntity save = humorousService.saveInfo(humorousInfo);
        if(save!=null) {
            Map<String, String> ma = new HashMap<>();
            ma.put("humorous_id",save.getHumorousId());
            return ResultUtil.success(ma);
        }
        return ResultUtil.error("传入session_id有误");

    }

    /**
     * 上传图片 ,图片的name都叫files,上传到服务器开着的端口的位置上
     */
    @ApiOperation("上传图片")
    @PostMapping("/postHumorousImags")
    public ResultData uploadImags(HttpServletRequest request){
        try {
            String humorous_id =(String) request.getParameter("humorous_id");
            String uploadDir = "//opt//weixi//images";
            logger.info(uploadDir);
            File uploadDirFile = new File(uploadDir);
            if(!uploadDirFile.exists()){
                uploadDirFile.mkdir();
            }
            if(humorous_id!=null) {
                if (humorousService.findImges(humorous_id)) {
                    List<String> imagsUrl_hu = new ArrayList<>();
                    List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("files");
                    if(files!= null) {
                        for (MultipartFile file : files) {
                            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                            File localFile = new File(uploadDirFile, UUID.randomUUID() + suffix);
                            file.transferTo(localFile);
                            logger.info(localFile.getName());
                            imagsUrl_hu.add(localFile.getName());
                        }
                        redisDao.saveImages(humorous_id, imagsUrl_hu);
                        return ResultUtil.success(imagsUrl_hu);
                    }
                    return ResultUtil.error("上传空图片");
                }
                return ResultUtil.error("传入humorous_id不存在或者不允许上传图片");
            }
            return ResultUtil.error("传入humorous_id为空");
        } catch (IOException e) {
            logger.error(e.getMessage());
            return ResultUtil.error("上传图片保存异常");
        }
    }

    /**
     *返回图片的位置就好了
     */
    @ApiOperation("得到图片服务器位置，直接请求：域名/图片名")
    @GetMapping("getHumorousImages")
    public ResultData getLoveImags(@RequestParam(name = "love_id") String love_id){
        List<String> images = redisDao.putImages(love_id);
        if(images!= null){
            return ResultUtil.success(images);
        }
        return ResultUtil.error("传入love_id有误");
    }
}
