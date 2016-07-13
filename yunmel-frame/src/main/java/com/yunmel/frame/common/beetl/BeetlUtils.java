package com.yunmel.frame.common.beetl;

import org.beetl.core.GroupTemplate;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;

import com.yunmel.frame.common.spring.SpringContextHolder;


public class BeetlUtils {
	
	private static final BeetlGroupUtilConfiguration beetlConfig = SpringContextHolder.getBean("beetlConfig");

	/**
	 * 获得beetl模板对象
	* @return
	 */
	public static final GroupTemplate getBeetlGroupTemplate(){
		return beetlConfig.getGroupTemplate();
	}
}
