package com.yunmel.frame.common.utils;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;
import com.yunmel.commons.service.RedisService;
import com.yunmel.commons.utils.TreeUtils;
import com.yunmel.frame.common.spring.SpringContextHolder;
import com.yunmel.frame.consts.Constant;
import com.yunmel.frame.consts.RedisKey;
import com.yunmel.frame.sys.model.SysMenu;
import com.yunmel.frame.sys.model.SysOffice;
import com.yunmel.frame.sys.model.SysRole;
import com.yunmel.frame.sys.model.SysUser;
import com.yunmel.frame.sys.service.SysMenuService;
import com.yunmel.frame.sys.service.SysOfficeService;
import com.yunmel.frame.sys.service.SysRoleService;
import com.yunmel.frame.sys.service.SysUserService;

/**
 * @ClassName:SysUserUtils
 * @date:2016年3月15日 下午8:12:41
 * @author taosq
 */
public class SysUserUtils {

  private static final Logger logger = LoggerFactory.getLogger(SysUserUtils.class);
  static Integer sessionTime = 1800;
  static SysMenuService sysMenuService = SpringContextHolder.getBean("sysMenuService");
  static SysUserService sysUserService = SpringContextHolder.getBean("sysUserService");
  static SysRoleService sysRoleService = SpringContextHolder.getBean("sysRoleService");
  static SysOfficeService sysOfficeService = SpringContextHolder.getBean("sysOfficeService");
  static RedisService redisService = SpringContextHolder.getBean("redisService");


  public static void cacheUser(SysUser user) {
    String userKey = RedisKey.getUserSessionKey(getSession().getId());
    String menuKey = RedisKey.getUserMenuKey(user.getId());
    String menuUrlKey = RedisKey.getUserMenuUrlKey(user.getId());
    String roleKey = RedisKey.getUserRoleKey(user.getId());
    List<SysMenu> resources = sysMenuService.findByUser(user);

    List<SysMenu> menus = Lists.newArrayList();
    for (SysMenu sr : resources) {
      if (Constant.RESOURCE_TYPE_MENU.equals(sr.getType())) {
        menus.add(sr);
      }
    }
    List<SysMenu> srs = TreeUtils.toTreeNodeList(menus, SysMenu.class);
    //
    try {
      redisService.set(userKey, JSONUtils.toJSONString(user), sessionTime);
      redisService.set(menuKey, JSONUtils.toJSONString(srs), sessionTime);
      redisService.set(menuUrlKey, JSON.toJSONString(sysMenuService.findUrlByUser(user)),
          sessionTime);
      redisService.set(roleKey, JSON.toJSONString(sysRoleService.findUserRoleListByUser(user)),
          sessionTime);
      redisService.set(RedisKey.ONLINE_USER_LIST,
          JSON.toJSONString(getOnlineUserId(getSession().getId())));
    } catch (Exception e) {
      logger.error("redis 缓存用户信息出错，用户信息将缓存到session中", e);
      getSession().setAttribute(userKey, user);
    }
  }

  public static List<String> getOnlineUserId(String id) {
    String json = redisService.get(RedisKey.ONLINE_USER_LIST);
    List<String> list = Lists.newArrayList();
    if (StringUtils.isNotBlank(json)) {
      list = (List<String>) JSON.parse(json);
    }
    if (!list.contains(id)) {
      list.add(id);
    }
    return list;
  }

  /**
   * 获取在线用户
   */
  public static List<SysUser> getOnlineUser() {
    Map<String, SysUser> sysUser = Maps.newHashMap();
    try {
      List<String> ids = Lists.newArrayList();
      String json = redisService.get(RedisKey.ONLINE_USER_LIST);
      if (StringUtils.isNotBlank(json)) {
        List<String> userIds = (List<String>) JSON.parse(json);
        for (String id : userIds) {
          SysUser user = getCacheLoginUser(id);
          if (user != null) {
            if (!ids.contains(id)) {
              ids.add(id);
            }
            sysUser.put(user.getId(), user);
          }
        }
        // 重新刷新用户在线列表
        redisService.set(RedisKey.ONLINE_USER_LIST, JSON.toJSONString(ids));
      }
    } catch (Exception e) {
      logger.error("获取在线用户出错", e);
    }
    return Lists.newArrayList(sysUser.values());
  }


  /**
   * 登录用户持有的资源
   * 
   * @return
   */
  public static List<SysMenu> getUserResources() {
    return sysMenuService.findMenuByUserFromRedis(getCacheLoginUser());
  }


  /**
   * 从缓存中取登录的用户
   */
  public static SysUser getCacheLoginUser() {
    return getCacheLoginUser(getSession().getId());
  }

