package com.yunmel.frame.sys.function;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.yunmel.frame.common.utils.SysUserUtils;
import com.yunmel.frame.consts.Constant;
import com.yunmel.frame.sys.model.SysUser;
import com.yunmel.frame.sys.service.SysMenuService;

@Component
public class AuthUserFunctions {

	@Resource
	private SysMenuService sysMenuService;
	/**
	 * 判断用户是否具有指定权限
	 */
	public boolean hasPermission(String url) {
		SysUser user = SysUserUtils.getCacheLoginUser();
		if(user.isAdmin()){
			return true;
		}
		List<String> allRes = sysMenuService.getCommonUrlFromRedis();
		if (allRes.contains(url)) {
			return true;
		}

		List<String> userRes = sysMenuService.findUrlByUserFromRedis(user);
		if (userRes.contains(url)) return true;
		return false;
	}
	
	/**
	 * 判断用户是否具有指定权限
	 */
	public  boolean hasPermissions(String... urls) {
		SysUser user = SysUserUtils.getCacheLoginUser();
		if(user.isAdmin()){
			return true;
		}
		
		List<String> allRes = sysMenuService.getCommonUrlFromRedis();
		List<String> userRes = sysMenuService.findUrlByUserFromRedis(user);
		
		for(int i = 0; i < urls.length; i++){
			if (allRes.contains(urls[i])) {
				return true;
			}
			
			if (userRes.contains(urls[i])) return true;
		}
		return false;
	}
	
	
	/**
	 * 登录用户
	* @return
	 */
	public SysUser getLoginUser(){
		return SysUserUtils.getCacheLoginUser();
	}
	
	/**
	 * 是否为超级管理员
	* @return
	 */
	public boolean isSuper(){
		return getLoginUser().getUserType().equals(Constant.SUPER_ADMIN)?true:false;
	}
	
	
	/**
	 * 是否持有所有数据(数据范围，可认为是总管理)
	 */
	public boolean hasAllDataScope(){
		return SysUserUtils.getUserDataScope().contains(Constant.DATA_SCOPE_ALL);
	}

}
