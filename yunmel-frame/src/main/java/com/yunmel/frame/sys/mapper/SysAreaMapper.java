package com.yunmel.frame.sys.mapper;

import java.util.List;
import java.util.Map;

import com.yunmel.commons.mapper.BaseMapper;
import com.yunmel.frame.sys.model.SysArea;

/**
 * 
 * @author 
 */

public interface SysAreaMapper extends BaseMapper<SysArea>{
	
	public int updateParentIds(SysArea sysArea);
	
	public int deleteAreaByRootId(String id);

	public List<SysArea> findSysAreaList(Map<String, Object> params);
	
}
