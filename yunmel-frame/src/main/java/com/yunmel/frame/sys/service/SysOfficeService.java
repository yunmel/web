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
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.yunmel.commons.service.BaseService;
import com.yunmel.commons.service.RedisService;
import com.yunmel.commons.utils.DealParamUtil;
import com.yunmel.commons.utils.StrUtils;
import com.yunmel.frame.common.utils.SysUserUtils;
import com.yunmel.frame.consts.RedisKey;
import com.yunmel.frame.sys.mapper.SysOfficeMapper;
import com.yunmel.frame.sys.mapper.SysRoleMapper;
import com.yunmel.frame.sys.model.SysOffice;
import com.yunmel.frame.sys.model.SysRole;
import com.yunmel.frame.sys.model.SysUser;
import com.yunmel.frame.sys.vo.SysOfficeVO;

/**
 * 
 * @author
 */

@Service("sysOfficeService")
public class SysOfficeService extends BaseService<SysOffice> {

  private final static Logger LOG = LoggerFactory.getLogger(SysOfficeService.class);
  @Resource
  private SysOfficeMapper sysOfficeMapper;
  @Resource
  private SysRoleMapper sysRoleMapper;
  @Resource
  private RedisService redisService;

  /**
   * 新增或更新SysOffice
   */
  public int saveSysOffice(SysOffice sysOffice) {
    if (null == sysOffice.getId()) {
      return this.insertSelective(sysOffice);
    } else {
      return this.updateByPrimaryKeySelective(sysOffice);
    }
  }

  public int deleteOfficeByRootId(String id) {
    int roleCount = this.beforeDeleteTreeStructure(id, "officeId", SysRole.class, SysOffice.class);
    if (roleCount < 0)
      return -1;
    int userOfficeCount =
        this.beforeDeleteTreeStructure(id, "officeId", SysUser.class, SysOffice.class);
    int userCompanyCount =
        this.beforeDeleteTreeStructure(id, "companyId", SysUser.class, SysOffice.class);
    if (userOfficeCount + userCompanyCount < 0)
      return -1;
    int c = sysOfficeMapper.deleteOfficeByRootId(id);

    if (c > 0) {
      putRedis();
      SysUserUtils.clearUserDataScopeOffice();
    }

    return c;
  }

  /**
   * 根据用户id查询用户的数据范围
   */
  public List<String> findUserDataScopeByUserId(String userId) {
    return sysOfficeMapper.findUserDataScopeByUserId(userId);
  }

  /**
   * 根据根节点查询自身及其子孙节点
   */
  public List<String> findOfficeIdsByRootId(String rootId) {
    return sysOfficeMapper.findOfficeIdsByRootId(rootId);
  }


  /**
   * 根据条件分页查询SysOffice列表
   * 
   * @param {"pageNum":"页码","pageSize":"条数","isCount":"是否生成count sql",......}
   */
  public PageInfo<SysOffice> findPageInfo(Map<String, Object> params) {
    DealParamUtil.dealParam(params);
    PageHelper.startPage(params);
    List<SysOffice> list = this.findByPatams(params);
    return new PageInfo<SysOffice>(list);
  }
  
  /**
   * 不分页
   * @param params
   * @return
   */
  public List<SysOffice> findByPatams(Map<String, Object> params) {
    if(params==null){
      params = Maps.newHashMap();
    }
    return sysOfficeMapper.findPageInfo(params);
  }

  public List<SysOffice> findAllOffice() {
    return this.findByPatams(null);
  }

  // 保存添加，只传名称
  public Integer saveAdd(SysOfficeVO offices, String pid) {
    SysOffice pMenu = null;
    if (!StrUtils.isNoneBlank(pid)) {
      pMenu = this.selectByPrimaryKey(pid);
      if (pMenu == null) {
        return 0;
      }
    }
    int c = 0;
    for (SysOffice m : offices.getOffices()) {
      if (m.getId() != null) {
        if (StringUtils.isNotBlank(m.getName()) && !m.getName().equals(m.getOldName())) {
          c += this.updateByPrimaryKeySelective(m);
        }
      } else {
        if (StringUtils.isNotBlank(m.getName())) {
          m.setParentId(pid);
          m.setAreaId("");
          m.setParentIds(StrUtils.isNoneBlank(pid) ? "0," : pMenu.getParentIds() + "" + pid + ",");
          c += this.insertSelective(m);
        }
      }
    }
    // 如果运行到这里，至少要返回1
    if (c == 0) {
      c = 1;
    } else {
      putRedis(); // 加入缓存
      SysUserUtils.clearUserDataScopeOffice();
    }
    return c;
  }


  public void putRedis() {
    try {
      List<SysOffice> sdt = getAll();
      redisService.set(RedisKey.ALL_OFFICE, JSONUtils.toJSONString(sdt));
    } catch (Exception e) {
      LOG.error("redis 放入字典分类数据出错", e);
    }
  }

  public List<SysOffice> findAllOfficeFromRedis() {
    String json = redisService.get(RedisKey.ALL_OFFICE);
    if (StringUtils.isNotBlank(json)) {
      return JSON.parseArray(json, SysOffice.class);
    } else {
      return getAll();
    }
  }

  public List<SysOffice> getAll() {
    return this.select(new SysOffice());
  }

  public int deleteOfficeByRootId(String[] ids) {
    int c = 0;
    for (String id : ids) {
      c += deleteOfficeByRootId(id);
    }
    if (c > 0) {
      putRedis();
      this.putRedis();
    }
    return c;
  }

  public List<SysOffice> findOfficeByParentId(Map<String, Object> map) {

    return this.sysOfficeMapper.findOfficeByParentId(map);
  }

  /**
   * 通过部门id获取公司id
   * 
   * @param officeId
   * @return
   */
  public String getCompanyIdByOfficeId(String officeId) {
    SysOffice so = this.selectByPrimaryKey(officeId);

    Map<String, Object> map = Maps.newHashMap();
    String companyId = "";
    map.put("parentId", "" + so.getParentId() + "");
    SysOffice sysOffice = this.findOfficeByParentId(map).get(0);
    if (!StrUtils.isNoneBlank(sysOffice.getParentId())) {
      getCompanyIdByOfficeId(sysOffice.getId());
    } else {
      companyId = sysOffice.getId();
    }
    return companyId;
  }

  // /**
  // * 带统计信息
  // * @return
  // */
  // public List<SysOffice> findOffice() {
  // return sysOfficeMapper.findOfficeHaveCount();
  // }

  public List<SysOffice> findListByDataScope(String dataScope) {
    Map<String, Object> params = Maps.newHashMap();
    params.put("scope", dataScope);
    return sysOfficeMapper.findListByDataScope(params);
  }



  /**
   * 查询所有单位
   * 
   * @return
   */
  public List<SysOffice> findSchoolAll() {
    return sysOfficeMapper.findSchoolAll();
  }

  public Integer deleteOfficeBy(String[] ids) {
    int tag = 0;
    for (String id : ids) {
      tag +=  this.updateDelFlagToDelStatusById(SysOffice.class, id);
    }
    return tag;
  }

  
//  public List<SysOffice> findOfficeByProject(Map<String, Object> params) {
//    
//    return sysOfficeMapper.findOfficeByProject(params);
//  }


}
