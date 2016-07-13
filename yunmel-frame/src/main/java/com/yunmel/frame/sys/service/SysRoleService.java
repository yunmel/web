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
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yunmel.commons.service.BaseService;
import com.yunmel.commons.service.RedisService;
import com.yunmel.frame.common.utils.SysUserUtils;
import com.yunmel.frame.consts.Constant;
import com.yunmel.frame.consts.RedisKey;
import com.yunmel.frame.sys.mapper.SysRoleMapper;
import com.yunmel.frame.sys.model.SysMenu;
import com.yunmel.frame.sys.model.SysRole;
import com.yunmel.frame.sys.model.SysUser;

/**
 * 
 * @author 
 */

@Service("sysRoleService")
public class SysRoleService extends BaseService<SysRole> {
	private final static Logger LOG = LoggerFactory.getLogger(SysRoleService.class);
	
	@Resource
	private SysRoleMapper sysRoleMapper;
	
	@Resource
	private RedisService redisService;
	
//	/**
//	 *新增或更新SysRole
//	 */
//	public int saveSysRole(SysRole sysRole){
//		int count = 0;
//		if(null == sysRole.getId()){
//			count = this.insertSelective(sysRole);
//		}else{
//			sysRoleMapper.deleteRoleResourceByRoleId(sysRole.getId());
//			sysRoleMapper.deleteRoleOfficeByRoleId(sysRole.getId());
//			count = this.updateByPrimaryKeySelective(sysRole);
//		}
//		if(sysRole.getResourceIds().length>0){
//			sysRoleMapper.insertRoleResource(sysRole);
//		}
//		if(sysRole.getOfficeIds().length>0 && ("9").equals(sysRole.getDataScope())){
//			sysRoleMapper.insertRoleOffice(sysRole);
//		}
//		if(count > 0){
//			putRedis();
//		}
//	    return count;
//	}
	
	/**
	 * 删除角色
	* @param id
	 */
	public int deleteSysRole(String id){
		sysRoleMapper.deleteUserRoleByRoleId(id);
		sysRoleMapper.deleteRoleOfficeByRoleId(id);
		sysRoleMapper.deleteRoleResourceByRoleId(id);
		int count = this.deleteByPrimaryKey(id);
		//清空所有的用户缓存
		SysUserUtils.clearUserRole();
		putRedis();
		return count;
	}
	
	/**
	 * 该方法只保存名称和角色，所以不需要清空缓存
	 * @param sysRole
	 * @return
	 */
	public int saveSysRole(SysRole sysRole){
		int c = 0;
		if(sysRole.getId() != null){
			c = this.updateByPrimaryKeySelective(sysRole);
		}else{
			sysRole.setDataScope(Constant.DATA_SCOPE_SCHOOL);
			c = this.insertSelective(sysRole);
		}
		if(c > 0){
			putRedis();
		}
		return c;
	}
	
	
	
	/**
	 * 添加角色绑定的人员
	* @param sysRole
	* @return
	 */
	public int saveUserRole(SysRole sysRole){
		//旧的绑定的人员id
		List<String> userIds = sysRoleMapper.findUserIdsByRoleId(sysRole.getId());
		//当前的要绑定的人员id
		List<String> curUserIds = Lists.newArrayList(sysRole.getUserIds());
		userIds.addAll(curUserIds);
		//ImmutableList<String> mergeList = ImmutableSet.copyOf(userIds).asList();
		
		sysRoleMapper.deleteUserRoleByRoleId(sysRole.getId());
		if(sysRole.getUserIds().length>0) {
			sysRoleMapper.insertUserRoleByRoleId(sysRole);
		}
		//清除缓存
		SysUserUtils.clearUserRole();
		return 1;
	}
	
	
	public Integer saveUserMenu(SysRole sysRole) {
		sysRoleMapper.deleteRoleResourceByRoleId(sysRole.getId());
		if(sysRole.getResourceIds().length>0){
			//去掉
			String [] menus = sysRole.getResourceIds();
			List<String> res = Lists.newLinkedList();
			for (String m : menus) {
				if(!res.contains(m)) {  
		            res.add(m);  
		        } 
			}
			
		   sysRole.setResourceIds(res.toArray(new String[res.size()]));  
		   sysRoleMapper.insertRoleResource(sysRole);
		}
		//清除缓存
		SysUserUtils.clearUserMenu();
		return 1;
	}
	
	
	public Integer saveUserOffice(SysRole sysRole) {
		sysRoleMapper.deleteRoleOfficeByRoleId(sysRole.getId());
		
		if(sysRole.getOfficeIds() != null && sysRole.getOfficeIds().length>0 && ("9").equals(sysRole.getDataScope())){
			sysRoleMapper.insertRoleOffice(sysRole);
		}
		this.updateByPrimaryKeySelective(sysRole);
		//清除缓存
		SysUserUtils.clearUserDataScopeOffice();
		return 1;
	}
	
	
	
	
	/**
	 * 根据条件分页查询SysRole列表
	 * @param {"pageNum":"页码","pageSize":"条数","isCount":"是否生成count sql",......}
	 */
	public List<SysRole> findPageInfo(Map<String,Object> params) {
		params.put("dataScope",SysUserUtils.dataScopeFilterString("so",null));
        return sysRoleMapper.findPageInfo(params); 
	}
	
