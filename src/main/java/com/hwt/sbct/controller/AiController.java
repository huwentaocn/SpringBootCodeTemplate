package com.hwt.sbct.controller;

import com.hwt.sbct.pojo.req.RealTimeRecordingTaskOperateReq;
import com.hwt.sbct.pojo.resp.RealTimeRecordingTaskOperateResp;
import com.hwt.sbct.pojo.resp.RealTimeRecordingTaskResultResp;
import com.hwt.sbct.result.Result;
import com.hwt.sbct.service.AiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Description ai控制层
 * @Date 2025/6/5 17:31 星期四
 * @Author Hu Wentao
 */
@Api(tags = "ai管理模块")
@CrossOrigin
@RestController
@RequestMapping("/ai")
public class AiController {

    @Resource
    private AiService aiService;

    @ApiOperation(value = "创建实时记录任务", notes = "创建实时记录任务")
    @PostMapping("/create/real/time/recording/task")
    public Result<RealTimeRecordingTaskOperateResp> createRealTimeRecordingTask(@RequestBody @Valid RealTimeRecordingTaskOperateReq req) {
        return Result.success(aiService.createRealTimeRecordingTask(req));
    }

    @ApiOperation(value = "停止实时记录任务", notes = "停止实时记录任务")
    @PostMapping("/stop/real/time/recording/task")
    public Result<RealTimeRecordingTaskOperateResp> stopRealTimeRecordingTask(@RequestBody @Valid RealTimeRecordingTaskOperateReq req) {
        return Result.success(aiService.stopRealTimeRecordingTask(req));
    }

    @ApiOperation(value = "查询任务状态和结果", notes = "停止实时记录任务")
    @PostMapping("/get/real/time/recording/task/info")
    public Result<RealTimeRecordingTaskOperateResp> getRealTimeRecordingTaskInfo(@RequestBody @Valid RealTimeRecordingTaskOperateReq req) {
        return Result.success(aiService.getRealTimeRecordingTaskInfo(req));
    }

    @ApiOperation(value = "测试模拟实时推流", notes = "测试模拟实时推流")
    @GetMapping("/test/real/time/trans")
    public Result testRealtimeTrans(@RequestParam("meetingJoinUrl") String meetingJoinUrl) {
        aiService.testRealtimeTrans(meetingJoinUrl);
        return Result.success();
    }
}
