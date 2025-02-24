package com.hwt.sbct.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwt.sbct.mapper.WxProjectMapper;
import com.hwt.sbct.pojo.callVo.SaveProjectReq;
import com.hwt.sbct.pojo.callVo.SaveProjectResp;
import com.hwt.sbct.pojo.entity.WxProject;
import com.hwt.sbct.pojo.req.OperationProjectReq;
import com.hwt.sbct.result.Result;
import com.hwt.sbct.result.ResultCodeEnum;
import com.hwt.sbct.service.WxProjectService;
import com.hwt.sbct.serviceCall.WxpfCallService;
import com.hwt.sbct.until.EncryptionUtil;
import com.hwt.sbct.until.RSAKeyPair;
import com.hwt.sbct.until.SnowFlakeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 项目表 服务实现类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-08-24
 */
@Service
public class WxProjectServiceImpl extends ServiceImpl<WxProjectMapper, WxProject> implements WxProjectService {

    @Autowired
    private WxpfCallService wxpfCallService;

    @Override
    public Result addProject(OperationProjectReq req) {
        try {
            String name = req.getName();
            String outnetIp = req.getOutnetIp();
            String domainName = req.getDomainName();

            String host = StringUtils.isNotBlank(domainName) ? domainName : outnetIp;

            WxProject wxProject = new WxProject();

            if (StringUtils.isAllBlank(outnetIp, domainName)) {
                return Result.fail(ResultCodeEnum.PARAM_FAIL, "外网ip和域名不能同时为空");
            }

            //测试通信
            Result testServerResult = wxpfCallService.testServer(host);
            if (testServerResult == null || !Objects.equals(testServerResult.getCode(), ResultCodeEnum.SUCCESS.getCode())) {
                return Result.fail(ResultCodeEnum.WENXIANG_CALL_FAIL, "测试通信失败");
            }

            //生成项目id
            long projectId = SnowFlakeUtil.getNextId();
            //生成非对称密钥对
            RSAKeyPair rsaKeyPair = EncryptionUtil.generateRSAKeyPair();

            //保存项目信息
            SaveProjectReq saveProjectReq = new SaveProjectReq();
            saveProjectReq.setHost(host);
            saveProjectReq.setProjectId(projectId);
            saveProjectReq.setPublicKey(rsaKeyPair.getPublicKey());
            Result saveProjectRespResult = wxpfCallService.saveProject(saveProjectReq);
            if (saveProjectRespResult == null || !Objects.equals(saveProjectRespResult.getCode(), ResultCodeEnum.SUCCESS.getCode()) || saveProjectRespResult.getData() == null) {
                return Result.fail(ResultCodeEnum.WENXIANG_CALL_FAIL, "保存项目信息失败");
            }
            SaveProjectResp saveProjectResp = JSONObject.parseObject(JSONObject.toJSONString(saveProjectRespResult.getData()), SaveProjectResp.class);

            wxProject.setId(projectId);
            wxProject.setName(name);
            wxProject.setContractNumber(req.getContractNumber());
            wxProject.setPfId(req.getPfId());
            wxProject.setOutnetIp(outnetIp);
            wxProject.setIntranetIp(req.getIntranetIp());
            wxProject.setDomainName(req.getDomainName());
            wxProject.setPublicKey(rsaKeyPair.getPublicKey());
            wxProject.setPrivateKey(rsaKeyPair.getPrivateKey());

            //拿到的是用非对称密钥加密后的对称密钥，需解密
            String encryptedSymmetricKey = saveProjectResp.getEncryptedSymmetricKey();
            String symmetricKey = EncryptionUtil.decryptWithRSA(encryptedSymmetricKey, rsaKeyPair.getPrivateKey());
            wxProject.setSymmetricKey(symmetricKey);

            //存库
            saveOrUpdate(wxProject);
            return Result.success();
        } catch (Exception e) {
            log.error("WxProjectServiceImpl addProject Exception: ==>", e);
            return Result.fail(ResultCodeEnum.SYSTEM_ERROR500, e.getMessage());
        }
    }

}
