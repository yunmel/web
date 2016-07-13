

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
import com.google.common.collect.Lists;
import com.yunmel.commons.service.BaseService;
import com.yunmel.commons.service.RedisService;
import com.yunmel.commons.utils.StrUtils;
import com.yunmel.commons.utils.TreeUtils;
import com.yunmel.frame.consts.Constant;
import com.yunmel.frame.consts.RedisKey;
import com.yunmel.frame.sys.mapper.SysMenuMapper;
import com.yunmel.frame.sys.model.SysMenu;
import com.yunmel.frame.sys.model.SysUser;
import com.yunmel.frame.sys.vo.SysMenuVO;

/**
 * 菜单管理业务处理
 * 
 * @author yunmel
 */
@Service("sysMenuService")
public class SysMenuService extends BaseService<SysMenu> {

  private final static Logger LOG = LoggerFactory.getLogger(SysMenuService.class);

  @Resource
  private SysMenuMapper sysMenuMapper;

  @Resource
  private RedisService redisService;

  /**
   * 新增or更新SysMenu
   */
  public int saveSysMenu(SysMenu sysMenu) {
    // sysMenu.setType(sysMenu.getType()); //因type是关键字，故这样做
    int count = 0;
    // 新的parentIds
    sysMenu.setParentIds(sysMenu.getParentIds() + sysMenu.getParentId() + ",");
    if (null == sysMenu.getId()) {
      count = this.insertSelective(sysMenu);
    } else {
      // getParentIds() 当前选择的父节点parentIds , getParentId()父节点的id
      // 先更新parentId，此节点的parentIds以更新
      count = this.updateByPrimaryKeySelective(sysMenu);
      // 不移动节点不更新子节点的pids
      if (!StringUtils.equals(sysMenu.getOldParentIds(), sysMenu.getParentIds())) {
        sysMenuMapper.updateParentIds(sysMenu); // 批量更新子节点的parentIds
      }
    }
    if (count > 0) {
      putRedis();
    }
    return count;
  }


  /**
   * 根据父id删除自身已经所有子节点
   * 
   * @param id
   * @return
   */
  public int deleteMenuByRootId(String id) {
    int count = deleteMenuByRootId(id, true);
    return count;
  }

  /**
   * 根据父id删除自身已经所有子节点
   * 
   * @param id
   * @return
   */
  public int deleteMenuByRootId(String id, boolean updateRedis) {
    int count = sysMenuMapper.beforeDeleteMenu(id);
    if (count > 0)
      return -1;
    int delCount = sysMenuMapper.deleteIdsByRootId(id);
    if (delCount > 0 && updateRedis) {
      putRedis();
    }
    return delCount;
  }

  /**
   * 根据用户id得到用户持有的资源
   * 
   * @param userId
   * @return
   */
  public List<SysMenu> findByUser(SysUser user) {
    if (user.isAdmin()) {
      return getAllEnable();
    }
    return sysMenuMapper.findUserMenuByUserId(user.getId());
  }

  /**
   * 根据用户id得到用户持有的资源的URL
   * 
   * @param userId
   * @return
   */
  public List<String> findUrlByUser(SysUser user) {
    return getUrl(findByUser(user));
  }


  public List<SysMenu> getAll() {
    return this.select(new SysMenu(), "sort");
  }

  public List<SysMenu> getAllEnable() {
    SysMenu sr = new SysMenu();
    sr.setStatus(Constant.SYSTEM_COMMON_ENABLE);
    return this.select(sr, "sort");
  }

  /**
   * 获取菜单树
   */
  public List<SysMenu> getMenuTree() {
    return TreeUtils.toTreeNodeList(getAllFromRedis(), SysMenu.class);
  }


  /**
   * 菜单查询
   * 
   * @param params {"name":"菜单名字","id":"菜单id"}
   * @return
   */
  public List<SysMenu> find(Map<String, Object> params) {
    return sysMenuMapper.findPageInfo(params);
  }


  public List<SysMenu> getAllFromRedis() {
    try {
      String json = redisService.get(RedisKey.ALL_MENU);
      if (StringUtils.isNotBlank(json)) {
        return (List<SysMenu>) JSON.parseArray(json, SysMenu.class);
      }
    } catch (Exception e) {
      LOG.error("redis 获取菜单出错", e);
    }
    return getAll();
  }



  public List<String> getAllUrlFromRedis() {
    try {
      String json = redisService.get(RedisKey.ALL_MENU_URL);
      if (StringUtils.isNotBlank(json)) {
        return (List<String>) JSON.parseArray(json, String.class);
      }
    } catch (Exception e) {
      LOG.error("redis 获取菜单地址出错", e);
    }

    return getUrl(getAll());
  }


  public List<String> getCommonUrlFromRedis() {
    try {
      String json = redisService.get(RedisKey.COMMON_MENU_URL);
      if (StringUtils.isNotBlank(json)) {
        return (List<String>) JSON.parseArray(json, String.class);
      }
    } catch (Exception e) {
      LOG.error("redis 获取通用菜单地址出错", e);
    }

    return getCommonUrl();
  }


  public List<String> findUrlByUserFromRedis(SysUser user) {
    try {
      String json = redisService.get(RedisKey.getUserMenuUrlKey(user.getId()));
      if (StringUtils.isNotBlank(json)) {
        return (List<String>) JSON.parseArray(json, String.class);
      }
    } catch (Exception e) {
      LOG.error("redis 获取用户菜单地址出错", e);
    }

    return findUrlByUser(user);
  }


