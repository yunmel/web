package com.yunmel.frame.web.sys;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yunmel.commons.utils.StrUtils;
import com.yunmel.commons.utils.TreeUtils;
import com.yunmel.frame.consts.Constant;
import com.yunmel.frame.sys.model.SysMenu;
import com.yunmel.frame.sys.service.SysMenuService;
import com.yunmel.frame.sys.vo.SysMenuVO;

/**
 * 菜单管理
 * 
 * @ClassName: MenuController
 * @author taosq
 * @date 2016年3月30日 上午11:38:28
 *
 */

@Controller
@RequestMapping("sys/menu")
public class SysMenuController {

  @Resource
  private SysMenuService sysMenuService;

  /**
   * 跳转到菜单管理页面
   * 
   * @param model
   * @return 菜单管理模块html
   */
  @RequestMapping
  public String toMenu(Model model) {
    List<SysMenu> srs = sysMenuService.getAllFromRedis();
    model.addAttribute("treeList", JSON.toJSONString(TreeUtils.toTreeNodeList(srs, SysMenu.class)));
    return "sys/menu/menu";
  }

  @RequestMapping("mng")
  public String toListMng(Model model) {
    List<SysMenu> srs = sysMenuService.getAllFromRedis();
    // 获取第一层用于编辑
    List<SysMenu> rootMenus = Lists.newArrayList();
    for (SysMenu sm : srs) {
      if (StrUtils.isNoneBlank(sm.getParentId())) {
        rootMenus.add(sm);
      }
    }
    model.addAttribute("rootMenus", rootMenus);
    model.addAttribute("treeList", JSON.toJSONString(TreeUtils.toTreeNodeList(srs, SysMenu.class)));
    return "sys/menu/mng";
  }

  @RequestMapping(value = "left/menu", method = RequestMethod.POST)
  public String leftMenu(Model model, String select) {
    List<SysMenu> srs = sysMenuService.getAllFromRedis();
    model.addAttribute("select", select);
    model.addAttribute("treeList", JSON.toJSONString(TreeUtils.toTreeNodeList(srs, SysMenu.class)));
    return "sys/menu/left-menu";
  }


  @RequestMapping(value = "add", method = RequestMethod.POST)
  public String toAdd(Model model, String pid, String pnames) {
    List<SysMenu> srs = sysMenuService.getAllFromRedis();

    if (pid == null || pid.equals("0")) {
      // 获取第一层用于编辑
      List<SysMenu> rootMenus = Lists.newArrayList();
      for (SysMenu sm : srs) {
        if (sm.getParentId() == null || sm.getParentId().equals("0")) {
          rootMenus.add(sm);
        }
      }
      model.addAttribute("menus", rootMenus);
    } else {
      SysMenu sm = new SysMenu();
      sm.setParentId(pid);
      model.addAttribute("menus", this.sysMenuService.select(sm));
    }
    model.addAttribute("pid", pid);
    model.addAttribute("pnames", pnames);
    return "sys/menu/menu-add";
  }


  @RequestMapping(value = "add/save", method = RequestMethod.POST)
  public @ResponseBody Integer toAddSave(Model model, @ModelAttribute SysMenuVO menus, String pid) {
    return sysMenuService.saveAdd(menus, pid);
  }


  @RequestMapping(value = "edit/{id}", method = RequestMethod.POST)
  public String toEdit(Model model, @PathVariable String id) {
    SysMenu menu = sysMenuService.selectByPrimaryKey(id);
    SysMenu pMenu = null;
    if (menu != null && StrUtils.isNoneBlank(menu.getParentId())) {
      pMenu = sysMenuService.selectByPrimaryKey(menu.getParentId());
    }
    model.addAttribute("menu", menu);
    model.addAttribute("pMenu", pMenu);

    List<SysMenu> srs = sysMenuService.getAllFromRedis();
    List<SysMenu> pList = Lists.newArrayList();
    // 去掉按钮
    for (SysMenu m : srs) {
      if (Constant.RESOURCE_TYPE_MENU.equals(m.getType())) {
        pList.add(m);
      }
    }
    model.addAttribute("treeList",
        JSON.toJSONString(TreeUtils.toTreeNodeList(pList, SysMenu.class)));

    return "sys/menu/menu-edit";
  }


  /**
   * 更新
   */
  @RequestMapping(value = "edit/save", method = RequestMethod.POST)
  public @ResponseBody Integer save(@ModelAttribute SysMenu SysMenu) {
    return sysMenuService.saveSysMenu(SysMenu);
  }



  /**
   * 菜单列表，可查询，直接查询数据库
   * 
   * @param params
   * @param model
   * @return
   */
  @RequestMapping(value = "list", method = RequestMethod.POST)
  public @ResponseBody List<SysMenu> list(@RequestParam Map<String, Object> params, Model model) {
    return sysMenuService.find(params);
  }



  /**
   * 删除菜单及其子菜单
   * 
   * @param resourceId 菜单id
   * @return
   */
  @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
  public @ResponseBody Integer del(@PathVariable String id) {
    Integer count = 0;
    if (null != id) {
      count = sysMenuService.deleteMenuByRootId(id);
    }
    return count;
  }


  /**
   * 删除菜单及其子菜单
   * 
   * @param resourceId 菜单id
   * @return
   */
  @RequestMapping(value = "deletes", method = RequestMethod.POST)
  public @ResponseBody Integer dels(@RequestParam(value = "ids[]") String[] ids) {
    Integer count = 0;
    if (null != ids) {
      count = sysMenuService.deleteMenuByRootId(ids);
    }
    return count;
  }


  /**
   * 更新状态
   * 
   * @param resourceId 菜单id
   * @return
   */
  @RequestMapping(value = "update/status", method = RequestMethod.POST)
  public @ResponseBody Integer updateStauts(@RequestParam(value = "ids[]") String[] ids,
      String status) {
    Integer count = 0;
    if (null != ids && StrUtils.isNotBlank(status)) {
      count = sysMenuService.updateStauts(ids, status);
    }
    return count;
  }



  /**
   * 更新状态
   * 
   * @param resourceId 菜单id
   * @return
   */
  @RequestMapping(value = "update/status/{id}", method = RequestMethod.POST)
  public @ResponseBody Integer updateStatusById(@PathVariable String id, String status) {
    Integer count = 0;
    if (null != id && StrUtils.isNotBlank(status)) {
      count = sysMenuService.updateStauts(id, status);
    }
    return count;
  }

  @RequestMapping(value = "/update/sort/{id}", method = RequestMethod.POST)
  public @ResponseBody Integer updateSort(@PathVariable String id, Integer sort) {
    Integer count = 0;
    if (id != null && sort != null) {
      count = sysMenuService.updateSort(id, sort);
    }
    return count;
  }


  /**
   * @return
   */
  @RequestMapping(value = "/detail/{id}", method = RequestMethod.POST)
  public String detail(@PathVariable String id, Model model) {

    SysMenu menu = sysMenuService.selectByPrimaryKey(id);
    SysMenu pMenu = null;
    if (!StrUtils.isNoneBlank(menu.getParentId())) {
      pMenu = sysMenuService.selectByPrimaryKey(menu.getParentId());
    }

    model.addAttribute("pMenu", pMenu).addAttribute("menu", menu);
    return "sys/menu/menu-detail";
  }


}
