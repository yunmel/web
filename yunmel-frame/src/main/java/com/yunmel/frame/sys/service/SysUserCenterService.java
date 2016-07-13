package com.yunmel.frame.sys.service;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.yunmel.commons.service.BaseService;
import com.yunmel.commons.utils.IPUtils;
import com.yunmel.commons.utils.PasswordEncoders;
import com.yunmel.frame.common.utils.SysUserUtils;
import com.yunmel.frame.sys.function.OfficeFunctions;
import com.yunmel.frame.sys.model.SysOffice;
import com.yunmel.frame.sys.model.SysUser;

@Service
public class SysUserCenterService extends BaseService<SysUser> {

	@Resource
	private OfficeFunctions officeFunctions;

	public SysUser getSysUserInfo() {
		SysUser sysUser = SysUserUtils.getCacheLoginUser();
		Map<String, SysOffice> offices = officeFunctions.getAllOfficeMap();
//		String companyId = sysUser.getCompanyId();
		String officeId = sysUser.getUnitId();
		String orgStr = null;
//		if (companyId.equals(officeId)) { // 机构
//			orgStr = ((SysOffice) offices.get(companyId)).getName();
//		} else {
//			orgStr = ((SysOffice) offices.get(companyId)).getName() + " —— "
//					+ ((SysOffice) offices.get(officeId)).getName();
//		}
		orgStr = ((SysOffice) offices.get(officeId)).getName();
		
		String curIP = IPUtils.getClientAddress(SysUserUtils.getCurRequest());
		String ipEx = "";
		if (!StringUtils.equals(sysUser.getLoginIp(), curIP))
			ipEx = "(当前IP为:" + curIP + "，与上次登录IP不一致，请注意!)";
		sysUser.set("orgStr", orgStr);
		sysUser.set("ipEx", ipEx);
		return sysUser;
	}

	/**
	 * 用户更新资料
	 */
	public Integer updateSysuserInfo(SysUser sysUser) {
		String pwd = null;
		if(StringUtils.isNotBlank(sysUser.getPassword())){
			pwd = PasswordEncoders.encrypt(sysUser.getPassword(),
					SysUserUtils.getCacheLoginUser().getUsername());
		}
		sysUser.setPassword(pwd);
		sysUser.setId(SysUserUtils.getCacheLoginUser().getId());
		return this.updateByPrimaryKeySelective(sysUser);
	}

}
