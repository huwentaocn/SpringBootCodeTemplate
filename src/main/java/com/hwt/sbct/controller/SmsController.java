package com.hwt.sbct.controller;

import com.hwt.sbct.pojo.req.SendSmsReq;
import com.hwt.sbct.exception.BindingResultResponse;
import com.hwt.sbct.result.Result;
import com.hwt.sbct.result.ResultCodeEnum;
import com.hwt.sbct.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Description 短信管理模块
 * @Date 2023/8/26 13:55 星期六
 * @Author Hu Wentao
 */

@Slf4j
@Api(tags = "短信管理模块")
@RestController
@CrossOrigin
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @ApiOperation(value = "发送五分钟通用短信", notes = "发送五分钟通用短信")
    @PostMapping("/send/five/minute/common/message")
    public Result sendFiveMinuteCommonMessage(@RequestBody @Valid SendSmsReq req,
                                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return Result.fail(ResultCodeEnum.PARAM_FAIL, BindingResultResponse.getError(bindingResult));
        }

        return smsService.sendFiveMinuteCommonMessage(req);
    }

    @ApiOperation(value = "发送两分钟通用短信", notes = "发送两分钟通用短信")
    @PostMapping("/send/two/minute/common/message")
    public Result sendTwoMinuteCommonMessage(@RequestBody @Valid SendSmsReq req,
                                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return Result.fail(ResultCodeEnum.PARAM_FAIL, BindingResultResponse.getError(bindingResult));
        }

        return smsService.sendTwoMinuteCommonMessage(req);
    }

    @ApiOperation(value = "发送一分钟通用短信", notes = "发送一分钟通用短信")
    @PostMapping("/send/one/minute/common/message")
    public Result sendOneMinuteCommonMessage(@RequestBody @Valid SendSmsReq req,
                                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return Result.fail(ResultCodeEnum.PARAM_FAIL, BindingResultResponse.getError(bindingResult));
        }

        return smsService.sendOneMinuteCommonMessage(req);
    }
}