  /**
   * 根据sessionId获取用户
   * 
   * @param sessionId
   * @return
   */
  public static SysUser getCacheLoginUser(String sessionId) {
    String userKey = RedisKey.getUserSessionKey(sessionId);
    try {
      String json = redisService.get(userKey);
      if (StringUtils.isNotBlank(json)) {
        return JSON.parseObject(json, SysUser.class);
      }
    } catch (Exception e) {
      Object obj = getSession().getAttribute(userKey);
      if (obj != null) {
        return (SysUser) obj;
      }
    }
    return null;
  }



  /**
   * 得到当前session
   */
  public static HttpSession getSession() {
    HttpSession session = getCurRequest().getSession();
    return session;
  }

  /**
   * @Title: getCurRequest
   * @Description:(获得当前的request)
   * @param:@return
   * @return:HttpServletRequest
   */
  public static HttpServletRequest getCurRequest() {
    RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
    if (requestAttributes != null && requestAttributes instanceof ServletRequestAttributes) {
      ServletRequestAttributes servletRequestAttributes =
          (ServletRequestAttributes) requestAttributes;
      return servletRequestAttributes.getRequest();
    }
    return null;
  }


  /**
   * 用户信息清空时，只清空session信息， 不能清空用户的菜单（一多用户多客户端登录时）
   */
  public static void clearCacheUser() {
    String userKey = RedisKey.getUserSessionKey(getSession().getId());
    try {
      redisService.del(userKey);
    } catch (Exception e) {
      getSession().removeAttribute(userKey);
      logger.error("redis 用户退出错误", e);
    }
  }


  public static void updateUserCache() {
    try {
      SysUser user = getCacheLoginUser();
      System.out.println(RedisKey.getUserSessionKey(getSession().getId()));
      redisService.expire(RedisKey.getUserSessionKey(getSession().getId()), sessionTime);
      redisService.expire(RedisKey.getUserMenuKey(user.getId()), sessionTime);
      redisService.expire(RedisKey.getUserMenuUrlKey(user.getId()), sessionTime);
      redisService.expire(RedisKey.getUserRoleKey(user.getId()), sessionTime);

      redisService.expire(RedisKey.getUserDataScopeKey(user.getId()), sessionTime);
      redisService.expire(RedisKey.getUserOfficeKey(user.getId()), sessionTime);

      logger.info("redis 更新缓存用户【{}】缓存成功", user.getName());
    } catch (Exception e) {
      logger.error("redis 更新缓存用户缓存失败", e);
    }
  }


  /**
   * 用户持有的数据范围,包含数据范围小的
   */
  public static List<String> getUserDataScope() {
    SysUser user = getCacheLoginUser();
    List<String> dataScope = null;
    String json = redisService.get(RedisKey.getUserDataScopeKey(user.getId()));
    if (StringUtils.isNotBlank(json)) {
      dataScope = JSON.parseArray(json, String.class);
    } else {
      if (user.isAdmin()) {
        dataScope = Constant.DATA_SCOPE_ADMIN;
      } else {
        dataScope = Lists.newArrayList();
        List<Integer> dc = Lists.transform(sysRoleService.findUserRoleFromRedis(user),
            new Function<SysRole, Integer>() {
              @Override
              public Integer apply(SysRole sysRole) {
                return Integer.parseInt(sysRole.getDataScope());
              }
            });
        int[] dataScopes = Ints.toArray(dc);
        if (dataScopes.length == 0)
          return dataScope;
        int min = Ints.min(dataScopes);
        for (int i = min, len = Integer.parseInt(Constant.DATA_SCOPE_CUSTOM); i <= len; i++) {
          dataScope.add(i + "");
        }
      }
      redisService.set(RedisKey.getUserDataScopeKey(user.getId()), JSON.toJSONString(dataScope),
          sessionTime);
    }
    return dataScope;
  }

  /**
   * 登录用户的机构
   * 
   * @return
   */
  public static List<SysOffice> getUserOffice() {
    SysUser sysUser = getCacheLoginUser();
    String json = redisService.get(RedisKey.getUserOfficeKey(sysUser.getId()));
    List<SysOffice> userOffices = null;
    if (StringUtils.isNotBlank(json)) {
      userOffices = JSON.parseArray(json, SysOffice.class);
    } else {
      if (sysUser.isAdmin()) {
        userOffices = sysOfficeService.findAllOffice();
      } else {
        // office.setUserDataScope(SysUserUtils.dataScopeFilterString(null, null));
        userOffices =
            sysOfficeService.findListByDataScope(SysUserUtils.dataScopeFilterString("o", null));
      }
      redisService.set(RedisKey.getUserOfficeKey(sysUser.getId()), JSON.toJSONString(userOffices));
    }
    return userOffices;
  }


