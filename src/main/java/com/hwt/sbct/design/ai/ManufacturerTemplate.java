package com.hwt.sbct.design.ai;

import com.hwt.sbct.pojo.req.RealTimeRecordingTaskOperateReq;
import com.hwt.sbct.pojo.resp.RealTimeRecordingTaskOperateResp;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.InitializingBean;

/**
 * @Description 厂商方法模板
 * @Date 2025/6/6 14:24 星期五
 * @Author Hu Wentao
 */
@ApiModel(value = "ManufacturerTemplate", description = "厂商方法模板")
public abstract class ManufacturerTemplate implements InitializingBean {

    /**
     * 创建实时记录任务
     * @param req
     * @return
     */
    public abstract RealTimeRecordingTaskOperateResp createRealTimeRecordingTask(RealTimeRecordingTaskOperateReq req);

    /**
     * 停止实时记录任务
     * @param req
     * @return
     */
    public abstract RealTimeRecordingTaskOperateResp stopRealTimeRecordingTask(RealTimeRecordingTaskOperateReq req);

    /**
     * 查询实时记录任务结果
     * @param req
     * @return
     */
    public abstract RealTimeRecordingTaskOperateResp getRealTimeRecordingTaskInfo(RealTimeRecordingTaskOperateReq req);


}
