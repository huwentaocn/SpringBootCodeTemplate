package com.wx.manage.service.impl;

import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wx.manage.exception.GlobalException;
import com.wx.manage.pojo.entity.InfraDataSourceConfig;
import com.wx.manage.pojo.entity.SystemDataSource;
import com.wx.manage.mapper.SystemDataSourceMapper;
import com.wx.manage.pojo.entity.SystemTenant;
import com.wx.manage.pojo.req.DataSourceConfigCreateVo;
import com.wx.manage.pojo.req.DataSourceCreateVo;
import com.wx.manage.result.ResultCodeEnum;
import com.wx.manage.service.SystemDataSourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.manage.until.JdbcUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 数据源表	 服务实现类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-20
 */
@Service
public class SystemDataSourceServiceImpl extends ServiceImpl<SystemDataSourceMapper, SystemDataSource> implements SystemDataSourceService {

    @Value("${sbct.databaseUrlTemplate}")
    private String databaseUrlTemplate;

    @Resource
    private DynamicDataSourceProperties dynamicDataSourceProperties;


    @Override
    public Long createDataSourceConfig(DataSourceCreateVo createReqVO) {
        SystemDataSource dataSource = new SystemDataSource();

        dataSource.setName(createReqVO.getName());
        dataSource.setIp(createReqVO.getIp());
        dataSource.setPort(createReqVO.getPort());
        dataSource.setUserName(createReqVO.getUsername());
        dataSource.setPassword(createReqVO.getPassword());
        dataSource.setType(1);

        String url = databaseUrlTemplate.replace("ip", dataSource.getIp()).replace("port", dataSource.getPort());
        dataSource.setUrl(url);

        //查询数据源是否存在
        validateDataSourceExists(createReqVO.getIp(), createReqVO.getPort());

        //校验连接
        validateConnectionOK(dataSource);

        // 插入
        saveOrUpdate(dataSource);
        // 返回
        return dataSource.getId();
    }

    @Override
    public Boolean testDataSource(Long id) {
        //从db中获取
        SystemDataSource dataSource = getById(id);

        //测试是否联通
        validateConnectionOK(dataSource);
        return true;
    }


    /**
     * 校验数据源是否存在
     * @param ip
     * @param port
     */
    private void validateDataSourceExists(String ip, String port) {
        LambdaQueryWrapper<SystemDataSource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(ip), SystemDataSource::getIp, ip);
        queryWrapper.eq(StringUtils.isNotBlank(port), SystemDataSource::getPort, port);
        SystemDataSource dataSource = getOne(queryWrapper);
        if (dataSource != null) {
            throw new GlobalException(ResultCodeEnum.DATA_EXIST_FAIL);
        }
    }


    /**
     * 检验连接是否正常
     * @param dataSource
     */
    private void validateConnectionOK(SystemDataSource dataSource) {
        if (dataSource == null) {
            throw new GlobalException(ResultCodeEnum.DATA_SOURCE_CONFIG_NOT_EXISTS);
        }

        boolean success = JdbcUtils.isConnectionOK(dataSource.getUrl(), dataSource.getUserName(), dataSource.getPassword());
        if (!success) {
            throw new GlobalException(ResultCodeEnum.DATA_SOURCE_CONFIG_NOT_OK);
        }
    }


}
