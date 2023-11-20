package com.wx.manage.mapper;

import com.baomidou.dynamic.datasource.annotation.Master;
import com.wx.manage.pojo.entity.WxUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-08-31
 */
@Mapper
@Master
public interface WxUserMapper extends BaseMapper<WxUser> {

}
