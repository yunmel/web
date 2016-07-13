package com.yunmel.frame.sys.function;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.yunmel.frame.sys.model.SysDict;
import com.yunmel.frame.sys.service.SysDictService;

/**
 * 字典方法
 * 传入参数 type
 */
@Component
public class DictFunction{
	
	@Resource
	private SysDictService sysDictService;
	
	/**
	 * 根据类型和键值得到字典实体
	* @param type 如sys_data_scope等
	* @param value 
	 */
	public SysDict getDictByTypeAndValue(String type,String value){
		List<SysDict> dics = sysDictService.findByTypeFromRedis(type);
		SysDict d = null;
		for (SysDict dic : dics) {
			if(dic.getValue().equals(value)){
				d =  dic;
				break;
			}
		}
		//如果为空，有可能是已经禁用，直接从数据库查询
		if(d == null){
			d = sysDictService.findByTypeAndValue(type,value);
		}
		return d;
	}
	
	/**
	 * 根据类型得到字典列表
	* @param type 如sys_data_scope等
	 */
	public List<SysDict> getDictListByType(String type){
		return sysDictService.findByTypeFromRedis(type);
	}
	

}
