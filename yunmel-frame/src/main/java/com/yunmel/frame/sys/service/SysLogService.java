
package com.yunmel.frame.sys.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yunmel.commons.service.BaseService;
import com.yunmel.commons.utils.Globle;
import com.yunmel.commons.utils.StrUtils;
import com.yunmel.frame.sys.mapper.SysLogMapper;
import com.yunmel.frame.sys.model.SysLog;

@Service("sysLogService")
public class SysLogService extends BaseService<SysLog> {
	@Resource
	private SysLogMapper sysLogMapper;
	/**
	 * 日志只有保存操作！ 
	 */
	public int saveSysLog(SysLog sysLog) {
		int count = 0;
		sysLog.set("createDate", new Date());
		sysLog.set("updateDate", new Date());
		sysLog.set("delFlag", Globle.DEL_FLAG_NORMAL);
		if(StrUtils.isNotBlank(sysLog.getCreateBy())){
		  sysLog.set("createBy", "system");
		}
		count = sysLogMapper.insertSelective(sysLog);
		return count;
	}

	/**
	 * 根据条件分页查询SysLog列表
	 * 
	 * @param {"pageNum":"页码","pageSize":"条数","isCount":"是否生成count sql",......}
	 */
	public List<SysLog> findSysLogList(Map<String, Object> params) {
		List<SysLog> list = sysLogMapper.findSysLogList(params);
		return list;
	}

	// 执行SQL语句
	public PageInfo<Map<String, Object>> selectList(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<Map<String, Object>> list = sysLogMapper.selectList(params);
		return new PageInfo<>(list);
	}
}
