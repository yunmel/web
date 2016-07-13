

package com.yunmel.frame.sys.mapper;

import java.util.List;
import java.util.Map;

import com.yunmel.commons.mapper.BaseMapper;
import com.yunmel.frame.sys.model.SysDict;

/**
 * 
 * @author 
 */

public interface SysDictMapper extends BaseMapper<SysDict>{

	List<SysDict> findDict();

	List<SysDict> findPageInfo(Map<String, Object> params);

	public Integer checkDictValue(Map<String, Object> map);

}
