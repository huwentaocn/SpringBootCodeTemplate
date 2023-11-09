package com.wx.manage.service;

import com.wx.manage.pojo.entity.InfraDataSourceConfig;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.manage.pojo.req.DataSourceConfigCreateVo;
import com.wx.manage.pojo.req.DataSourceConfigUpdateVo;
import com.wx.manage.pojo.resp.DataSourceConfigResp;

import java.util.List;

/**
 * <p>
 * 数据源配置表 服务类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-08
 */
public interface InfraDataSourceConfigService extends IService<InfraDataSourceConfig> {

    Long createDataSourceConfig(DataSourceConfigCreateVo createReqVO);

    Boolean updateDataSourceConfig(DataSourceConfigUpdateVo updateReq);

    Boolean deleteDataSourceConfig(Long id);

    DataSourceConfigResp getDataSourceConfigDetail(Long id);

    List<DataSourceConfigResp> getDataSourceConfigList();

    Boolean testDataSourceConfig(Long id);

    Boolean initTableStructureDataSourceConfig(Long id);
}
