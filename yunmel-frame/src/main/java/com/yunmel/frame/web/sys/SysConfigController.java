package com.yunmel.frame.web.sys;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunmel.frame.sys.service.SysConfigService;
import com.yunmel.frame.sys.vo.LoginConfig;


/**
 * 系统配置
 * @author taosq
 * 
 */
@Controller
@RequestMapping("sys/config")
public class SysConfigController {
	
	@Resource
	private SysConfigService sysConfigService;
	
	@RequestMapping
	public String toSysConfig(Model model){
		return "sys/config/config";
	}
	
	@RequestMapping("/login/save")
	public @ResponseBody Integer saveLoginConfig(@ModelAttribute LoginConfig config){
		
		if(config.getLoginCaptchaOn() == null){
			config.setLoginCaptchaOn("0");
		}
		if(config.getPwdStrongVerifcation() == null){
			config.setPwdStrongVerifcation("0");
		}
		if(config.getPwdFristLoginMod() == null){
			config.setPwdFristLoginMod("0");
		}
		
		return sysConfigService.saveLoginConfig(config);
	}
	
	@RequestMapping("/attach/save")
	public @ResponseBody Integer saveAttachConfig(String path,String size){
		return sysConfigService.saveAttachConfig(path,size);
	}
	
	
}
