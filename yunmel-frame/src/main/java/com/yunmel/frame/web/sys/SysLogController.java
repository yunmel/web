

package com.yunmel.frame.web.sys;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.yunmel.commons.utils.StrUtils;
import com.yunmel.frame.sys.model.SysLog;
import com.yunmel.frame.sys.service.SysDictService;
import com.yunmel.frame.sys.service.SysLogService;

@Controller
@RequestMapping("sys/log")
public class SysLogController {
	
	
	@Resource
	private SysLogService sysLogService;
	
	@Resource
	private SysDictService sysDictService;
	
	
	/**
	 * 跳转到模块页面
	 */
	@RequestMapping
	public String toSysLog(Model model){
		model.addAttribute("dict", sysDictService.findJsonByTypeFromRedis("sys_log_type"));
		return "sys/log/log";
	}
	
	
	/**
	 * 分页显示
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	public @ResponseBody PageInfo<SysLog> list(int pageNum,int pageSize,String sortName,String order,@ModelAttribute SysLog sysLog, Model model){
		if(StrUtils.isBlank(sortName)){
			sortName = "id";
		}
		if(StrUtils.isBlank(sortName)){
			order = "desc";
		}
		sortName =StrUtils.camelhumpToUnderline(sortName);
		return sysLogService.selectPage(pageNum, pageSize, sysLog,sortName + " " + order);
	}
	
	/**
	 * 添加或更新
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute SysLog sysLog){
		return sysLogService.saveSysLog(sysLog);
	}
	
	
	/**
	 * 删除
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(Long id){
		return sysLogService.deleteByPrimaryKey(id);
	}
	
	
	/**
	 * 弹窗显示
	 * @param params {"mode":"1.add 2.edit 3.detail}
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(Long id,Model model){
		SysLog sysLog = sysLogService.selectByPrimaryKey(id);
		model.addAttribute("sysLog", sysLog);
		return "sys/log/log-detail";
	}
	

}
