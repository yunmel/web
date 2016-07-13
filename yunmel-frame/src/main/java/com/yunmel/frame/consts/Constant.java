package com.yunmel.frame.consts;

import java.util.List;

import com.google.common.collect.Lists;

public class Constant {

	// 删除标记（0：正常；1：删除；2：审核；）
	public static final String FIELD_DEL_FLAG = "delFlag";
	public static final String DEL_FLAG_NORMAL = "0";
	public static final String DEL_FLAG_DELETE = "1";
	public static final String DEL_FLAG_AUDIT = "2";
	
	
	// 资源
	public static final String RESOURCE_TYPE_MENU = "0"; // 菜单类型
	public static final String RESOURCE_TYPE_BUTTON = "1"; // 按钮类型
	public static final String RESOURCE_COMMON = "1"; // 公共资源

	// 用户
	public static final Integer USER_STATUS_STOP = 0;//禁用
	public static final Integer USER_STATUS_NORMAL = 1; //正常
	public static final Integer USER_UN_APPROVE = 2; //未审核
	public static final Integer USER_STATUS_LOCK = 3;//锁定
	public static final String SESSION_LOGIN_USER = "loginUser"; // session中的用户key
	public static final String SUPER_ADMIN = "1"; // 超级管理员
	public static final String SUPER_NORMAL = "0"; // 普通用户
	public static final String CACHE_WECHAT_USER = "wechat";
	public static final String SESSION_LOGIN_STUDENT = "loginStudent"; // session中的学生KEY
	public static final String SYS_ADMIN_USER = "admin"; // 超级管理员admin
	

	

	// 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在部门及以下数据；5：所在部门数据；8：仅本人数据；9：按明细设置）
	public static final String DATA_SCOPE_ALL = "1";
	public static final String DATA_SCOPE_SCHOOL = "2";
//	public static final String DATA_SCOPE_COMPANY_AND_CHILD = "2";
//	public static final String DATA_SCOPE_COMPANY = "3";
//	public static final String DATA_SCOPE_OFFICE_AND_CHILD = "4";
//	public static final String DATA_SCOPE_OFFICE = "5";
//	public static final String DATA_SCOPE_SELF = "8";
	public static final String DATA_SCOPE_CUSTOM = "9";
	public static final List<String> DATA_SCOPE_ADMIN = Lists.newArrayList("1","2","9");

	// 显示/隐藏
	public static final String SHOW = "1";
	public static final String HIDE = "0";

	// 是/否
	public static final String YES = "1";
	public static final String NO = "0";
	
	public static final String CHAR_YES = "Y";
	public static final String CHAR_NO = "N";
	
	//update or add or delete
	public static final Integer SUCCESS = 1;
	
	public static final String MSG = "msg";
	
	// 启用/禁用
	public static final String SYSTEM_COMMON_ENABLE = "1";
	public static final String SYSTEM_COMMON_DISABLE = "0";
	
	
	
	/**
	 * 数据库表默认的5个字段
	 */
	public static final List<String> DEFAULT_PROP = Lists.newArrayList("createDate","createBy",
			"updateDate","updateBy","delFlag");
	
	public static final String LOGTYPE_LOGIN  = "0"; //登录日志
    public static final String LOGTYPE_ACCESS = "1"; //操作日志
	public static final String LOGTYPE_EXCEPTION = "2"; //异常日志
	
	
	public static final int APP_FLAG_ENABLE = 1;
	public static final int APP_FLAG_DISABLE = 0;
	
	
	
	public static final String LOCAL_API_USER = "LOCAL_API_USER"; //api用户
	
	
	public static final int API_PARAMS_BASE_TYPE_PROJECT = 1;//工程
	public static final int API_PARAMS_BASE_TYPE_SECTIONS = 2;//标段
	
}
