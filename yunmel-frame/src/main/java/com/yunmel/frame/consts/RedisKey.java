package com.yunmel.frame.consts;


/**
 * 统一管理redis存储中的key
 * @author taosq
 *
 */
public final class RedisKey {

	/**
	 * 系统相关
	 */
	public static final String ALL_MENU = "menu:all";                //所有菜单以list存储
	public static final String ALL_MENU_URL = "menu:all:url";        //所有菜单地址以list存储，可以考虑使用字符串存储
	public static final String COMMON_MENU_URL = "menu:common:url";  //所有的通用菜单，这类菜单不用做权限控制
	
	public static final String ALL_OFFICE = "office:all";
	public static final String ALL_ROLE = "role:all";
	public static final String ALL_DIC_TYPE="dict:type:all";
	
	public static final String DICT="dict";
	public static final String SYS_CONFIG="config:";
	
	
	/**
	 * 用户相关
	 */
	public static final String ONLINE_USER_LIST = "user:list";
	private static final String USER_SESSION = "user:session";
	private static final String USER_MENU = "user:menu";
	private static final String USER_MENU_URL = "user:menu:url";
	private static final String USER_ROLE = "user:role";
	private static final String USER_DATA_SCOPE = "user:data:scope";
	private static final String USER_OFFICE = "user:office";
	
	/**
	 * 去向相关 
	 */
	public static final String WHERE_ABOUTS_LIST = "where:abouts:list";
	
	
	
	public static String getUserSessionKey(String sessionId){
		return USER_SESSION + ":"+ sessionId;
	}
	
	public static String getUserMenuKey(String userId){
		return USER_MENU + ":" + userId;
	}
	
	public static String getUserMenuUrlKey(String userId){
		return USER_MENU_URL + ":" + userId;
	}
	
	public static String getUserRoleKey(String userId){
		return USER_ROLE + ":" + userId;
	}
	
	public static String getUserDataScopeKey(String userId){
		return USER_DATA_SCOPE + ":" + userId;
	}
	
	public static String getUserOfficeKey(String userId){
		return USER_OFFICE + ":" + userId;
	}
	
	
	public static String getDictKey(String type){
		return DICT + ":" + type;
	}
	
	
	public static String getConfig(String type){
		return SYS_CONFIG + ":" + type;
	}
	
	
}