	/**
	 * 根据角色id查询拥有的资源id集合
	* @param roleId
	* @return
	 */
	public List<String> findResourceIdsByRoleId(String roleId){
		return sysRoleMapper.findResourceIdsByRoleId(roleId);
	}
	
	/**
	 * 根据角色id查询拥有的机构id集合
	* @param roleId
	* @return
	 */
	public List<String> findOfficeIdsByRoleId(String roleId){
		return sysRoleMapper.findOfficeIdsByRoleId(roleId);
	}
	
	/**
	 * 根据角色id查询拥有的资源 
	* @param roleId
	* @return
	 */
	public List<SysMenu> findResourceByRoleId(String roleId){
		return sysRoleMapper.findResourceByRoleId(roleId);
	}
	
	/**
	 * 根据角色id查询拥有此角色的用户
	* @param roleId
	* @return
	 */
	public List<SysUser> findUserByRoleId(String roleId){
		return sysRoleMapper.findUserByRoleId(roleId);
	}
	
	/**
	 * 当前登录用户的可见的角色
	 */
	public List<SysRole> findCurUserRoleList(){
		Map<String, Object> params = Maps.newHashMap();
	//	params.put(Constant.CACHE_USER_DATASCOPE, SysUserUtils.dataScopeFilterString("so", "sur","user_id"));
		return sysRoleMapper.findPageInfo(params);
	}
	
	/**
	 * 当前登录用户的可见的角色map形式 
	 */
	public Map<String, SysRole> findCurUserRoleMap(){
		List<SysRole> list = this.findCurUserRoleList();
		Map<String, SysRole> map = Maps.uniqueIndex(list, new Function<SysRole, String>() {
			@Override
			public String apply(SysRole sysRole) {
				return sysRole.getId();
			}
		});
		return map;
	}
	
	
	/**
	 * 用户的角色List列表
	* @param userId
	* @return userRoleList
	 */
	public List<SysRole> findUserRoleListByUser(SysUser user){
		List<SysRole> userRoles = sysRoleMapper.findUserRoleListByUserId(user.getId());
		return userRoles;
	}
	
	
	/***
	 * 从redis中获取用户的角色
	 * @param user
	 * @return
	 */
	public List<SysRole> findUserRoleFromRedis(SysUser user) {
		try{
			String json = redisService.get(RedisKey.getUserRoleKey(user.getId()));
			if(StringUtils.isNotBlank(json)){
				return (List<SysRole>) JSON.parseArray(json,SysRole.class);
			}
		}catch (Exception e) {
			
		}
		return findUserRoleListByUser(user);
	}
	
	/***
	 * 从redis中获取用户的角色
	 * @param user
	 * @return
	 */
	public List<SysRole> findAllRoleFromRedis() {
		try{
			String json = redisService.get(RedisKey.ALL_ROLE);
			if(StringUtils.isNotBlank(json)){
				return (List<SysRole>) JSON.parseArray(json,SysRole.class);
			}
		}catch (Exception e) {
			
		}
		return select(new SysRole());
	}
	
	
	
	public void putRedis(){
		try{
			List<SysRole> roles = select(new SysRole());
			redisService.set(RedisKey.ALL_ROLE, JSONUtils.toJSONString(roles));
		}catch (Exception e) {
			LOG.error("redis 放入角色数据出错",e);
		}
	}

	
	/**
	 * 用户的角色Map
	* @param userId
	* @return userRoleMap
	 */
	public Map<String, SysRole> findUserRoleMapByUser(SysUser user){
		List<SysRole> roleList = this.findUserRoleListByUser(user);
		Map<String, SysRole> userRoleMap = Maps.uniqueIndex(roleList, new Function<SysRole, String>() {
			@Override
			public String apply(SysRole sysRole) {
				return sysRole.getId();
			}
		});
		return userRoleMap;
	}

}
