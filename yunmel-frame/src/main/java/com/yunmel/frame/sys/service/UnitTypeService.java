package com.yunmel.frame.sys.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.yunmel.commons.service.BaseService;
import com.yunmel.commons.utils.DealParamUtil;
import com.yunmel.frame.sys.mapper.UnitTypeMapper;
import com.yunmel.frame.sys.model.UnitType;


@Service("unitTypeService")
public class UnitTypeService extends BaseService<UnitType>{

	@Resource
	private UnitTypeMapper unitTypeMapper;
	
	public PageInfo<UnitType> findPageInfo(Map<String, Object> params){
	  DealParamUtil.dealParam(params);
	  PageHelper.startPage(params);
	  List<UnitType> list = findByParams(params);
	  
	  return new PageInfo<UnitType>(list);
	}
	
	public List<UnitType> findByParams(Map<String, Object> params){
	  if(params==null){
	    params = Maps.newHashMap();
	  }
      return unitTypeMapper.findPageInfo(params);
    }

	public List<UnitType> findAll() {
	    return this.findByParams(null);
	  }
	
  public Integer saveUnitType(UnitType unitType) {
    if(unitType.getId()==null){
      return this.insertSelective(unitType);
    }else{
      return this.updateByPrimaryKeySelective(unitType);
    }
  }

  public Integer deleteUnitType(String[] ids) {
    int tag = 0;
    for (String id : ids) {
      tag += this.updateDelFlagToDelStatusById(UnitType.class, id);
    }
    return tag;
  }

  
	
}
