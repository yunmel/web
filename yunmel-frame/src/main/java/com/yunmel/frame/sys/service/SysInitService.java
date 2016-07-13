package com.yunmel.frame.sys.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * 系统初始化的service
 *    1.所有启动时需要加载到redis服务器中的数据，都在这里统一实现
 *    2.如果redis出现挂机时，直接从数据库读取，当redis启动成功后，可以重新加载
 * @author taosq
 */
@Service("sysInitService")
public class SysInitService{
	
	private static final Logger LOG = LoggerFactory.getLogger(SysInitService.class);
	
	@Resource
	private SysMenuService sysMenuService;
	
	@Resource
	private SysDictTypeService sysDictTypeService;
	
	@Resource
	private SysDictService sysDictService;
	
	@Resource
	private SysConfigService sysConfigService;
	
	@Resource
	private SysRoleService sysRoleService;
	
	public void loadData(){
		sysMenuService.putRedis();
		sysDictTypeService.putRedis();
		sysDictService.putRedis();
		sysRoleService.putRedis();
		sysConfigService.putRedis();
	} 
	
}
