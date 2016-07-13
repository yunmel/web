

package com.yunmel.frame.web.sys;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.yunmel.commons.utils.TreeUtils;
import com.yunmel.frame.common.utils.SysUserUtils;
import com.yunmel.frame.common.utils.ValidUtils;
import com.yunmel.frame.sys.model.SysMenu;
import com.yunmel.frame.sys.model.SysOffice;
import com.yunmel.frame.sys.model.SysRole;
import com.yunmel.frame.sys.model.SysUser;
import com.yunmel.frame.sys.service.SysMenuService;
import com.yunmel.frame.sys.service.SysOfficeService;
import com.yunmel.frame.sys.service.SysRoleService;
import com.yunmel.frame.sys.service.SysUserService;

/**
 * 
 * @author
 */

@Controller
@RequestMapping("sys/role")
public class SysRoleController {


  @Resource
  private SysRoleService sysRoleService;
  @Resource
  private SysUserService sysUserService;
  @Resource
  private SysOfficeService sysOfficeService;
  @Resource
  private SysMenuService sysMenuService;

  /**
   * 跳转到模块页面
   * 
   * @param model
   * @return 模块html
   */
  @RequestMapping
  public String toIndex(Model model) {
    Map<String, Object> params = Maps.newHashMap();
    model.addAttribute("roles", sysRoleService.findPageInfo(params));
    return "sys/role/role";
  }


  @RequestMapping(value = "{mode}/dialog", method = RequestMethod.POST)
  public String toDialog(String id, @PathVariable String mode, Model model) {
    if ("add".equals(mode)) {
      return "sys/role/role-save";
    } else if ("edit".equals(mode)) {
      model.addAttribute("role", sysRoleService.selectByPrimaryKey(id));
      return "sys/role/role-save";
    } else if ("user".equals(mode)) {
      List<SysUser> users = sysRoleService.findUserByRoleId(id);
      model.addAttribute("users", users).addAttribute("roleId", id);
      model.addAttribute("offices",
          TreeUtils.toTreeNodeList(SysUserUtils.getUserOffice(), SysOffice.class));
      return "sys/role/role-user";
    } else if ("menu".equals(mode)) {
      SysRole sysRole = sysRoleService.selectByPrimaryKey(id);
      List<String> resIds = sysRoleService.findResourceIdsByRoleId(id);
      model.addAttribute("resIds", JSON.toJSONString(resIds));
      model.addAttribute("menus",
          TreeUtils.toTreeNodeList(sysMenuService.findAllMenuFromReids(), SysMenu.class));
      model.addAttribute("role", sysRole).addAttribute("roleId", id);
      return "sys/role/role-menu";
    } else if ("data".equals(mode)) {
      SysRole sysRole = sysRoleService.selectByPrimaryKey(id);
      if (sysRole.getDataScope().equals("9")) {
        List<String> officeIds = sysRoleService.findOfficeIdsByRoleId(id);
        Map<String, String> officeIdMap =
            Maps.uniqueIndex(officeIds, new Function<String, String>() {
              @Override
              public String apply(String key) {
                return key;
              }
            });
        model.addAttribute("officeIdMap", officeIdMap);
      }
      model.addAttribute("role", sysRole);
      model.addAttribute("offices", sysOfficeService.findAllOfficeFromRedis());
      return "sys/role/role-data";
    }
    return null;
  }


  @RequestMapping(value = "save", method = RequestMethod.POST)
  public @ResponseBody Integer save(@ModelAttribute SysRole role) {
    return this.sysRoleService.saveSysRole(role);
  }


  @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
  public @ResponseBody Integer del(@PathVariable String id) {
    return sysRoleService.updateDelFlagToDelStatusById(SysRole.class, id);
  }


  @RequestMapping(value = "check/{field}", method = RequestMethod.POST)
  public @ResponseBody Map<String, Boolean> check(@PathVariable String field, String id,
      HttpServletRequest request) {
    String value = request.getParameter(field);
    return ValidUtils.buildValid(this.sysRoleService.unique(SysRole.class, field, value, id));
  }



  /**
   * 部门的人员
   * 
   * @param officeId
   * @return
   */
  @RequestMapping(value = "office/user", method = RequestMethod.POST)
  public @ResponseBody List<SysUser> officeUser(String officeId) {
    SysUser sysUser = new SysUser();
    sysUser.setUnitId(officeId);
    return sysUserService.select(sysUser);
  }


  /**
   * 保存角色绑定的用户
   * 
   * @return
   */
  @RequestMapping(value = "save/user", method = RequestMethod.POST)
  public @ResponseBody Integer saveUserRole(@ModelAttribute SysRole sysRole) {
    return sysRoleService.saveUserRole(sysRole);
  }



  /**
   * 保存角色的菜单
   * 
   * @return
   */
  @RequestMapping(value = "save/menu", method = RequestMethod.POST)
  public @ResponseBody Integer saveMenu(@ModelAttribute SysRole sysRole) {
    return sysRoleService.saveUserMenu(sysRole);
  }

  /**
   * 保存角色的部门
   * 
   * @return
   */
  @RequestMapping(value = "save/office", method = RequestMethod.POST)
  public @ResponseBody Integer saveOffice(@ModelAttribute SysRole sysRole) {
    return sysRoleService.saveUserOffice(sysRole);
  }



  /**
   * 分页显示
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "list", method = RequestMethod.POST)
  public @ResponseBody List<SysRole> list(@RequestParam Map<String, Object> params, Model model) {
    return sysRoleService.findPageInfo(params);
  }



  /**
   * 弹窗显示
   * 
   * @param params {"mode":"1.add 2.edit 3.detail}
   * @return
   */
  @RequestMapping(value = "{mode}/showlayer", method = RequestMethod.POST)
  public String layer(String id, @PathVariable String mode, Model model) {
    SysRole sysRole = null;
    if (StringUtils.equals("edit", mode)) {
      sysRole = sysRoleService.selectByPrimaryKey(id);
      List<String> resIds = sysRoleService.findResourceIdsByRoleId(id);
      if (sysRole.getDataScope().equals("9")) {
        List<String> officeIds = sysRoleService.findOfficeIdsByRoleId(id);
        model.addAttribute("officeIds", JSON.toJSON(officeIds));
      }
      model.addAttribute("resIds", JSON.toJSONString(resIds));
    }
    if (StringUtils.equals("detail", mode)) {
      sysRole = sysRoleService.selectByPrimaryKey(id);
      List<SysUser> users = sysRoleService.findUserByRoleId(id);
      List<SysMenu> resources = sysRoleService.findResourceByRoleId(id);
      model.addAttribute("users", users).addAttribute("resources", resources);
    }
    model.addAttribute("sysrole", sysRole);
    return mode.equals("detail") ? "sys/role/role-detail" : "sys/role/role-save";
  }


}
