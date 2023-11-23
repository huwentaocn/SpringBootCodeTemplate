package com.wx.manage.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.dynamic.datasource.annotation.Master;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.manage.config.tenant.aop.TenantDS;
import com.wx.manage.constant.EnableStatusEnum;
import com.wx.manage.exception.GlobalException;
import com.wx.manage.pojo.entity.SystemTenant;
import com.wx.manage.pojo.entity.SystemUsers;
import com.wx.manage.mapper.SystemUsersMapper;
import com.wx.manage.pojo.page.PageResult;
import com.wx.manage.pojo.req.UserCreateReq;
import com.wx.manage.pojo.req.UserPageReq;
import com.wx.manage.pojo.req.UserUpdatePasswordReq;
import com.wx.manage.pojo.req.UserUpdateReq;
import com.wx.manage.pojo.resp.TenantResp;
import com.wx.manage.pojo.resp.UserResp;
import com.wx.manage.pojo.resp.UserSimpleResp;
import com.wx.manage.result.ResultCodeEnum;
import com.wx.manage.service.SystemTenantService;
import com.wx.manage.service.SystemUserRoleService;
import com.wx.manage.service.SystemUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-07
 */
@Service
@TenantDS
public class SystemUsersServiceImpl extends ServiceImpl<SystemUsersMapper, SystemUsers> implements SystemUsersService {

    @Autowired
    private SystemTenantService tenantService;

    @Autowired
    private SystemUserRoleService userRoleService;

    @Autowired
    private SystemUsersMapper usersMapper;

    /**
     * 更新用户登录数据
     * @param userId
     * @param loginIp
     */
    @Override
    public void updateUserLogin(Long userId, String loginIp) {
        SystemUsers systemUsers = new SystemUsers();
        systemUsers.setId(userId);
        systemUsers.setLoginIp(loginIp);
        updateById(systemUsers);
    }

    @Override
    @DSTransactional // 多租户多 DB 场景：使用 @DSTransactional 实现事务，避免 @Transactional 无法切换数据源
    public Long createUser(UserCreateReq createReq) {
        // 校验账户配合
        tenantService.handleTenantInfo(tenant -> {
            long count = count();
            if (count >= tenant.getAccountCount()) {
                throw new GlobalException(ResultCodeEnum.USER_COUNT_MAX, tenant.getAccountCount());
            }
        });

        // 校验正确性
        validateUserForCreateOrUpdate(null, createReq.getUsername(), createReq.getMobile(), createReq.getEmail(),
                createReq.getDeptId(), createReq.getPostIds());

        //插入用户
        SystemUsers user = new SystemUsers();
        BeanUtils.copyProperties(createReq, user);
        user.setStatus(EnableStatusEnum.ENABLE.getStatus());
        saveOrUpdate(user);

        return user.getId();
    }

    private void validateUserForCreateOrUpdate(Long id, String username, String mobile, String email,
                                               Long deptId, Set<Long> postIds) {
        // 关闭数据权限，避免因为没有数据权限，查询不到数据，进而导致唯一校验不正确
//        DataPermissionUtils.executeIgnore(() -> {
            // 校验用户存在
            validateUserExists(id);
            // 校验用户名唯一
            validateUsernameUnique(id, username);
            // 校验手机号唯一
            validateMobileUnique(id, mobile);
            // 校验邮箱唯一
            validateEmailUnique(id, email);
//            // 校验部门处于开启状态
//            deptService.validateDeptList(CollectionUtils.singleton(deptId));
//            // 校验岗位处于开启状态
//            postService.validatePostList(postIds);
//        });
    }

    @Override
    @DSTransactional // 多租户多 DB 场景：使用 @DSTransactional 实现事务，避免 @Transactional 无法切换数据源
    public Boolean updateUser(UserUpdateReq updateReq) {
        // 校验正确性
        validateUserForCreateOrUpdate(updateReq.getId(), updateReq.getUsername(), updateReq.getMobile(), updateReq.getEmail(),
                updateReq.getDeptId(), updateReq.getPostIds());
        //更新用户
        SystemUsers user = new SystemUsers();
        BeanUtils.copyProperties(updateReq, user);
        return saveOrUpdate(user);
    }

