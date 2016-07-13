package com.yunmel.frame.common.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.yunmel.commons.utils.ThreadLocalUtils;
import com.yunmel.frame.common.utils.SysUserUtils;
import com.yunmel.frame.sys.model.SysMenu;
import com.yunmel.frame.sys.model.SysRole;
import com.yunmel.frame.sys.model.SysUser;
import com.yunmel.frame.sys.service.SysRoleService;
import com.yunmel.frame.sys.service.SysUserService;

public class ShiroDbRealm extends AuthorizingRealm {
	private static Logger LOGGER = LoggerFactory.getLogger(ShiroDbRealm.class);
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysRoleService sysRoleService; //= SpringUtils.getBean(SysRoleService.class);

	/**
	 * Shiro登录认证(原理：用户提交 用户名和密码 --- shiro 封装令牌 ---- realm 通过用户名将密码查询返回 ----
	 * shiro 自动去比较查询出密码和用户输入密码是否一致---- 进行登陆控制 )
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		LOGGER.info("Shiro开始登录认证");
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		SysUser user = sysUserService.findUserByUsername(token.getUsername());
		// 账号不存在
		if (user == null) {
			return null;
		}
		// 账号未启用
		if (user.getStatus() == 0) {
			return null;
		}
		SysUserUtils.cacheUser(user);
		ThreadLocalUtils.set("user", user.getId() + "," + user.getName());
		List<SysRole> roleList = sysRoleService.findUserRoleListByUser(user);
		List<String> roleIds = Lists.newArrayList();
		for (SysRole r : roleList) {
			roleIds.add(r.getId());
		}
		ShiroUser shiroUser = new ShiroUser(user.getId(), user.getUsername(), user.getName(), roleIds);
		// 认证缓存信息
		return new SimpleAuthenticationInfo(shiroUser, user.getPassword().toCharArray(), getName());
	}

	/**
	 * Shiro权限认证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		List<String> roleList = shiroUser.roleList;
		Set<String> urlSet = new HashSet<String>();
		for (String roleId : roleList) {
			List<SysMenu> roleResourceList = sysRoleService.findResourceByRoleId(roleId);
			if (roleResourceList != null) {
				for (SysMenu map : roleResourceList) {
					if (StringUtils.isNoneBlank(map.getUrl())) {
						urlSet.add(map.getUrl());
					}
				}
			}
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermissions(urlSet);
		return info;
	}
}
