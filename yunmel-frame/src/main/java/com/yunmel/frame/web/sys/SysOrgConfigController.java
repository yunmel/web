package com.yunmel.frame.web.sys;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunmel.frame.sys.service.SysConfigService;


/**
 * 组织配置
 * 可以设置版权单位、登录页面登录的名称、系统的名称
 * @author taosq
 *
 */
@Controller
@RequestMapping("sys/org/name")
public class SysOrgConfigController {
	@Resource
	public SysConfigService sysConfigService;
	
	@RequestMapping
	public String toConfig(){
		return "sys/config/org";
	}
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody Integer save(String orgName,String loginShowName,String indexShowName){
		return sysConfigService.saveOrg(orgName,loginShowName,indexShowName);
	}
	
	
	

}
