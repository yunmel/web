

package com.yunmel.frame.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.yunmel.commons.service.BaseService;
import com.yunmel.commons.service.RedisService;
import com.yunmel.frame.consts.Constant;
import com.yunmel.frame.consts.RedisKey;
import com.yunmel.frame.sys.mapper.SysDictMapper;
import com.yunmel.frame.sys.model.SysArea;
import com.yunmel.frame.sys.model.SysDict;
import com.yunmel.frame.sys.model.SysOffice;
import com.yunmel.frame.sys.model.SysRole;
import com.yunmel.frame.sys.vo.SysDictVO;

/**
 * 
 * @author
 */

@Service("sysDictService")
public class SysDictService extends BaseService<SysDict> {
	private static final Logger LOG = LoggerFactory.getLogger(SysDictService.class);
	
	@Resource
	private SysDictMapper sysDictMapper;

	@Resource
	private RedisService redisService;
	
	/**
	 * 保存或更新
	 * @param sysDict
	 * @return
	 */
	public int saveDict(SysDict sysDict) {
		if(sysDict.getStatus() == null){
			sysDict.setStatus(Constant.SYSTEM_COMMON_ENABLE);
		}
//		int c = 0;
//		if(sysDict.getId()==null){
//		  c = this.insertSelective(sysDict);
//		}else{
//		  c = this.updateByPrimaryKeySelective(sysDict);
//		}
		
		int c =  this.save(sysDict);
		if(c > 0){
			putRedis();
		}
		return c;
	}

