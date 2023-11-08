package com.wx.manage.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.binarywang.wx.miniapp.util.WxMaConfigHolder;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wx.manage.constant.RedisConstant;
import com.wx.manage.constant.UserTypeEnum;
import com.wx.manage.pojo.entity.WxUser;
import com.wx.manage.pojo.req.GetWechatUserInfoReq;
import com.wx.manage.pojo.req.GetWechatUserPhoneReq;
import com.wx.manage.pojo.resp.UserLoginResp;
import com.wx.manage.pojo.vo.UserInfoVo;
import com.wx.manage.result.Result;
import com.wx.manage.result.ResultCodeEnum;
import com.wx.manage.service.WechatAppService;
import com.wx.manage.service.WxUserService;
import com.wx.manage.until.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Description 微信接口管理接口实现层
 * @Date 2023/8/29 14:49 星期二
 * @Author Hu Wentao
 */
@Service
@Slf4j
public class WechatAppServiceImpl implements WechatAppService {

    @Autowired
    private WxMaService wxMaService;

    @Autowired
    private WxUserService userService;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public Result<UserLoginResp> appLogin(String code) {
        try {
            /**
             * 1 得到微信返回code临时票据值
             * 2 拿着code + 小程序id + 小程序秘钥 请求微信接口服务
             * 3 请求微信接口服务，返回两个值 session_key 和 openid
             *
             * openId是你微信唯一标识
             */
            WxMaJscode2SessionResult sessionInfo = wxMaService.getUserService().getSessionInfo(code);
            log.info("sessionInfo ==> {}", JSONObject.toJSONString(sessionInfo));
            if (sessionInfo == null) {
                return Result.fail(ResultCodeEnum.WECHAT_CALL_FAIL);
            }

            /**
             * 4 添加微信用户信息到数据库里面
             * 判断是否是第一次使用微信授权登录：如何判断？openId
             */
            //
            String openid = sessionInfo.getOpenid();
            LambdaQueryWrapper<WxUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StringUtils.isNotBlank(openid), WxUser::getOpenId, openid);
            WxUser user = userService.getOne(queryWrapper);
            //第一次使用微信登录
            if (user == null) {
                user = new WxUser();
                user.setOpenId(openid);
                user.setUserName(openid);
                user.setNickName(openid);
                user.setUserTypeEnum(UserTypeEnum.USER);
                userService.saveOrUpdate(user);
            }

            //5 使用JWT工具根据userId和userName生成token字符串
            String token = JwtUtil.createToken(user.getId(), user.getUserName());

            //6 获取当前登录用户信息，放到Redis里面，设置有效时间
            UserInfoVo userInfoVo = new UserInfoVo();
            BeanUtils.copyProperties(user, userInfoVo);
            redisTemplate.opsForValue().set(RedisConstant.getUserInfoKey(user.getId()), userInfoVo, RedisConstant.USER_KEY_TIMEOUT, TimeUnit.DAYS);

            //8 需要数据封装到map返回
            UserLoginResp userLoginResp = new UserLoginResp();
            userLoginResp.setUserInfoVo(userInfoVo);
            userLoginResp.setToken(token);

            return Result.success(userLoginResp);
        } catch (Exception e) {
        	log.error("WechatServiceImpl appLogin Exception: ==>", e);
        	return Result.fail(ResultCodeEnum.SYSTEM_ERROR500, e.getMessage());
        }
    }

    @Override
    public Result<UserInfoVo> bindUserPhoneNumber(GetWechatUserPhoneReq req) {
        try {
            String code = req.getCode();
            String openid = req.getOpenid();

            //调用微信服务获取手机号对象
            WxMaPhoneNumberInfo phoneNoInfo = wxMaService.getUserService().getPhoneNoInfo(code);
            if (phoneNoInfo == null) {
                return Result.fail(ResultCodeEnum.WECHAT_CALL_FAIL);
            }

            LambdaQueryWrapper<WxUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StringUtils.isNotBlank(openid), WxUser::getOpenId, openid);
            WxUser user = userService.getOne(queryWrapper);
            if (user == null) {
                return Result.fail(ResultCodeEnum.DATA_NOT_EXIST_FAIL, "用户不存在");
            }

            user.setUserName(phoneNoInfo.getPhoneNumber());
            userService.saveOrUpdate(user);

            UserInfoVo userInfoVo = new UserInfoVo();
            BeanUtils.copyProperties(user, userInfoVo);

            //更新redis中的用户信息
            redisTemplate.opsForValue().set(RedisConstant.getUserInfoKey(user.getId()), userInfoVo, RedisConstant.USER_KEY_TIMEOUT, TimeUnit.DAYS);

            return Result.success(userInfoVo);
        } catch (Exception e) {
            log.error("WechatAppServiceImpl bindUserPhoneNumber Exception: ==>", e);
            return Result.fail(ResultCodeEnum.SYSTEM_ERROR500, e.getMessage());
        }
    }

    @Override
    public Result<UserInfoVo> getWechatUserInfo(GetWechatUserInfoReq req) {
        String sessionKey = req.getSessionKey();
        String encryptedData = req.getEncryptedData();
        String iv = req.getIv();

        // 解密用户信息
        WxMaUserInfo userInfo = wxMaService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
        WxMaConfigHolder.remove();//清理ThreadLocal

        log.info("userInfo==>{}", JSONObject.toJSONString(userInfo));

        return null;
    }

}
