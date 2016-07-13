

package com.yunmel.frame.web.sys;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.yunmel.commons.utils.StrUtils;
import com.yunmel.frame.common.utils.ControllerUtils;
import com.yunmel.frame.common.utils.ValidUtils;
import com.yunmel.frame.sys.model.SysOffice;
import com.yunmel.frame.sys.model.UnitType;
import com.yunmel.frame.sys.service.SysOfficeService;
import com.yunmel.frame.sys.service.UnitTypeService;

/**
 * 
 * @author
 */

@Controller
@RequestMapping("sys/office")
public class SysOfficeController {


  @Resource
  private SysOfficeService sysOfficeService;
  @Resource
  private UnitTypeService unitTypeService;

  // /**
  // * 跳转到模块页面
  // * @param model
  // * @return 模块html
  // */
  // @RequestMapping
  // public String toSysOffice(Model model,@RequestParam Map<String, Object> params){
  // List<SysOffice> list = sysOfficeService.findAllOffice();
  // model.addAttribute("treeList", JSON.toJSONString(TreeUtils.toTreeNodeList(list,
  // SysOffice.class)));
  // model.addAttribute("admin", Constant.SYS_ADMIN_USER);
  // return "sys/office/office";
  // }
  /**
   * 跳转到模块页面
   * 
   * @param model
   * @return 模块html
   */
  @RequestMapping
  public String toIndex(Model model, @RequestParam Map<String, Object> params) {
    return "sys/unit/unit-index";
  }

  @RequestMapping(value = "list", method = RequestMethod.POST)
  public @ResponseBody PageInfo<SysOffice> list(@RequestParam Map<String, Object> params) {
    // 处理编码
    params = ControllerUtils.dealCoding(params, "name");

    if (params.containsKey("sortName")) {
      params.put("sortC", StrUtils.camelhumpToUnderline(params.get("sortName").toString()));
    }
    return sysOfficeService.findPageInfo(params);
  }

  @RequestMapping(value = "{mode}/dialog")
  public String toDialog(@PathVariable String mode, Model model, String id) {

    if ("add".equals(mode) || "edit".equals(mode)) {
      if (id != null) {
        SysOffice unit = sysOfficeService.selectByPrimaryKey(id);
        model.addAttribute("unit", unit);
      }

      List<UnitType> utypes = unitTypeService.findAll();
      model.addAttribute("utypes", utypes);

      return "sys/unit/unit-form";
    } else if ("detail".equals(mode)) {
      SysOffice unit = sysOfficeService.selectByPrimaryKey(id);
      model.addAttribute("unit", unit);
      UnitType uType = unitTypeService.selectByPrimaryKey(unit.getType());
      model.addAttribute("uType", uType);
      return "sys/unit/unit-detail";
    }
    return null;
  }


  @RequestMapping(value = "save", method = RequestMethod.POST)
  public @ResponseBody Integer save(@ModelAttribute SysOffice sysOffice) {
    return sysOfficeService.saveSysOffice(sysOffice);
  }

  @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
  public @ResponseBody Integer del(@PathVariable String id) {
    return sysOfficeService.updateDelFlagToDelStatusById(SysOffice.class, id);
  }

  @RequestMapping(value = "deletes", method = RequestMethod.POST)
  public @ResponseBody Integer dels(@RequestParam(value = "ids[]") String[] ids) {
    return sysOfficeService.deleteOfficeBy(ids);
  }

  // ------------------------------------唯一性校验-----------------------------------
  @RequestMapping(value = "check/{field}", method = RequestMethod.POST)
  public @ResponseBody Map<String, Boolean> check(@PathVariable String field, String id,
      HttpServletRequest request) {
    String value = request.getParameter(field);
    return ValidUtils.buildValid(this.sysOfficeService.unique(SysOffice.class, field, value, id));
  }


