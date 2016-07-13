

package com.yunmel.frame.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yunmel.commons.mapper.BaseMapper;
import com.yunmel.frame.sys.model.SysDictType;

/**
 * 
 * @author taosq
 */

public interface SysDictTypeMapper extends BaseMapper<SysDictType>{
	
	public int updateParentIds(SysDictType sysArea);
	
	public int deleteDictTypeByRootId(String id);

	public List<SysDictType> findPageInfo(Map<String, Object> params);
	
	public List<SysDictType> findSysDictTypeByCode(Map<String, Object> params);

	public int updateStatusByRootId(@Param("id") String id, @Param("status") String status);

	public List<SysDictType> findByParentCode(String code);
	
}
