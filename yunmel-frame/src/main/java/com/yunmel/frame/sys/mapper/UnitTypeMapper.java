package com.yunmel.frame.sys.mapper;

import java.util.List;
import java.util.Map;

import com.yunmel.commons.mapper.BaseMapper;
import com.yunmel.frame.sys.model.UnitType;


/**
* @author yunmel
*/
public interface UnitTypeMapper extends BaseMapper<UnitType>{

	public List<UnitType> findPageInfo(Map<String, Object> params); 
	
}