    @Override
    public Boolean updateUserPassword(UserUpdatePasswordReq updatePasswordReq) {
        // 校验用户存在
        validateUserExists(updatePasswordReq.getId());

        //重置密码
        SystemUsers user = new SystemUsers();
        user.setId(updatePasswordReq.getId());
        user.setPassword(updatePasswordReq.getPassword());
        return saveOrUpdate(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteUser(Long id) {
        // 校验用户存在
        validateUserExists(id);

        // 删除用户关联数据
        userRoleService.removeListByUserId(id);

        // 删除用户
        return removeById(id);
    }

    @Override
    public UserResp getUserDetail(Long id) {
        SystemUsers user = getById(id);
        UserResp userResp = new UserResp();
        BeanUtils.copyProperties(user, userResp);
        return userResp;
    }

    @Override
    public List<UserSimpleResp> getSimpleUserList() {
        LambdaQueryWrapper<SystemUsers> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemUsers::getStatus, EnableStatusEnum.ENABLE.getStatus());
        List<SystemUsers> list = list(queryWrapper);

        //构造返回对象
        List<UserSimpleResp> userSimpleRespList = list.stream().map(item -> {
            UserSimpleResp userSimpleResp = new UserSimpleResp();
            userSimpleResp.setId(item.getId());
            userSimpleResp.setNickname(item.getNickname());
            return userSimpleResp;
        }).collect(Collectors.toList());
        return userSimpleRespList;
    }

    @Override
    public List<UserResp> getUserList() {
        List<SystemUsers> usersList = list();

        return usersList.stream().map(item -> {
            UserResp userResp = new UserResp();
            BeanUtils.copyProperties(item, userResp);
            return userResp;
        }).collect(Collectors.toList());
    }

    @Override
    public PageResult<UserResp> getUserPage(UserPageReq req) {
        LambdaQueryWrapper<SystemUsers> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(req.getUsername()), SystemUsers::getUsername, req.getUsername())
                .like(StringUtils.isNotBlank(req.getMobile()), SystemUsers::getMobile, req.getMobile())
                .eq(req.getStatus() != null, SystemUsers::getStatus, req.getStatus())
//                .between(req.getCreateTime() != null, SystemTenant::getCreateTime, req.getCreateTime()[0], req.getCreateTime()[1])
                .eq(req.getDeptId() != null, SystemUsers::getDeptId, req.getDeptId())
                .orderByDesc(SystemUsers::getId);

        Page<SystemUsers> page = new Page<>(req.getPageNo(), req.getPageSize());
        Page<SystemUsers> systemUsersPage = usersMapper.selectPage(page, queryWrapper);
        List<UserResp> userRespList = systemUsersPage.getRecords().stream().map(item -> {
            UserResp userResp = new UserResp();
            BeanUtils.copyProperties(item, userResp);
            return userResp;
        }).collect(Collectors.toList());

        return new PageResult<>(systemUsersPage, userRespList);
    }

    /**
     * 校验用户存在
     * @param id
     */
    void validateUserExists(Long id) {
        if (id == null) {
            return;
        }
        SystemUsers user = getById(id);
        if (user == null) {
            throw new GlobalException(ResultCodeEnum.USER_NOT_EXISTS);
        }
    }

    /**
     * 校验用户名唯一
     * @param id
     * @param username
     */
    void validateUsernameUnique(Long id, String username) {
        if (StrUtil.isBlank(username)) {
            return;
        }
        LambdaQueryWrapper<SystemUsers> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemUsers::getUsername, username);
        SystemUsers user = getOne(queryWrapper);
        if (user == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw new GlobalException(ResultCodeEnum.USER_USERNAME_EXISTS);
        }
        if (!user.getId().equals(id)) {
            throw new GlobalException(ResultCodeEnum.USER_USERNAME_EXISTS);
        }
    }

    /**
     * 校验手机号码唯一
     * @param id
     * @param mobile
     */
    void validateMobileUnique(Long id, String mobile) {
        if (StrUtil.isBlank(mobile)) {
            return;
        }
        LambdaQueryWrapper<SystemUsers> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemUsers::getMobile, mobile);
        SystemUsers user = getOne(queryWrapper);
        if (user == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw new GlobalException(ResultCodeEnum.USER_MOBILE_EXISTS);
        }
        if (!user.getId().equals(id)) {
            throw new GlobalException(ResultCodeEnum.USER_MOBILE_EXISTS);
        }
    }

    /**
     * 校验邮箱唯一
     * @param id
     * @param email
     */
    void validateEmailUnique(Long id, String email) {
        if (StrUtil.isBlank(email)) {
            return;
        }
        LambdaQueryWrapper<SystemUsers> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemUsers::getEmail, email);
        SystemUsers user = getOne(queryWrapper);
        if (user == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw new GlobalException(ResultCodeEnum.USER_EMAIL_EXISTS);
        }
        if (!user.getId().equals(id)) {
            throw new GlobalException(ResultCodeEnum.USER_EMAIL_EXISTS);
        }
    }

    /**
     * 校验旧密码
     * @param id          用户 id
     * @param oldPassword 旧密码
     */
    void validateOldPassword(Long id, String oldPassword) {
        SystemUsers user = getById(id);
        if (user == null) {
            throw new GlobalException(ResultCodeEnum.USER_NOT_EXISTS);
        }
        if (!user.getPassword().equals(oldPassword)) {
            throw new GlobalException(ResultCodeEnum.USER_PASSWORD_FAILED);
        }
    }
}
