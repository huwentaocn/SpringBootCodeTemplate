//package com.wx.manage.until;
//
//import com.wx.manage.aop.DataPermission;
//import com.wx.manage.config.datapermission.DataPermissionContextHolder;
//import lombok.SneakyThrows;
//
///**
// * @Description 数据权限工具类
// * @Date 2023/11/10 10:40 星期五
// * @Author Hu Wentao
// */
//public class DataPermissionUtils {
//
//    private static DataPermission DATA_PERMISSION_DISABLE;
//
//    @DataPermission(enable = false)
//    @SneakyThrows
//    private static DataPermission getDisableDataPermissionDisable() {
//        if (DATA_PERMISSION_DISABLE == null) {
//            DATA_PERMISSION_DISABLE = DataPermissionUtils.class
//                    .getDeclaredMethod("getDisableDataPermissionDisable")
//                    .getAnnotation(DataPermission.class);
//        }
//        return DATA_PERMISSION_DISABLE;
//    }
//
//    /**
//     * 忽略数据权限，执行对应的逻辑
//     *
//     * @param runnable 逻辑
//     */
//    public static void executeIgnore(Runnable runnable) {
//        DataPermission dataPermission = getDisableDataPermissionDisable();
//        DataPermissionContextHolder.add(dataPermission);
//        try {
//            // 执行 runnable
//            runnable.run();
//        } finally {
//            DataPermissionContextHolder.remove();
//        }
//    }
//
//}
