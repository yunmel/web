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

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yunmel.commons.utils.StrUtils;
import com.yunmel.commons.utils.TreeUtils;
import com.yunmel.frame.common.utils.ValidUtils;
import com.yunmel.frame.sys.model.SysDict;
import com.yunmel.frame.sys.model.SysDictType;
import com.yunmel.frame.sys.service.SysDictService;
import com.yunmel.frame.sys.service.SysDictTypeService;
import com.yunmel.frame.sys.vo.SysDictTypeVO;
import com.yunmel.frame.sys.vo.SysDictVO;

@Controller
@RequestMapping("sys/dict")
public class SysDictController {

  @Resource
  private SysDictService sysDictService;

  @Resource
  private SysDictTypeService sysDictTypeService;

  @RequestMapping
  public String toDict(Model model) {
    List<SysDictType> sdt = sysDictTypeService.getAll();
    model.addAttribute("treeList",
        JSON.toJSONString(TreeUtils.toTreeNodeList(sdt, SysDictType.class)));
    return "sys/dict/dict";
  }

  // /**
  // * 添加或更新区域
  // * @param params
  // * @return
  // */
  // @RequestMapping(value = "save", method = RequestMethod.POST)
  // public @ResponseBody Integer save(@ModelAttribute SysDict sysDict) {
  // return sysDictService.saveDict(sysDict);
  // }

  /**
   * 查询
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "list", method = RequestMethod.POST)
  public @ResponseBody PageInfo<SysDict> list(@RequestParam Map<String, Object> params) {
    PageHelper.startPage(params);
    List<SysDict> list = null;
    if (params.get("id") == null || "".equals(params.get("id"))) {
      list = sysDictService.find(params);
    } else {
      SysDictType sdt = sysDictTypeService.selectByPrimaryKey(params.get("id"));
      if (sdt == null || sdt.getCode() == null || sdt.getCode().equals(""))
        return null;

      params.remove("id");
      params.put("type", sdt.getCode());
      list = sysDictService.find(params);
    }
    return new PageInfo<>(list);
  }

  @RequestMapping("mng")
  public String toListMng(Model model, @RequestParam Map<String, Object> params) {
    List<SysDictType> srs = sysDictTypeService.getAll();
    // 获取第一层用于编辑
    List<SysDictType> rootDict = Lists.newArrayList();
    for (SysDictType sm : srs) {
      if (StrUtils.isNoneBlank(sm.getParentId())) {
        rootDict.add(sm);
      }
    }
    model.addAttribute("rootDict", rootDict);
    model.addAttribute("treeList",
        JSON.toJSONString(TreeUtils.toTreeNodeList(srs, SysDictType.class)));
    return "sys/dict/mng";
  }


  // @RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
  // public String showLayer(String id, Model model){
  // SysDict dict = sysDictService.selectByPrimaryKey(id);
  // if(dict != null){
  // SysDictType pType = sysDictTypeService.selectDicTypeByCode(dict.getType());
  // model.addAttribute("pType", pType);
  // }
  // model.addAttribute("dict", dict);
  // return "sys/dict/dict-save";
  // }



  /**
   * 删除数据字典
   */
  @RequestMapping(value = "deleteDict/{id}", method = RequestMethod.POST)
  public @ResponseBody Integer del(@PathVariable String id) {
    Integer count = 0;
    if (null != id) {
      count = sysDictService.deleteDict(id);
    }
    return count;
  }


