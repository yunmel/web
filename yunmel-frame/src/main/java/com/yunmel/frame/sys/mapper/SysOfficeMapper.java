

package com.yunmel.frame.sys.mapper;

import java.util.List;
import java.util.Map;

import com.yunmel.commons.mapper.BaseMapper;
import com.yunmel.frame.sys.model.SysOffice;

/**
 * 
 * @author 
 */

public interface SysOfficeMapper extends BaseMapper<SysOffice> {

	public List<SysOffice> findPageInfo(Map<String, Object> params);
	
	public int deleteOfficeByRootId(String id);

	public int updateParentIds(SysOffice sysOffice);
	
	public SysOffice findOfficeCompanyIdByDepId(String depId);
	
	//得到用户数据范围
	public List<String> findUserDataScopeByUserId(String userId);
	
	public List<String> findOfficeIdsByRootId(String rootId);

	public List<SysOffice> findAllOffice();

	public List<SysOffice> findOfficeByParentId(Map<String, Object> map);

//	public List<SysOffice> findOfficeHaveCount();

	public List<SysOffice> findListByDataScope(Map<String, Object> params);
	
	//查询所有单位
	public List<SysOffice> findSchoolAll();

  //public List<SysOffice> findOfficeByProject(Map<String, Object> params);
}
