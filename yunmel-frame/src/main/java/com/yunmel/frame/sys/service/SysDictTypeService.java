

package com.yunmel.frame.sys.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yunmel.commons.service.BaseService;
import com.yunmel.commons.service.RedisService;
import com.yunmel.commons.utils.StrUtils;
import com.yunmel.frame.consts.Constant;
import com.yunmel.frame.consts.RedisKey;
import com.yunmel.frame.sys.mapper.SysDictMapper;
import com.yunmel.frame.sys.mapper.SysDictTypeMapper;
import com.yunmel.frame.sys.model.SysDict;
import com.yunmel.frame.sys.model.SysDictType;
import com.yunmel.frame.sys.vo.SysDictTypeVO;

/**
 * 
 * @author taosq
 */

@Service("sysDictTypeService")
public class SysDictTypeService extends BaseService<SysDictType>{
	private final static Logger LOG = LoggerFactory.getLogger(SysMenuService.class);
	
	@Resource
	private SysDictTypeMapper sysDictTypeMapper;
	
	@Resource
	private SysDictMapper sysDictMapper;
	
	@Resource
	private RedisService redisService;
	
	@Resource
	private SysDictService sysDictService;
	
	/**
	 *新增or更新SysArea
	 */
	
	public int saveDicType(SysDictType dictType){
		int count = 0;
		//新的parentIds
		dictType.setParentIds(dictType.getParentIds()+dictType.getParentId()+","); 
		if(null == dictType.getId()){
			count = this.insertSelective(dictType);
		}else{
			//getParentIds() 当前选择的父节点parentIds , getParentId()父节点的id
			//先更新parentId，此节点的parentIds以更新
			count = this.updateByPrimaryKeySelective(dictType); 
			//不移动节点不更新子节点的pids
			if(!StringUtils.equals(dictType.getOldParentIds(), dictType.getParentIds())){
				sysDictTypeMapper.updateParentIds(dictType); //批量更新子节点的parentIds
			}
		}
		
		if(count > 0){
			putRedis();
			sysDictService.putRedis();
		}
		return count;
	}
	
	
	public int deleteDictTypeByRootId(String id){
		return deleteDictTypeByRootId(id, true);
	}
	
	public int deleteDictTypeByRootId(String[] ids){
		int c = 0;
		for (String id : ids) {
			c += deleteDictTypeByRootId(id, false);
		}
		if(c > 0){
			putRedis();
			sysDictService.putRedis();
		}
		return c;
	}
	
	
	
	/**
	 * 根据父id删除自身已经所有子节点
	* @param id
	* @return
	 */
	private int deleteDictTypeByRootId(String id,boolean updateRedis){
		//先删除相关字典 
		String  type = sysDictTypeMapper.selectByPrimaryKey(id).getCode();
		
		SysDict sd = new  SysDict();
		sd.setType(type);
		sd.setDelFlag(Constant.DEL_FLAG_DELETE);
		
		int dCount = sysDictMapper.updateByPrimaryKeySelective(sd);
		if(dCount > 0 && updateRedis){
			sysDictService.putRedis();
		}
		
		int typeCount =  sysDictTypeMapper.deleteDictTypeByRootId(id);
		if(typeCount > 0 && updateRedis){
			putRedis();
		}
		return typeCount;
	}
	
	
	/**
	* @return
	 */
	public List<SysDictType> getAll(){
		return this.select(new SysDictType());
	}

	public List<SysDictType> getAllEnabel(){
		SysDictType sdt = new SysDictType();
		sdt.setStatus(Constant.SYSTEM_COMMON_ENABLE);
		return this.select(sdt,"sort");
	}
	
	public SysDictType selectDicTypeByCode(String type) {
		SysDictType st = new SysDictType();
		st.setCode(type);
		return sysDictTypeMapper.selectOne(st);
	}

	
	
	public List<SysDictType> getAllFromRedis() {
		try{
			String json = redisService.get(RedisKey.ALL_DIC_TYPE);
			if(StringUtils.isNotBlank(json)){
				return (List<SysDictType>) JSON.parseArray(json,SysDictType.class);
			}
		}catch (Exception e) {
			LOG.error("redis 获取字典分类出错",e);
		}
		return getAll();
	}

