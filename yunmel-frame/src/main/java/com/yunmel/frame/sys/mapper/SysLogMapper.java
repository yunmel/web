

package com.yunmel.frame.sys.mapper;

import java.util.List;
import java.util.Map;

import com.yunmel.commons.mapper.BaseMapper;
import com.yunmel.frame.sys.model.SysLog;

/**
 * 
 * @author 
 */

public interface SysLogMapper extends BaseMapper<SysLog> {


	public List<SysLog> findSysLogList(Map<String, Object> params);

	public List<Map<String, Object>> selectList(Map<String, Object> params);
   
}
