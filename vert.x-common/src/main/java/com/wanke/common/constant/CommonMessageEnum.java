//package com.wanke.common.constant;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//
///**
// * @author  ldd
// */
//@AllArgsConstructor
//public enum CommonMessageEnum {
//
//    /**
//     * 响应消息枚举列表
//     */
//
//
//    SUCCESS("操作成功","10000"),
//    FAIL("操作失败","10001"),
//    USER_IS_NULL("用户不存在","11001"),
//    USER_PASS_ERR("用户密码错误","11002"),
//    USER_NO_EFFECT("用户失效","11003"),
//    USER_NO_SESSION("用户登录状态失效","11004"),
//    USER_ROLE_REPE("系统角色名已存在","11005"),
//    USER_REPE("用户名或手机号已存在","11006"),
//    SERVERERR("服务异常！","20000"),
//    NO_PERMISION("您没有权限执行此操作","50000"),
//    PARAM_LOST("参数缺失","10008"),
//    ILLEGAL_PARAM("非法参数","ILLEGAL_PARAM"),
//    DATA_IS_EMPTY("数据为空","DATA_IS_EMPTY"),
//    PAGE_IS_EMPTY("分页数据为空","PAGE_IS_EMPTY"),
//    PRI_ID_IS_EMPT("主键ID为空","PAGE_IS_EMPTY"),
//    TYPE_IS_EMPTY("类型为空","TYPE_IS_EMPTY"),
//
//    USER_IS_EXIST("用户已存在","USER_IS_EXIST"),
//    FILE_FORMAT_ERROR("仅支持.jpg .jpeg .png格式","FILE_FORMAT_ERROR"),
//    FILE_EMPTY("文件缺失","FILE_EMPTY"),
//    FILE_FORMAT("文件内容不匹配","FILE_FORMAT"),
//    PASSWORD_ERROR("密码错误","PASSWORD_ERROR"),
//    USER_NOT_ALLOW_LOGIN("当前用户禁止登录,请联系管理员", "USER_NOT_ALLOW_LOGIN"),
//    USER_NOT_LOGIN("用户未登陆", "USER_NOT_LOGIN"),
//    USER_LOCK_ALLOW_LOGIN("当前用户已被锁定,请联系管理员", "USER_LOCK_ALLOW_LOGIN"),
//    RECORD_EMPTY("无录音文件","RECORD_EMPTY"),
//    LOGIN_SUCCESS("登陆成功","LOGIN_SUCCESS"),
//    GET_MENU_LIST_FAIL("获取菜单列表失败","GET_MENU_LIST_FAIL"),
//    INSERT_FAILURE("增加失败","INSERT_FAILURE"),
//    UPDATE_FAILURE("更改失败","UPDATE_FAILURE"),
//    RECORD_IS_NULL("数据不存在","RECORD_IS_NULL"),
//    PARAMETER_ERROR("参数列表字段有空值","PARAMETER_ERROR"),
//    ID_IS_NULL("主键为空","ID_IS_NULL"),
//    UNKNOWN_ERROR("未知错误","UNKNOWN_ERROR"),
//    NO_PERMISSION("您没有权限,请勿操作！","NO_PERMISSION")
//
//    ;
//    private @Getter
//    String msg;
//    private @Getter
//    String code;
//
//}
