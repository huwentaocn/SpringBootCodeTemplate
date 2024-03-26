package com.hwt.sbct.mapper;

import com.hwt.sbct.pojo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author Hu Wentao
 * @since 2024-03-16
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
