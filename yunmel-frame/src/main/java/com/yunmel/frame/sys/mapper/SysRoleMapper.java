

package com.yunmel.frame.sys.mapper;

import java.util.List;
import java.util.Map;

import com.yunmel.commons.mapper.BaseMapper;
import com.yunmel.frame.sys.model.SysMenu;
import com.yunmel.frame.sys.model.SysRole;
import com.yunmel.frame.sys.model.SysUser;

/**
 * 
 * @author
 */

public interface SysRoleMapper extends BaseMapper<SysRole> {

	public int insertRoleOffice(SysRole sysRole);

	public int insertRoleResource(SysRole sysRole);

	public int insertUserRoleByRoleId(SysRole sysRole);

	public int insertUserRoleByUserId(SysUser sysUser);

	public int deleteRoleOfficeByRoleId(String roleId);

	public int deleteRoleResourceByRoleId(String roleId);

	public int deleteUserRoleByRoleId(String roleId);

	public int deleteUserRoleByUserId(String userId);

	public List<String> findOfficeIdsByRoleId(String roleId);

	public List<String> findResourceIdsByRoleId(String roleId);
	
	public List<String> findUserIdsByRoleId(String userId);

	public List<SysMenu> findResourceByRoleId(String roleId);

	public List<SysUser> findUserByRoleId(String roleId);

	public List<SysRole> findUserRoleListByUserId(String userId);

	public List<SysRole> findPageInfo(Map<String, Object> params);

}
