

package com.yunmel.frame.sys.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yunmel.commons.mapper.BaseMapper;
import com.yunmel.frame.sys.model.SysUser;

/**
 * 
 * @author 
 */

public interface SysUserMapper extends BaseMapper<SysUser>{
	
	public List<SysUser> findPageInfo(Map<String, Object> params);

	public Long findSysUserIdByLoginName(String loginName);

	public List<SysUser> findAllUser();
	
//	public List<SysUser> findByParams(Map<String, Object> map);
	
	public List<SysUser> findUserByStatus(Map<String, Object> map);

  public SysUser getUserByUsername(@Param("username") String username);
	
}
