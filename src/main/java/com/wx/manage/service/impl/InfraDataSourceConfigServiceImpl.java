package com.wx.manage.service.impl;

import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wx.manage.exception.GlobalException;
import com.wx.manage.pojo.entity.InfraDataSourceConfig;
import com.wx.manage.mapper.InfraDataSourceConfigMapper;
import com.wx.manage.pojo.entity.SystemTenant;
import com.wx.manage.pojo.req.DataSourceConfigCreateVo;
import com.wx.manage.pojo.req.DataSourceConfigUpdateVo;
import com.wx.manage.pojo.resp.DataSourceConfigResp;
import com.wx.manage.result.ResultCodeEnum;
import com.wx.manage.service.InfraDataSourceConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.manage.service.SystemTenantService;
import com.wx.manage.until.JdbcUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 数据源配置表 服务实现类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-08
 */
@Service
public class InfraDataSourceConfigServiceImpl extends ServiceImpl<InfraDataSourceConfigMapper, InfraDataSourceConfig> implements InfraDataSourceConfigService {

    @Autowired
    private SystemTenantService tenantService;

    @Resource
    private DynamicDataSourceProperties dynamicDataSourceProperties;

    @Override
    public Long createDataSourceConfig(DataSourceConfigCreateVo createReqVO) {
        InfraDataSourceConfig dataSourceConfig = new InfraDataSourceConfig();
        dataSourceConfig.setUrl(createReqVO.getUrl());
        dataSourceConfig.setName(createReqVO.getName());
        dataSourceConfig.setUsername(createReqVO.getUsername());
        dataSourceConfig.setPassword(createReqVO.getPassword());

        //查询url是否存在
        validateDataSourceConfigExists(null, createReqVO.getUrl());

        //校验连接
        validateConnectionOK(dataSourceConfig);

        // 插入
        saveOrUpdate(dataSourceConfig);
        // 返回
        return dataSourceConfig.getId();
    }

    @Override
    public Boolean updateDataSourceConfig(DataSourceConfigUpdateVo updateReq) {
        InfraDataSourceConfig dataSourceConfig = getById(updateReq.getId());
        if (dataSourceConfig == null) {
            throw new GlobalException(ResultCodeEnum.DATA_NOT_EXIST_FAIL);
        }

        //查询url是否存在
        validateDataSourceConfigExists(updateReq.getId(), updateReq.getUrl());

        dataSourceConfig.setUrl(updateReq.getUrl());
        dataSourceConfig.setName(updateReq.getName());
        dataSourceConfig.setUsername(updateReq.getUsername());
        dataSourceConfig.setPassword(updateReq.getPassword());

        //校验连接
        validateConnectionOK(dataSourceConfig);

        return saveOrUpdate(dataSourceConfig);
    }

    @Override
    public Boolean deleteDataSourceConfig(Long id) {
        //校验数据是否被占用
        validateDataSourceConfigBeUsed(id);

        return removeById(id);
    }

    @Override
    public DataSourceConfigResp getDataSourceConfigDetail(Long id) {
        InfraDataSourceConfig dataSourceConfig = null;

        // 如果 id 为 0，默认为 master 的数据源
        if (Objects.equals(id, InfraDataSourceConfig.ID_MASTER)) {
            dataSourceConfig = buildMasterDataSourceConfig();
        } else {
            // 从 DB 中读取
            dataSourceConfig = getById(id);
        }

        DataSourceConfigResp resp = new DataSourceConfigResp();
        if (dataSourceConfig != null) {
            BeanUtils.copyProperties(dataSourceConfig, resp);
        }

        return resp;
    }

    @Override
    public List<DataSourceConfigResp> getDataSourceConfigList() {
        List<InfraDataSourceConfig> list = list();
        // 补充 master 数据源
        list.add(0, buildMasterDataSourceConfig());

        return list.stream().map(item -> {
            DataSourceConfigResp resp = new DataSourceConfigResp();
            BeanUtils.copyProperties(item, resp);
            return resp;
        }).collect(Collectors.toList());
    }

    @Override
    public Boolean testDataSourceConfig(Long id) {
        InfraDataSourceConfig dataSourceConfig = null;

        // 如果 id 为 0，默认为 master 的数据源
        if (Objects.equals(id, InfraDataSourceConfig.ID_MASTER)) {
            dataSourceConfig = buildMasterDataSourceConfig();
        } else {
            // 从 DB 中读取
            dataSourceConfig = getById(id);
        }

        //测试是否联通
        validateConnectionOK(dataSourceConfig);
        return true;
    }

    @Override
    public Boolean initTableStructureDataSourceConfig(Long id) {
        InfraDataSourceConfig dataSourceConfig = null;
        // 如果 id 为 0，默认为 master 的数据源
        if (Objects.equals(id, InfraDataSourceConfig.ID_MASTER)) {
            dataSourceConfig = buildMasterDataSourceConfig();
        } else {
            // 从 DB 中读取
            dataSourceConfig = getById(id);
        }

        String dbUrl = dataSourceConfig.getUrl();
        String username = dataSourceConfig.getUsername();
        String password = dataSourceConfig.getPassword();

        //测试是否联通
        validateConnectionOK(dataSourceConfig);

        String filePath = this.getClass().getClassLoader().getResource("sql/init_table.sql").getPath();

        return JdbcUtils.executeSqlFile(filePath, dbUrl, username, password);
    }


    /***
     * 检验连接是否正常
     * @param config
     */
    private void validateConnectionOK(InfraDataSourceConfig config) {
        if (config == null) {
            throw new GlobalException(ResultCodeEnum.DATA_SOURCE_CONFIG_NOT_EXISTS);
        }
        boolean success = JdbcUtils.isConnectionOK(config.getUrl(), config.getUsername(), config.getPassword());
        if (!success) {
            throw new GlobalException(ResultCodeEnum.DATA_SOURCE_CONFIG_NOT_OK);
        }
    }

    /**
     * 校验数据源是否存在
     * @param id
     * @param url
     */
    private void validateDataSourceConfigExists(Long id, String url) {
        LambdaQueryWrapper<InfraDataSourceConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(url), InfraDataSourceConfig::getUrl, url);
        queryWrapper.ne(id != null, InfraDataSourceConfig::getId, id);
        InfraDataSourceConfig infraDataSourceConfig = getOne(queryWrapper);
        if (infraDataSourceConfig != null) {
            throw new GlobalException(ResultCodeEnum.DATA_EXIST_FAIL);
        }
    }

    /**
     * 校验数据源是否被使用
     * @param id
     */
    public void validateDataSourceConfigBeUsed(Long id) {
        LambdaQueryWrapper<SystemTenant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemTenant::getDataSourceConfigId, id);
        List<SystemTenant> list = tenantService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            throw new GlobalException(ResultCodeEnum.DATA_IS_USED_FAIL);
        }
    }

    /**
     * 构建当前数据库
     * @return
     */
    private InfraDataSourceConfig buildMasterDataSourceConfig() {
        String primary = dynamicDataSourceProperties.getPrimary();
        DataSourceProperty dataSourceProperty = dynamicDataSourceProperties.getDatasource().get(primary);
        return new InfraDataSourceConfig().setId(InfraDataSourceConfig.ID_MASTER).setName(primary)
                .setUrl(dataSourceProperty.getUrl())
                .setUsername(dataSourceProperty.getUsername())
                .setPassword(dataSourceProperty.getPassword());
    }
}
