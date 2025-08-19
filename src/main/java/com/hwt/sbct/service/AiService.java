package com.hwt.sbct.service;

import com.hwt.sbct.pojo.req.RealTimeRecordingTaskOperateReq;
import com.hwt.sbct.pojo.resp.RealTimeRecordingTaskOperateResp;

/**
 * @Description ai业务接口
 * @Date 2025/6/5 17:33 星期四
 * @Author Hu Wentao
 */
public interface AiService {
    RealTimeRecordingTaskOperateResp createRealTimeRecordingTask(RealTimeRecordingTaskOperateReq req);

    RealTimeRecordingTaskOperateResp stopRealTimeRecordingTask(RealTimeRecordingTaskOperateReq req);

    RealTimeRecordingTaskOperateResp getRealTimeRecordingTaskInfo(RealTimeRecordingTaskOperateReq req);

    void testRealtimeTrans(String meetingJoinUrl);
}
