package com.yunmel.frame.common.utils;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 校验值工具类
 * @author taosq
 *
 */
public final class ValidUtils {
	
	/**
	 * 集成boostrap vilidata
	 * @param result  
	 * @return
	 */
	public static Map<String, Boolean> buildValid(boolean result){
		Map<String, Boolean> maps = Maps.newHashMap();
		maps.put("valid", result);
		return maps;
	}

}
