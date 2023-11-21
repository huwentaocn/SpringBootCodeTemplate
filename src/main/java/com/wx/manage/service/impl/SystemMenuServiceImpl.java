package com.wx.manage.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.annotations.VisibleForTesting;
import com.wx.manage.constant.EnableStatusEnum;
import com.wx.manage.constant.MenuTypeEnum;
import com.wx.manage.exception.GlobalException;
import com.wx.manage.pojo.entity.SystemMenu;
import com.wx.manage.mapper.SystemMenuMapper;
import com.wx.manage.pojo.req.MenuCreateReq;
import com.wx.manage.pojo.req.MenuListReq;
import com.wx.manage.pojo.req.MenuUpdateReq;
import com.wx.manage.pojo.resp.MenuResp;
import com.wx.manage.pojo.resp.MenuSimpleResp;
import com.wx.manage.result.ResultCodeEnum;
import com.wx.manage.service.SystemMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.manage.service.SystemRoleMenuService;
import com.wx.manage.service.SystemTenantService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.wx.manage.pojo.entity.SystemMenu.ID_ROOT;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-07
 */
@Service
public class SystemMenuServiceImpl extends ServiceImpl<SystemMenuMapper, SystemMenu> implements SystemMenuService {

    @Autowired
    private SystemRoleMenuService roleMenuService;

    @Resource
    @Lazy // 延迟，避免循环依赖报错
    private SystemTenantService tenantService;

    @Override
    public Long createMenu(MenuCreateReq createReq) {
        // 校验父菜单存在
        validateParentMenu(createReq.getParentId(), null);
        // 校验菜单（自己）
        validateMenu(createReq.getParentId(), createReq.getName(), null);

        // 插入数据库
        SystemMenu menu = new SystemMenu();
        BeanUtils.copyProperties(createReq, menu);
        initMenuProperty(menu);
        saveOrUpdate(menu);
        // 返回
        return menu.getId();
    }

    @Override
    public Boolean updateMenu(MenuUpdateReq updateReq) {
        // 校验更新的菜单是否存在
        SystemMenu menu = getById(updateReq.getId());
        if (menu == null) {
            throw new GlobalException(ResultCodeEnum.MENU_NOT_EXISTS);
        }
        // 校验父菜单存在
        validateParentMenu(updateReq.getParentId(), updateReq.getId());
        // 校验菜单（自己）
        validateMenu(updateReq.getParentId(), updateReq.getName(), updateReq.getId());

        // 更新到数据库
        BeanUtils.copyProperties(updateReq, menu);
        initMenuProperty(menu);
        return saveOrUpdate(menu);
    }

    @Override
    public Boolean deleteMenu(Long id) {

        // 校验是否还有子菜单
        LambdaQueryWrapper<SystemMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(id != null, SystemMenu::getParentId, id);
        long count = count(queryWrapper);
        if (count > 0) {
            throw new GlobalException(ResultCodeEnum.MENU_EXISTS_CHILDREN);
        }

        // 校验删除的菜单是否存在
        SystemMenu menu = getById(id);
        if (menu == null) {
            throw new GlobalException(ResultCodeEnum.MENU_NOT_EXISTS);
        }

        // 删除授予给角色的权限
        roleMenuService.removeListByMenuId(id);

        //标记删除
        return removeById(id);
    }

    @Override
    public MenuResp getMenuDetail(Long id) {
        SystemMenu menu = getById(id);

        MenuResp resp = new MenuResp();
        BeanUtils.copyProperties(menu, resp);
        return resp;
    }

    @Override
    public List<MenuResp> getMenuList(MenuListReq req) {
        String name = req.getName();
        Integer status = req.getStatus();

        LambdaQueryWrapper<SystemMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name), SystemMenu::getName, name);
        queryWrapper.eq(status != null, SystemMenu::getStatus, status);
        List<SystemMenu> systemMenuList = list(queryWrapper);

        return systemMenuList.stream().map(item -> {
            MenuResp resp = new MenuResp();
            BeanUtils.copyProperties(item, resp);
            return resp;
        }).collect(Collectors.toList());
    }

    @Override
    public List<MenuSimpleResp> getMenuListByTenant() {
        // 获得菜单列表，只要开启状态的
        LambdaQueryWrapper<SystemMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemMenu::getStatus, EnableStatusEnum.ENABLE.getStatus());
        List<SystemMenu> systemMenuList = list(queryWrapper);

        // 开启多租户的情况下，需要过滤掉未开通的菜单
        tenantService.handleTenantMenu(menuIds -> systemMenuList.removeIf(menu -> !CollUtil.contains(menuIds, menu.getId())));

        return systemMenuList.stream().map(item -> {
            MenuSimpleResp resp = new MenuSimpleResp();
            BeanUtils.copyProperties(item, resp);
            return resp;
        }).collect(Collectors.toList());
    }


    /**
     * 校验父菜单是否合法
     *
     * 1. 不能设置自己为父菜单
     * 2. 父菜单不存在
     * 3. 父菜单必须是 {@link MenuTypeEnum#MENU} 菜单类型
     *
     * @param parentId 父菜单编号
     * @param childId 当前菜单编号
     */
    @VisibleForTesting
    void validateParentMenu(Long parentId, Long childId) {
        if (parentId == null || ID_ROOT.equals(parentId)) {
            return;
        }
        // 不能设置自己为父菜单
        if (parentId.equals(childId)) {
            throw new GlobalException(ResultCodeEnum.MENU_PARENT_ERROR);
        }
        SystemMenu parentMenu = getById(parentId);
        // 父菜单不存在
        if (parentMenu == null) {
            throw new GlobalException(ResultCodeEnum.MENU_PARENT_NOT_EXISTS);
        }
        // 父菜单必须是目录或者菜单类型
        if (!MenuTypeEnum.DIR.getType().equals(parentMenu.getType())
                && !MenuTypeEnum.MENU.getType().equals(parentMenu.getType())) {
            throw new GlobalException(ResultCodeEnum.MENU_PARENT_NOT_DIR_OR_MENU);
        }
    }


    /**
     * 校验菜单是否合法
     *
     * 1. 校验相同父菜单编号下，是否存在相同的菜单名
     *
     * @param name 菜单名字
     * @param parentId 父菜单编号
     * @param id 菜单编号
     */
    void validateMenu(Long parentId, String name, Long id) {
        LambdaQueryWrapper<SystemMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(parentId != null, SystemMenu::getParentId, parentId);
        queryWrapper.eq(StringUtils.isNotBlank(name), SystemMenu::getName, name);
        SystemMenu menu = getOne(queryWrapper);
        if (menu == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的菜单
        if (id == null) {
            throw new GlobalException(ResultCodeEnum.MENU_NAME_DUPLICATE);
        }
        if (!menu.getId().equals(id)) {
            throw new GlobalException(ResultCodeEnum.MENU_NAME_DUPLICATE);
        }
    }


    /**
     * 初始化菜单的通用属性。
     *
     * 例如说，只有目录或者菜单类型的菜单，才设置 icon
     *
     * @param menu 菜单
     */
    private void initMenuProperty(SystemMenu menu) {
        // 菜单为按钮类型时，无需 component、icon、path 属性，进行置空
        if (MenuTypeEnum.BUTTON.getType().equals(menu.getType())) {
            menu.setComponent("");
            menu.setComponentName("");
            menu.setIcon("");
            menu.setPath("");
        }
    }
}
