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
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.yunmel.commons.utils.TreeUtils;
import com.yunmel.frame.common.utils.ControllerUtils;
import com.yunmel.frame.common.utils.SysUserUtils;
import com.yunmel.frame.common.utils.ValidUtils;
import com.yunmel.frame.consts.Constant;
import com.yunmel.frame.sys.model.SysOffice;
import com.yunmel.frame.sys.model.SysRole;
import com.yunmel.frame.sys.model.SysUser;
import com.yunmel.frame.sys.model.UnitType;
import com.yunmel.frame.sys.service.SysOfficeService;
import com.yunmel.frame.sys.service.SysRoleService;
import com.yunmel.frame.sys.service.SysUserService;
import com.yunmel.frame.sys.service.UnitTypeService;
import com.yunmel.frame.sys.vo.SysUserVO;

@Controller
@RequestMapping("sys/user")
public class SysUserController {

  @Resource
  private SysUserService sysUserService;
  @Resource
  private SysOfficeService sysOfficeService;
  @Resource
  private SysRoleService sysRoleService;
  @Resource
  private UnitTypeService unitTypeService;


  @RequestMapping
  public String toIndex(Model model, @RequestParam Map<String, Object> params) {
    List<UnitType> utypes = unitTypeService.findByParams(params);
    List<SysOffice> units = sysOfficeService.findAllOffice();
    model.addAttribute("utypes", utypes);
    model.addAttribute("units", units);
    return "sys/user/user-index";
  }

  @RequestMapping(value = "list", method = RequestMethod.POST)
  public @ResponseBody PageInfo<SysUser> userList(@RequestParam Map<String, Object> params) {
    params = ControllerUtils.dealCoding(params, "name");
    return sysUserService.findPageInfo(params);
  }


  @RequestMapping(value = "{mode}/dialog")
  public String toDialog(@PathVariable String mode, Model model, String id) {
    Map<String, SysRole> rolesMap = Maps.newHashMap(), findUserRoleMap = null;

    if ("add".equals(mode)) {
      List<SysOffice> sysOffices = sysOfficeService.findAllOffice();
      model.addAttribute("sysOffices", sysOffices);

      // 角色
      rolesMap.putAll(sysRoleService.findCurUserRoleMap());
      model.addAttribute("rolesMap", rolesMap);
      return "sys/user/user-save";
    } else if ("edit".equals(mode)) {
      if (id == null)
        return null;
      SysUser sysUser = sysUserService.selectByPrimaryKey(id);
      SysOffice sysOffice = sysOfficeService.selectByPrimaryKey(sysUser.getUnitId());
      model.addAttribute("sysUser", sysUser);
      model.addAttribute("office", sysOffice);

      // 角色
      findUserRoleMap = sysRoleService.findUserRoleMapByUser(sysUser);
      rolesMap.putAll(sysRoleService.findCurUserRoleMap());
      rolesMap.putAll(findUserRoleMap);
      model.addAttribute("rolesMap", rolesMap).addAttribute("findUserRoleMap", findUserRoleMap);

      List<SysOffice> sysOffices = sysOfficeService.findAllOffice();
      model.addAttribute("sysOffices", TreeUtils.toTreeNodeList(sysOffices, SysOffice.class));
      return "sys/user/user-edit";
    } else if ("upload".equals(mode)) {

    }
    return null;
  }


  @RequestMapping(value = "save", method = RequestMethod.POST)
  public @ResponseBody Integer save(@ModelAttribute SysUser sysUser) {
    if (sysUser.getUserType() == null || sysUser.getUserType().equals("")) {
      sysUser.setUserType(Constant.SUPER_NORMAL);
    }
    return sysUserService.saveSysUser(sysUser);
  }

  /**
   * 更新状态
   * 
   * @return
   */
  @RequestMapping(value = "update/status", method = RequestMethod.POST)
  public @ResponseBody Integer updateStauts(@RequestParam(value = "ids[]") String[] ids,
      String status) {
    Integer count = 0;
    if (null != ids && StringUtils.isNotBlank(status)) {
      count = sysUserService.updateStauts(ids, status);
    }
    return count;
  }

  /**
   * 更新状态
   * 
   * @return
   */
  @RequestMapping(value = "update/status/{id}", method = RequestMethod.POST)
  public @ResponseBody Integer updateStatusById(@PathVariable String id, String status) {
    Integer count = 0;
    if (null != id && StringUtils.isNotBlank(status)) {
      count = sysUserService.updateStauts(id, status);
    }
    return count;
  }

  @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
  public @ResponseBody Integer deleteUser(@PathVariable String id) {
    Integer count = 0;
    if (null != id) {
      count = sysUserService.deleteUser(id);
    }
    return count;
  }

  @RequestMapping(value = "deletes", method = RequestMethod.POST)
  public @ResponseBody Integer dels(@RequestParam(value = "ids[]") String[] ids) {
    Integer count = 0;
    if (null != ids) {
      count = sysUserService.deleteUser(ids);
    }
    return count;
  }

  @RequestMapping(value = "pwd/reset", method = RequestMethod.POST)
  public @ResponseBody Integer pwdReset(@RequestParam(value = "ids[]") String[] ids) {
    Integer count = 0;
    if (null != ids) {
      count = sysUserService.resetPwd(ids);
    }
    return count;
  }

  @RequestMapping(value = "/detail/{id}", method = RequestMethod.POST)
  public String detail(@PathVariable String id, Model model) {

    SysUser sysUser = sysUserService.selectByPrimaryKey(id);
    model.addAttribute("sysUser", sysUser);
    return "sys/user/user-detail";
  }



  // 批量添加页面
  @RequestMapping(value = "add")
  public String toAdd(Model model, String id) {
    List<SysOffice> so = SysUserUtils.getUserOffice();
    model.addAttribute("treeList",
        JSON.toJSONString(TreeUtils.toTreeNodeList(so, SysOffice.class)));
    return "sys/user/user-add";
  }

  // 批量保存
  @RequestMapping(value = "save/add", method = RequestMethod.POST)
  public @ResponseBody Integer saveAdd(@ModelAttribute SysUserVO sysUser, String officeId,
      String password) {
    return sysUserService.addUser(sysUser, officeId, password);
  }


  @RequestMapping(value = "check/{field}", method = RequestMethod.POST)
  public @ResponseBody Map<String, Boolean> check(@PathVariable String field, String id,
      HttpServletRequest request) {
    String value = request.getParameter(field);
    return ValidUtils.buildValid(this.sysUserService.unique(SysUser.class, field, value, id));
  }

  // ----------------------------------------------用户审批---------------------------------------------

  @RequestMapping(value = "approve")
  public String toUserExamine(Model model) {
    return "sys/user-approve/index";
  }


  @RequestMapping(value = "approve/list", method = RequestMethod.POST)
  public @ResponseBody PageInfo<SysUser> examineList(@RequestParam Map<String, Object> params) {
    params.put("status", Constant.USER_UN_APPROVE);
    return sysUserService.findUnApproveUser(params);
  }

  /**
   * 审核
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "approve/approveDialog/{id}", method = RequestMethod.POST)
  public String approveDialog(Model model, @PathVariable String id) {
    model.addAttribute("sysOffice", sysOfficeService.findAllOffice());
    model.addAttribute("sysUser", sysUserService.selectByPrimaryKey(id));
    return "sys/user-approve/approve";
  }

  /**
   * 审核
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "approve/detail/dialog", method = RequestMethod.POST)
  public String detailDialog(Model model, String id) {
    model.addAttribute("sysUser", sysUserService.selectByPrimaryKey(id));
    return "sys/user-approve/detail";
  }
}
