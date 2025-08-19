package com.hwt.sbct.design.ai;

import com.hwt.sbct.constant.ManufacturerEnum;
import com.hwt.sbct.pojo.req.RealTimeRecordingTaskOperateReq;
import com.hwt.sbct.pojo.resp.RealTimeRecordingTaskOperateResp;
import org.springframework.stereotype.Service;

/**
 * @Description 微软处理器
 * @Date 2025/6/6 14:52 星期五
 * @Author Hu Wentao
 */

@Service
public class MicrosoftHandle extends  ManufacturerTemplate {

    @Override
    public void afterPropertiesSet() throws Exception {
        ManufacturerFactory.register(ManufacturerEnum.MICROSOFT, this);
    }

    @Override
    public RealTimeRecordingTaskOperateResp createRealTimeRecordingTask(RealTimeRecordingTaskOperateReq req) {
        return null;
    }

    @Override
    public RealTimeRecordingTaskOperateResp stopRealTimeRecordingTask(RealTimeRecordingTaskOperateReq req) {
        return null;
    }

    @Override
    public RealTimeRecordingTaskOperateResp getRealTimeRecordingTaskInfo(RealTimeRecordingTaskOperateReq req) {
        return null;
    }
}