  private List<String> getUrl(List<SysMenu> resources) {
    List<String> urls = Lists.newArrayList();
    for (SysMenu sysMenu : resources) {
      if (StringUtils.isNotBlank(sysMenu.getUrl())) {
        urls.add(sysMenu.getUrl());
      }
    }
    return urls;
  }

  private List<String> getCommonUrl() {
    List<SysMenu> resources = getAll();
    List<String> commonUrls = Lists.newArrayList();
    for (SysMenu sr : resources) {
      if (Constant.RESOURCE_COMMON.equals(sr.getCommon()) && StringUtils.isNotBlank(sr.getUrl())) {
        commonUrls.add(sr.getUrl());
      }
    }
    return commonUrls;
  }



  public void putRedis() {
    try {
      List<SysMenu> resources = getAll();
      redisService.set(RedisKey.ALL_MENU, JSONUtils.toJSONString(resources));
      List<String> urls = getUrl(resources);
      redisService.set(RedisKey.ALL_MENU_URL, JSONUtils.toJSONString(urls));
      List<String> commonUrls = getCommonUrl();
      redisService.set(RedisKey.COMMON_MENU_URL, JSONUtils.toJSONString(commonUrls));
    } catch (Exception e) {
      LOG.error("redis 放入菜单数据出错", e);
    }
  }


  /***
   * 注意只能获取菜单，不能获取按钮
   * 
   * @param user
   * @return
   */
  public List<SysMenu> findMenuByUserFromRedis(SysUser user) {
    try {
      String json = redisService.get(RedisKey.getUserMenuKey(user.getId()));
      if (StringUtils.isNotBlank(json)) {
        return (List<SysMenu>) JSON.parseArray(json, SysMenu.class);
      }
    } catch (Exception e) {

    }
    List<SysMenu> resources = null;
    if (user.isAdmin()) {
      SysMenu sr = new SysMenu();
      sr.setType(Constant.RESOURCE_TYPE_MENU);
      resources = this.select(sr, "sort");
    } else {
      resources = Lists.newArrayList();
      List<SysMenu> srs = sysMenuMapper.findUserMenuByUserId(user.getId());
      for (SysMenu sr : srs) {
        if (Constant.RESOURCE_TYPE_MENU.equals(sr.getType())) {
          resources.add(sr);
        }
      }
    }
    return TreeUtils.toTreeNodeList(resources, SysMenu.class);
  }

  public Integer deleteMenuByRootId(String[] ids) {
    int c = 0;
    for (String id : ids) {
      if (deleteMenuByRootId(id, false) != -1) {
        c++;
      }
    }
    if (c > 0) {
      putRedis();// 更新缓存
    }
    return c;
  }


  public Integer updateSort(String id, Integer sort) {
    SysMenu sr = new SysMenu();
    sr.setSort(sort);
    sr.setId(id);
    int c = this.updateByPrimaryKeySelective(sr);
    if (c == 1) {
      putRedis();
    }
    return c;
  }



  // 单个更新状态
  public Integer updateStauts(String id, String status) {
    return updateStauts(id, status, true);
  }

  // 批量更新状态
  public Integer updateStauts(String[] ids, String status) {
    int c = 0;
    for (String id : ids) {
      c += updateStauts(id, status, false);
    }
    if (c > 0) {
      putRedis();
    }
    return c;
  }


  private Integer updateStauts(String id, String status, boolean updateRedis) {
    if (Constant.SYSTEM_COMMON_ENABLE.equals(status)
        || Constant.SYSTEM_COMMON_DISABLE.equals(status)) {
      int updateCount = sysMenuMapper.updateStatusByRootId(id, status);
      if (updateCount > 0 && updateRedis) {
        putRedis();
      }
      return updateCount;
    }
    return 0;
  }


  // 保存添加，只传名称
  public Integer saveAdd(SysMenuVO menus, String pid) {
    SysMenu pMenu = null;
    if (!StrUtils.isNoneBlank(pid)) {
      pMenu = this.selectByPrimaryKey(pid);
      if (pMenu == null) {
        return 0;
      }
    }
    int c = 0;
    for (SysMenu m : menus.getMenus()) {
      if (StrUtils.isNotBlank(m.getId())) {
        if (StrUtils.isNotBlank(m.getName()) && !m.getName().equals(m.getOldName())) {
          c += this.updateByPrimaryKeySelective(m);
        }
      } else {
        if (StringUtils.isNotBlank(m.getName())) {
          m.setStatus(Constant.SYSTEM_COMMON_ENABLE);
          m.setType(Constant.RESOURCE_TYPE_MENU);
          m.setCommon(Constant.RESOURCE_COMMON);
          m.setParentId(pid);
          m.setParentIds(StrUtils.isNoneBlank(pid) ? "0," : pMenu.getParentIds() + "" + pid + ",");
          c += this.insertSelective(m);
        }
      }
    }
    // 如果运行到这里，至少要返回1
    if (c == 0) {
      c = 1;
    } else {
      putRedis();
    }
    return c;
  }


  public List<SysMenu> findAllMenuFromReids() {
    String json = redisService.get(RedisKey.ALL_MENU);
    if (StringUtils.isNotBlank(json)) {
      return JSON.parseArray(json, SysMenu.class);
    } else {
      return getAll();
    }
  }



}