  /**
   * 删除数据字典
   * 
   * @return
   */
  @RequestMapping(value = "deletes", method = RequestMethod.POST)
  public @ResponseBody Integer dels(@RequestParam(value = "ids[]") String[] ids) {
    Integer count = 0;
    if (null != ids) {
      count = sysDictService.deleteDict(ids);
    }
    return count;
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
    if (null != ids && StrUtils.isNotBlank(status)) {
      count = sysDictService.updateStauts(ids, status);
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
    if (null != id && StrUtils.isNotBlank(status)) {
      count = sysDictService.updateStauts(id, status);
    }
    return count;
  }

  @RequestMapping(value = "/update/sort/{id}", method = RequestMethod.POST)
  public @ResponseBody Integer updateSort(@PathVariable String id, Integer sort) {
    Integer count = 0;
    if (id != null && sort != null) {
      count = sysDictService.updateSort(id, sort);
    }
    return count;
  }

  @RequestMapping(value = "left/dictType", method = RequestMethod.POST)
  public String leftMenu(Model model, String select) {
    List<SysDictType> srs = sysDictTypeService.getAll();
    // model.addAttribute("select",select);
    model.addAttribute("treeList",
        JSON.toJSONString(TreeUtils.toTreeNodeList(srs, SysDictType.class)));
    return "sys/dict/type/left-dictType";
  }

  /**
   * 添加分类页面
   * 
   * @param model
   * @param pid
   * @param pnames
   * @return
   */
  @RequestMapping(value = "addDictType", method = RequestMethod.POST)
  public String addDictType(Model model, String pid) {
    List<SysDictType> srs = sysDictTypeService.getAll();

    if (StrUtils.isNoneBlank(pid)) {
      // 获取第一层用于编辑
      List<SysDictType> rootMenus = Lists.newArrayList();
      for (SysDictType sm : srs) {
        if (StrUtils.isNoneBlank(pid)) {
          rootMenus.add(sm);
        }
      }
      model.addAttribute("menus", rootMenus);
    } else {
      SysDictType sm = new SysDictType();
      sm.setParentId(pid);
      model.addAttribute("menus", this.sysDictTypeService.select(sm));
    }
    model.addAttribute("pid", pid);

    return "sys/dict/type/dictType-add";
  }


  @RequestMapping(value = "add/saveType", method = RequestMethod.POST)
  public @ResponseBody Integer toAddSave(Model model, @ModelAttribute SysDictTypeVO sysDictType,
      String pid) {
    return sysDictTypeService.saveAdd(sysDictType, pid);
  }

  //
  //
  @RequestMapping(value = "editType/{id}", method = RequestMethod.POST)
  public String toEdit(Model model, @PathVariable String id) {
    SysDictType sysDictType = sysDictTypeService.selectByPrimaryKey(id);
    SysDictType pSysDictType = sysDictTypeService.selectByPrimaryKey(sysDictType.getParentId());

    model.addAttribute("pSysDictType", pSysDictType);
    model.addAttribute("sysDictType", sysDictType);
    List<SysDictType> sysDictTypeList = sysDictTypeService.getAll();
    model.addAttribute("treeList", TreeUtils.toTreeNodeList(sysDictTypeList, SysDictType.class));
    return "sys/dict/type/dictType-edit";
  }

  //
  //
  /**
   * 更新
   */
  @RequestMapping(value = "editType/save", method = RequestMethod.POST)
  public @ResponseBody Integer save(@ModelAttribute SysDictType sysDictType) {
    return sysDictTypeService.saveDicType(sysDictType);
  }

  /**
   * 删除分类及其子分类
   * 
   * @param resourceId 菜单id
   * @return
   */
  @RequestMapping(value = "deleteType/{id}", method = RequestMethod.POST)
  public @ResponseBody Integer delType(@PathVariable String id) {
    Integer count = 0;
    if (null != id) {
      count = sysDictTypeService.deleteDictTypeByRootId(id);
    }
    return count;
  }

  /**
   * 删除菜单及其子菜单
   * 
   * @param resourceId 菜单id
   * @return
   */
  @RequestMapping(value = "deletesType", method = RequestMethod.POST)
  public @ResponseBody Integer delsType(@RequestParam(value = "ids[]") String[] ids) {
    Integer count = 0;
    if (null != ids) {
      count = sysDictTypeService.deleteDictTypeByRootId(ids);
    }
    return count;
  }

  // -------------------------------------------------------------------------------------------


  @RequestMapping(value = "saveDict", method = RequestMethod.POST)
  public @ResponseBody Integer saveDict(@ModelAttribute SysDict sysDict) {
    return sysDictService.saveDict(sysDict);
  }

  @RequestMapping(value = "/detail/{id}", method = RequestMethod.POST)
  public String detail(@PathVariable String id, Model model) {

    SysDict sysDict = sysDictService.selectByPrimaryKey(id);
    model.addAttribute("sysDict", sysDict);
    return "sys/dict/dict-detail";
  }


  @RequestMapping(value = "{mode}/dialog")
  public String toDialog(@PathVariable String mode, Model model, String id) {
    if ("add".equals(mode)) {
      List<SysDictType> sysDictType = sysDictTypeService.getAll();
      model.addAttribute("dictType", TreeUtils.toTreeNodeList(sysDictType, SysDictType.class));
      return "sys/dict/dict-save";
    } else if ("edit".equals(mode)) {
      if (id == null)
        return null;
      SysDict sysDict = sysDictService.selectByPrimaryKey(id);
      Map<String, Object> params = Maps.newHashMap();
      params.put("code", sysDict.getType());
      List<SysDictType> tList = sysDictTypeService.findSysDictTypeByCode(params);
      String typeName = "";
      if (tList != null && tList.size() > 0) {
        typeName = tList.get(0).getName();
      }
      model.addAttribute("sysDict", sysDict);
      model.addAttribute("typeName", typeName);
      List<SysDictType> sysDictType = sysDictTypeService.getAll();
      model.addAttribute("dictType", TreeUtils.toTreeNodeList(sysDictType, SysDictType.class));
      return "sys/dict/dict-save";


    } else if ("upload".equals(mode)) {

    }
    return null;
  }


  // 批量
  @RequestMapping(value = "dict-add")
  public String toAdd(Model model, String id) {
    List<SysDictType> sdt = sysDictTypeService.getAll();
    model.addAttribute("treeList",
        JSON.toJSONString(TreeUtils.toTreeNodeList(sdt, SysDictType.class)));
    return "sys/dict/dict-add";
  }

  // 批量页面（内嵌）
  @RequestMapping(value = "dict-add-page/{typeId}")
  public String toAddPage(Model model, @PathVariable String typeId) {
    SysDictType sysDictType = sysDictTypeService.selectByPrimaryKey(typeId);
    model.addAttribute("sysDictType", sysDictType);
    return "sys/dict/dict-add-page";
  }

  // 批量
  @RequestMapping(value = "save/dict-add", method = RequestMethod.POST)
  public @ResponseBody Integer saveAdd(@ModelAttribute SysDictVO sysDict, String type) {
    return sysDictService.addDict(sysDict, type);
  }

  // ------------------------------------唯一性校验-----------------------------------
  @RequestMapping(value = "check/{field}", method = RequestMethod.POST)
  public @ResponseBody Map<String, Boolean> check(@PathVariable String field, String id,
      HttpServletRequest request) {
    String value = request.getParameter(field);
    return ValidUtils.buildValid(this.sysDictService.unique(SysDict.class, field, value, id));
  }

  // 字典验证同意分类下是否存在键值-value(字典-分类专用)
  @RequestMapping(value = "checkDictValue/{field}", method = RequestMethod.POST)
  public @ResponseBody Map<String, Boolean> checkValue(@PathVariable String field, String type,
      String id, HttpServletRequest request) {
    String value = request.getParameter(field);
    System.out.println(field + "---" + value + "---" + type);
    return ValidUtils.buildValid(sysDictService.checkDictValue(field, value, type, id));
  }
}