	/**
	 * 删除
	* @param sysDict
	* @return
	 */
	public int deleteSysDict(SysDict sysDict) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", sysDict.getValue());
		if(sysDict.getType().equals("sys_area_type")){
			int areaCount = this.beforeDelete(SysArea.class,params);
			if(areaCount<0) return -1;
		}
		if(sysDict.getType().equals("sys_office_type")){
			int officeCount = this.beforeDelete(SysOffice.class,params);
			if(officeCount<0) return -1;
		}
		if(sysDict.getType().equals("sys_data_scope")){
			int roleCount = this.beforeDelete(SysRole.class, params);
			if(roleCount<0) return -1;
		}
		return this.updateDelFlagToDelStatusById(SysDict.class, sysDict.getId());
	}


	//根据类型从redis获取
	public List<SysDict> findByTypeFromRedis(String type){
		String json = redisService.get(RedisKey.getDictKey(type));
		if(StringUtils.isNotBlank(json)){
			return (List<SysDict>) JSON.parseArray(json,SysDict.class);
		}else{
			SysDict sd = new SysDict();
			sd.setType(type);
			sd.setStatus(Constant.SYSTEM_COMMON_ENABLE);
			return this.select(sd,"sort");
		}
	}
	
	public String findJsonByTypeFromRedis(String type) {
		String json = redisService.get(RedisKey.getDictKey(type));
		if(StringUtils.isNotBlank(json)){
			return json;
		}else{
			SysDict sd = new SysDict();
			sd.setType(type);
			sd.setStatus(Constant.SYSTEM_COMMON_ENABLE);
			return JSONUtils.toJSONString(this.select(sd,"sort"));
		}
	}
	
	

	public String getValue(String type,String label){
		List<SysDict> sysdicts = (List<SysDict>) findByTypeFromRedis(type);
		for (SysDict sysDict : sysdicts) {
			if(sysDict.getLabel().equals(label)){
				return sysDict.getValue();
			}
		}
		return null;
	}
	
	public String getLabel(String type,String value){
		List<SysDict> sysdicts = (List<SysDict>) findByTypeFromRedis(type);
		for (SysDict sysDict : sysdicts) {
			if(sysDict.getValue().equals(value)){
				return sysDict.getLabel();
			}
		}
		return null;
	}
	
	
	
	
	public List<SysDict> find(Map<String, Object> params) {
		return sysDictMapper.findPageInfo(params);
	}
	
	private List<SysDict> getAllEnable() {
		SysDict sd = new SysDict();
		sd.setStatus(Constant.SYSTEM_COMMON_ENABLE);
		return this.select(sd,"sort");
	}

	public void putRedis() {
		try{
			List<SysDict> sds = getAllEnable();
			List<SysDict> dictList = this.select(new SysDict());
			Multimap<String, SysDict> multimap = ArrayListMultimap.create();
			for(SysDict dict : dictList){
				multimap.put(dict.getType(), dict);
			}
			for (String type : multimap.keys()) {
				redisService.set(RedisKey.getDictKey(type), JSONUtils.toJSONString(multimap.get(type)));
			}
		}catch (Exception e) {
			LOG.error("redis 放入数据字典数据出错",e);
		}
		
	}

	

	public Integer deleteDict(String id) {
		return deleteDict(id,true);
	}

	public Integer deleteDict(String[] ids) {
		int c = 0;
		for (String id : ids) {
			c += deleteDict(id,false);
		}
		if(c > 0){
			putRedis();
		}
		return c;
	}

	
	private Integer deleteDict(String id,boolean updateReis){
		int count = this.updateDelFlagToDelStatusById(SysDict.class,id);
		if(count > 0 && updateReis){
			putRedis();
		}
		return count;
	}
	
	
	public Integer updateSort(String id, Integer sort) {
		SysDict sd = new SysDict();
		sd.setId(id);
		sd.setSort(sort);
		return this.updateByPrimaryKeySelective(sd);
	}
	

	public Integer updateStauts(String[] ids, String status) {
		int c = 0;
		for (String id : ids) {
			c += updateStauts(id, status, false);
		}
		
		if(c > 0){
			putRedis();
		}
		return c;
	}
	

	public Integer updateStauts(String id, String status) {
		return updateStauts(id,status,true);
	}
	
	private Integer updateStauts(String id, String status,boolean updateReids){
		if(Constant.SYSTEM_COMMON_ENABLE.equals(status) 
				||Constant.SYSTEM_COMMON_DISABLE.equals(status)){
			SysDict sd = new SysDict();
			sd.setId(id);
			sd.setStatus(status);
			int c = this.updateByPrimaryKeySelective(sd);
			if(c > 0 && updateReids){
				putRedis();
			}
			return c;
		}else {
			return 0;
		}
	}

	public SysDict findByTypeAndValue(String type, String value) {
		SysDict sd = new SysDict();
		sd.setType(type);
		sd.setValue(value);
		List<SysDict> sds = this.find(sd);
		if(!sds.isEmpty()){
			return sds.get(0);
		}
		return null;
	}

	public Integer addDict(SysDictVO sysDict, String type) {
		if(type == null || sysDict == null || sysDict.getSysDict().isEmpty()){
			return 0;
		}
		int c = 0;
		for (SysDict stu : sysDict.getSysDict()) {
			if(StringUtils.isNotBlank(stu.getLabel()) && StringUtils.isNotBlank(stu.getValue())){
				stu.setDelFlag(Constant.DEL_FLAG_NORMAL);
				stu.setStatus(Constant.SYSTEM_COMMON_ENABLE);
				stu.setType(type);
				c += this.insertSelective(stu);
			}
		}
		return c;
	}

	/**
	 * 检测数据词典同一分类下，是否存在要校验的值
	 * @param field   要检测的字段
	 * @param value   要检测值
	 * @param type    词典分类
	 * @param id 
	 * @return
	 */
	public boolean checkDictValue(String field, String value, String type, String id) {
		if(type==null || type.equals("")) return false;
		
		Map<String, Object> map = Maps.newHashMap();
		map.put("field", field);
		map.put("value", value);
		map.put("type", type);
		if(id!=null && !"".equals(id)){
			map.put("id", id+"");
		}
		
		int count = this.sysDictMapper.checkDictValue(map);
		return count <= 0 ? true : false;
	}


}