  // @RequestMapping("mng")
  // public String toListMng(Model model,@RequestParam Map<String, Object> params){
  // List<SysOffice> srs = sysOfficeService.findAllOffice();
  // //获取第一层用于编辑
  // List<SysOffice> rootDict = Lists.newArrayList();
  // for (SysOffice sm : srs) {
  // if(sm.getParentId() == null || sm.getParentId() == 0){
  // rootDict.add(sm);
  // }
  // }
  // model.addAttribute("rootDict",rootDict);
  // model.addAttribute("treeList", JSON.toJSONString(TreeUtils.toTreeNodeList(srs,
  // SysOffice.class)));
  // return "sys/office/mng";
  // }
  //
  // @RequestMapping(value="left/office",method=RequestMethod.POST)
  // public String leftMenu(Model model,Long select){
  // List<SysOffice> list = sysOfficeService.findAllOffice();
  // model.addAttribute("treeList", JSON.toJSONString(TreeUtils.toTreeNodeList(list,
  // SysOffice.class)));
  // return "sys/office/left-office";
  // }
  //
  // @RequestMapping(value="add",method=RequestMethod.POST)
  // public String toAdd(Model model,Long pid,String pnames){
  //
  // List<SysOffice> srs = sysOfficeService.findAllOffice();
  //
  // //获取第一层用于编辑
  // List<SysOffice> rootMenus = Lists.newArrayList();
  // for (SysOffice sm : srs) {
  // if(sm.getParentId() == null || sm.getParentId() == 0){
  // rootMenus.add(sm);
  // }
  // }
  // model.addAttribute("menus",rootMenus);
  // return "sys/office/office-add";
  // }
  //
  // @RequestMapping(value="edit/{id}",method=RequestMethod.POST)
  // public String toEditOffice(Model model,@PathVariable Long id){
  // SysOffice sysOffice = sysOfficeService.selectByPrimaryKey(id);
  // SysOffice pSysOffice = null;
  // if(sysOffice != null && sysOffice.getParentId() != 0 && sysOffice.getParentId() != null){
  // pSysOffice = sysOfficeService.selectByPrimaryKey(sysOffice.getParentId());
  // }
  // model.addAttribute("sysOffice", sysOffice);
  // model.addAttribute("pSysOffice", pSysOffice);
  //
  // List<SysOffice> srs = sysOfficeService.findAllOffice();
  // model.addAttribute("treeList", JSON.toJSONString(TreeUtils.toTreeNodeList(srs,
  // SysOffice.class)));
  //
  // return "sys/office/office-edit";
  // }
  //
  // /**
  // * 更新
  // */
  // @RequestMapping(value = "save", method = RequestMethod.POST)
  // public @ResponseBody Integer editOfficeSave(@ModelAttribute SysOffice sysOffice) {
  // return sysOfficeService.saveSysOffice(sysOffice);
  // }
  //
  // //新增部门
  // @RequestMapping(value="add/save",method=RequestMethod.POST)
  // public @ResponseBody Integer toAddSave(Model model,@ModelAttribute SysOfficeVO offices,Long
  // pid){
  // return sysOfficeService.saveAdd(offices,pid);
  // }
  //
  //
  // /**
  // * 删除分类及其子分类
  // *
  // * @param resourceId
  // * 部门id
  // * @return
  // */
  // @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
  // public @ResponseBody Integer delType(@PathVariable Long id) {
  // Integer count = 0;
  // if (null != id) {
  // count = sysOfficeService.deleteOfficeByRootId(id);
  // }
  // return count;
  // }
  //
  // /**
  // * 删除部门及其子部门
  // *
  // * @param resourceId
  // * 部门id
  // * @return
  // */
  // @RequestMapping(value = "deletes", method = RequestMethod.POST)
  // public @ResponseBody Integer dels(@RequestParam(value = "ids[]") Long[] ids) {
  // Integer count = 0;
  // if (null != ids) {
  // count = sysOfficeService.deleteOfficeByRootId(ids);
  // }
  // return count;
  // }
  //
  //
  //
  // @RequestMapping(value = "check/{field}", method = RequestMethod.POST)
  // public @ResponseBody
  // Map<String, Boolean> check(@PathVariable String field, Long id,
  // HttpServletRequest request) {
  // String value = request.getParameter(field);
  // return ValidUtils.buildValid(this.sysOfficeService.unique(SysOffice.class, field, value, id));
  // }



  // @RequestMapping(value="getOfficeByProject/{project}",method=RequestMethod.POST)
  // public String getOfficeByProject(Model model,@PathVariable Long project,@RequestParam
  // Map<String, Object> params){
  //
  // params.put("project", project);
  //// List<SysOffice> sysOffices = sysOfficeService.findOfficeByProject(params);
  // List<SysOffice> sysOffices = sysOfficeService.findAllOffice();
  // model.addAttribute("units", sysOffices);
  //
  // return "sys/office/select-unit";
  // }
}