  /**
   * 数据范围过滤
   * 
   * @param user 当前用户对象
   * @param officeAlias 机构表别名
   * @param userAlias 用户表别名，传递空，忽略此参数
   * @param field field[0] 用户表id字段名称 为了减少中间表连接
   * @return (so.office id=... or .. or)
   */
  public static String dataScopeFilterString(String officeAlias, String userAlias,
      String... field) {
    SysUser sysUser = getCacheLoginUser();
    if (StringUtils.isBlank(officeAlias))
      officeAlias = "sys_office";
    // 用户持有的角色
    List<SysRole> userRoles = sysRoleService.findUserRoleFromRedis(sysUser);
    // 临时sql保存
    StringBuilder tempSql = new StringBuilder();
    // 最终生成的sql
    String dataScopeSql = "";
    if (!sysUser.isAdmin()) {
      for (SysRole sr : userRoles) {
        if (StringUtils.isNotBlank(officeAlias)) {
          boolean isDataScopeAll = false;
          if (Constant.DATA_SCOPE_ALL.equals(sr.getDataScope())) {
            isDataScopeAll = true;
          } else if (Constant.DATA_SCOPE_SCHOOL.equals(sr.getDataScope())) {
            tempSql.append(" or " + officeAlias + ".id=" + sysUser.getUnitId());
          } else if (Constant.DATA_SCOPE_CUSTOM.equals(sr.getDataScope())) {
            List<String> offices = sysOfficeService.findUserDataScopeByUserId(sysUser.getId());
            if (offices.size() == 0)
              offices.add("");
            tempSql
                .append(" or " + officeAlias + ".id in (" + StringUtils.join(offices, ",") + ")");
          }
          if (!isDataScopeAll) {
            if (StringUtils.isNotBlank(userAlias)) {
              // or su.id=22
              if (field == null || field.length == 0)
                field[0] = "id";
              tempSql.append(" or " + userAlias + "." + field[0] + "=" + sysUser.getId());
            } else {
              tempSql.append(" or " + officeAlias + ".id is null");
            }
          } else {
            // 如果包含全部权限，则去掉之前添加的所有条件，并跳出循环。
            tempSql.delete(0, tempSql.length());
            break;
          }
        }
      } // for end

      if (StringUtils.isNotBlank(tempSql)) {
        dataScopeSql = "(" + tempSql.substring(tempSql.indexOf("or") + 2, tempSql.length()) + ")";
      }
    }
    return dataScopeSql;
  }

  // 清空和角色相关的缓存数据
  public static void clearUserRole() {
    List<SysUser> users = getOnlineUser();
    for (SysUser user : users) {
      redisService.set(RedisKey.getUserRoleKey(user.getId()),
          JSON.toJSONString(sysRoleService.findUserRoleListByUser(user)), sessionTime);
      redisService.del(RedisKey.getUserDataScopeKey(user.getId()));
      redisService.del(RedisKey.getUserOfficeKey(user.getId()));
    }
  }

  // 清空和角色相关的缓存数据
  public static void clearUserMenu() {
    List<SysUser> users = getOnlineUser();
    for (SysUser user : users) {
      String menuKey = RedisKey.getUserMenuKey(user.getId());
      String menuUrlKey = RedisKey.getUserMenuUrlKey(user.getId());
      List<SysMenu> resources = sysMenuService.findByUser(user);
      List<SysMenu> menus = Lists.newArrayList();
      for (SysMenu sr : resources) {
        if (Constant.RESOURCE_TYPE_MENU.equals(sr.getType())) {
          menus.add(sr);
        }
      }
      List<SysMenu> srs = TreeUtils.toTreeNodeList(menus, SysMenu.class);
      redisService.set(menuKey, JSONUtils.toJSONString(srs), sessionTime);
      redisService.set(menuUrlKey, JSON.toJSONString(sysMenuService.findUrlByUser(user)),
          sessionTime);
    }
  }


  // 清空和角色相关的缓存数据
  public static void clearUserDataScopeOffice() {
    List<SysUser> users = getOnlineUser();
    for (SysUser user : users) {
      redisService.del(RedisKey.getUserDataScopeKey(user.getId()));
      redisService.del(RedisKey.getUserOfficeKey(user.getId()));
    }
  }

  public static void clearUserOffice() {
    List<SysUser> users = getOnlineUser();
    for (SysUser user : users) {
      redisService.del(RedisKey.getUserOfficeKey(user.getId()));
    }
  }
}