	public Object getAllEnabelFromRedis() {
		try{
			String json = redisService.get(RedisKey.ALL_DIC_TYPE);
			if(StringUtils.isNotBlank(json)){
				List<SysDictType> dicTypes = (List<SysDictType>) JSON.parseArray(json,SysDictType.class);
				List<SysDictType> dicEnabelTypes = Lists.newArrayList();
				for (SysDictType dt : dicTypes) {
					if(Constant.SYSTEM_COMMON_ENABLE.equals(dt.getStatus())){
						dicEnabelTypes.add(dt);
					}
				}
				return dicEnabelTypes;
			}
		}catch (Exception e) {
			LOG.error("redis 获取字典分类出错",e);
		}
		return getAllEnabel();
	}
	
	
	//单个更新状态
	public Integer updateStauts(String id,String status){
		return updateStauts(id, status,true);
	}
	
	//批量更新状态
	public Integer updateStauts(String[] ids,String status){
		int c = 0;
		for (String id : ids) {
			c += updateStauts(id, status, false);
		}
		if(c > 0){
			putRedis();
		}
		return c;
	}
	
	
	private Integer updateStauts(String id,String status,boolean updateRedis){
		if(Constant.SYSTEM_COMMON_ENABLE.equals(status) ||
				Constant.SYSTEM_COMMON_DISABLE.equals(status)){
			int updateCount = sysDictTypeMapper.updateStatusByRootId(id,status);
			if(updateCount > 0 && updateRedis){
				putRedis();
			}
			return updateCount;
		}
		return 0;
	}
		
	
	
	public void putRedis(){
		try{
			List<SysDictType> sdt = getAll();
			redisService.set(RedisKey.ALL_DIC_TYPE, JSONUtils.toJSONString(sdt));
		}catch (Exception e) {
			LOG.error("redis 放入字典分类数据出错",e);
		}
	}


	/**
	 * 分类查询
	 * @param params {"name":"分类名字","id":"分类id"}
	 * @return
	 */
	public List<SysDictType> find(Map<String, Object> params) {
		return sysDictTypeMapper.findPageInfo(params);
	}


	
	public Integer updateSort(String id,Integer sort){
		SysDictType sr = new SysDictType();
		sr.setSort(sort);
		sr.setId(id);
		int c = this.updateByPrimaryKeySelective(sr);
		if(c == 1){
			putRedis();
		}
		return c;
	}

	//保存添加，只传名称
	public Integer saveAdd(SysDictTypeVO sysDictType,String pid) {
		SysDictType pMenu = null;
		if(!StrUtils.isNoneBlank(pid)){
			pMenu = this.selectByPrimaryKey(pid);
			if(pMenu == null){
				return 0;
			}
		}
		int c = 0;
		for(SysDictType m : sysDictType.getSysDictType()){
			if(m.getId() != null){
				if(StringUtils.isNotBlank(m.getName()) && !m.getName().equals(m.getOldName())){
					c += this.updateByPrimaryKeySelective(m);
				}
			}else{
				if(StringUtils.isNotBlank(m.getName())){
					m.setStatus(Constant.SYSTEM_COMMON_ENABLE);
					m.setParentId(pid);
					m.setParentIds(StrUtils.isNoneBlank(pid) ? "0,":pMenu.getParentIds()+""+pid+",");
					c += this.insertSelective(m);
				}
			}
		}
		//如果运行到这里，至少要返回1
		if(c == 0){
			c = 1;
		}else{
			putRedis();
		}
		return c;
	}

	public List<SysDictType> findSysDictTypeByCode(Map<String, Object> params){
		return sysDictTypeMapper.findSysDictTypeByCode(params);
	}
	
	/**
	 * 根据父节点的code查询所有子分类 
	 * @param code
	 * @return
	 */
	public List<SysDictType> findTypeByParentCode(String code){
		return sysDictTypeMapper.findByParentCode(code);
	}
	
}
