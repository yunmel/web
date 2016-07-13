

package com.yunmel.frame.sys.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yunmel.commons.mapper.BaseMapper;
import com.yunmel.frame.sys.model.SysMenu;

/**
 * 
 * @author 
 */

public interface SysMenuMapper extends BaseMapper<SysMenu>{
	
	public int updateParentIds(SysMenu sysMenu);
	
	public int deleteIdsByRootId(String id);
   
	public List<SysMenu> findPageInfo(Map<String, Object> params);
	
	//删除前验证
	public int beforeDeleteMenu(String resourceId);
	
	//根据userId获得持有的权限
	public List<SysMenu> findUserMenuByUserId(String userId);

	//更新状态
	public int updateStatusByRootId(@Param("id") String id, @Param("status") String status);
	
}
