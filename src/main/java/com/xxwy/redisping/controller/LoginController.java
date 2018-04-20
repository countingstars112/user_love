package com.xxwy.redisping.controller;

import com.alibaba.fastjson.JSONObject;
import com.xxwy.redisping.bean.UserInfo;
import com.xxwy.redisping.redis.RedisDao;
import com.xxwy.redisping.redis.bean.LoginRedis;
import com.xxwy.redisping.service.UserInfoService;
import com.xxwy.redisping.tools.ConnectionUtil;
import com.xxwy.redisping.tools.ResultUtil;
import com.xxwy.redisping.tools.bean.ResultData;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.UUID;

/**
 * * From  xxwy
 */
@RestController
@RequestMapping("/api/login")
public class LoginController {

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    RedisDao redisDao;

    @Autowired
    UserInfoService userInfoService;

    /**
     * 获得openic_session_key
     * @param code
     * @return
     */
    @ApiOperation("传入code得到获得session_id")
    @GetMapping("/getSessionId")
    public ResultData getSessionOId(@RequestParam(name = "code") String code) {
        if (!StringUtils.isEmpty(code)) {
            //和微信服务器通信
            ResultData result = ConnectionUtil.getSomeToLogin(code);
            //错误
            if (result.getCode().equals("200")) {
                return result;
            }
            JSONObject jsonObject = (JSONObject) result.getData();
            //保存上述的这些，然后返回自定义的sessionid
            String session_key = jsonObject.getString("session_key");
            String openid = jsonObject.getString("openid");
//            String session_key = "session_key"+code;
//            String openid = "openid1111"+code;
            String session_id = UUID.randomUUID().toString();

            redisDao.saveLogin(new LoginRedis(openid, session_id, session_key));
            HashMap<String, String> map = new HashMap<>();
            map.put("session_id", session_id);
            return ResultUtil.success(map);
        }
        return ResultUtil.error("code为空");
    }


    /**
     *
     * @param userInfo
     * @param result
     * @return
     */
    @ApiOperation(value = "上传个人信息")
    @PostMapping("postUserInfo")
    public ResultData postUserInfo(@Valid @RequestBody UserInfo userInfo, BindingResult result) {
        if (result.hasErrors()) {
            return ResultUtil.error(result);
        }
        boolean b = userInfoService.saveUserInfo(userInfo);
        if(b)
            return ResultUtil.success();
        return ResultUtil.error("传入session_id找不到对应的id");
    }



}
