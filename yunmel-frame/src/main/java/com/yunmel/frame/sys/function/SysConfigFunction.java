/**
 * 
 */
package com.yunmel.frame.sys.function;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.yunmel.frame.sys.service.SysConfigService;

/**
 * @author chenxd
 * @date 09/18/2015
 */
@Component
public class SysConfigFunction {

	@Resource
	private SysConfigService sysConfigService;

	public String get(String key) {
		return sysConfigService.findByKeyFromRedis(key);
	}
}
