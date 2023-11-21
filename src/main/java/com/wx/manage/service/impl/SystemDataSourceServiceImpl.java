package com.wx.manage.service.impl;

import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wx.manage.exception.GlobalException;
import com.wx.manage.pojo.entity.InfraDataSourceConfig;
import com.wx.manage.pojo.entity.SystemDataSource;
import com.wx.manage.mapper.SystemDataSourceMapper;
import com.wx.manage.pojo.entity.SystemTenantDataSource;
import com.wx.manage.pojo.req.DataSourceCreateVo;
import com.wx.manage.pojo.req.DataSourceUpdateVo;
import com.wx.manage.pojo.resp.DataSourceResp;
import com.wx.manage.result.ResultCodeEnum;
import com.wx.manage.service.SystemDataSourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.manage.service.SystemTenantDataSourceService;
import com.wx.manage.until.JdbcUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Autowired
    private SystemTenantDataSourceService tenantDataSourceService;


    @Override
    public Long createDataSource(DataSourceCreateVo createReqVO) {
        SystemDataSource dataSource = new SystemDataSource();

        dataSource.setName(createReqVO.getName());
        dataSource.setIp(createReqVO.getIp());
        dataSource.setPort(createReqVO.getPort());
        dataSource.setUsername(createReqVO.getUsername());
        dataSource.setPassword(createReqVO.getPassword());
        dataSource.setType(1);

        String url = databaseUrlTemplate.replace("ip", dataSource.getIp()).replace("port", dataSource.getPort());
        dataSource.setUrl(url);

        //查询数据源是否存在
        validateDataSourceExists(null, createReqVO.getIp(), createReqVO.getPort());

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

    @Override
    public Boolean updateDataSource(DataSourceUpdateVo updateReq) {
        SystemDataSource dataSource = getById(updateReq.getId());
        if (dataSource == null) {
            throw new GlobalException(ResultCodeEnum.DATA_SOURCE_CONFIG_NOT_EXISTS);
        }

        //查询数据源是否存在
        validateDataSourceExists(updateReq.getId(), updateReq.getIp(), updateReq.getPort());

        dataSource.setName(updateReq.getName());
        dataSource.setIp(updateReq.getIp());
        dataSource.setPort(updateReq.getPort());
        dataSource.setUsername(updateReq.getUsername());
        dataSource.setPassword(updateReq.getPassword());

        //校验连接
        validateConnectionOK(dataSource);

        return saveOrUpdate(dataSource);
    }

    @Override
    public Boolean deleteDataSource(Long id) {
        //校验数据是否被占用
        validateDataSourceConfigBeUsed(id);

        return removeById(id);
    }

    @Override
    public DataSourceResp getDataSourceDetail(Long id) {
        SystemDataSource dataSource = null;

        // 如果 id 为 0，默认为 master 的数据源
        if (Objects.equals(id, InfraDataSourceConfig.ID_MASTER)) {
            dataSource = buildMasterDataSource();
        } else {
            // 从 DB 中读取
            dataSource = getById(id);
        }

        DataSourceResp resp = new DataSourceResp();
        if (dataSource != null) {
            BeanUtils.copyProperties(dataSource, resp);
        }

        return resp;
    }

    @Override
    public List<DataSourceResp> getDataSourceList() {
        List<SystemDataSource> list = list();
        // 补充 master 数据源
        list.add(0, buildMasterDataSource());

        return list.stream().map(item -> {
            DataSourceResp resp = new DataSourceResp();
            BeanUtils.copyProperties(item, resp);
            return resp;
        }).collect(Collectors.toList());
    }


    /**
     * 校验数据源是否存在
     * @param ip
     * @param port
     */
    private void validateDataSourceExists(Long id, String ip, String port) {
        LambdaQueryWrapper<SystemDataSource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(ip), SystemDataSource::getIp, ip);
        queryWrapper.eq(StringUtils.isNotBlank(port), SystemDataSource::getPort, port);
        queryWrapper.ne(id != null, SystemDataSource::getId, id);
        SystemDataSource dataSource = getOne(queryWrapper);
        if (dataSource != null) {
            throw new GlobalException(ResultCodeEnum.DATA_EXIST_FAIL);
        }
    }


    /**
     * 校验数据源是否被使用
     * @param id
     */
    public void validateDataSourceConfigBeUsed(Long id) {
        LambdaQueryWrapper<SystemTenantDataSource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemTenantDataSource::getDataSourceId, id);
        List<SystemTenantDataSource> list = tenantDataSourceService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            throw new GlobalException(ResultCodeEnum.DATA_SOURCE_IS_USED);
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

        boolean success = JdbcUtils.isConnectionOK(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
        if (!success) {
            throw new GlobalException(ResultCodeEnum.DATA_SOURCE_CONFIG_NOT_OK);
        }
    }


    /**
     * 构建当前数据库
     * @return
     */
    private SystemDataSource buildMasterDataSource() {
        String primary = dynamicDataSourceProperties.getPrimary();
        DataSourceProperty dataSourceProperty = dynamicDataSourceProperties.getDatasource().get(primary);
        return new SystemDataSource().setId(SystemDataSource.ID_MASTER).setName(primary)
                .setUrl(dataSourceProperty.getUrl())
                .setUsername(dataSourceProperty.getUsername())
                .setPassword(dataSourceProperty.getPassword());
    }


}
