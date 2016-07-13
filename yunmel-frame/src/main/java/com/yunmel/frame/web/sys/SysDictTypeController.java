package com.yunmel.frame.web.sys;


import java.util.HashMap;
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
import com.yunmel.frame.common.utils.ValidUtils;
import com.yunmel.frame.sys.model.SysDictType;
import com.yunmel.frame.sys.service.SysDictTypeService;

@Controller
@RequestMapping("sys/dict/type")
public class SysDictTypeController {

  @Resource
  private SysDictTypeService sysDictTypeService;

  @RequestMapping
  public String toIndex(Model model) {
    List<SysDictType> srs = sysDictTypeService.getAllFromRedis();
    model.addAttribute("treeList", JSON.toJSONString(srs));
    return "sys/dict/type/dic-type";
  }


  @RequestMapping(value = "/update/sort/{id}", method = RequestMethod.POST)
  public @ResponseBody Integer updateSort(@PathVariable String id, Integer sort) {
    Integer count = 0;
    if (id != null && sort != null) {
      count = sysDictTypeService.updateSort(id, sort);
    }
    return count;
  }


  /**
   * 分类树
   * 
   * @return
   */
  @RequestMapping(value = "tree", method = RequestMethod.POST)
  public @ResponseBody List<SysDictType> getAreaTreeList() {
    List<SysDictType> list = sysDictTypeService.getAllFromRedis();
    return list;
  }


  /**
   * 分类列表，可查询，直接查询数据库
   * 
   * @param params
   * @param model
   * @return
   */
  @RequestMapping(value = "list", method = RequestMethod.POST)
  public @ResponseBody List<SysDictType> list(@RequestParam Map<String, Object> params,
      Model model) {
    return sysDictTypeService.find(params);
  }


  /**
   * 添加或更新区域
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "save", method = RequestMethod.POST)
  public @ResponseBody Integer save(@ModelAttribute SysDictType dictType) {
    return sysDictTypeService.saveDicType(dictType);
  }

  /**
   * 删除字典分类
   * 
   * @param id
   * @return
   */
  @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
  public @ResponseBody Integer del(@PathVariable String id) {
    return sysDictTypeService.deleteDictTypeByRootId(id);
  }

  /**
   * 删除字典分类
   * 
   * @param id
   * @return
   */
  @RequestMapping(value = "deletes", method = RequestMethod.POST)
  public @ResponseBody Integer dels(@RequestParam(value = "ids[]") String[] ids) {
    Integer count = 0;
    if (null != ids) {
      count = sysDictTypeService.deleteDictTypeByRootId(ids);
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
    if (null != ids && StringUtils.isNotBlank(status)) {
      count = sysDictTypeService.updateStauts(ids, status);
    }
    return count;
  }


  /**
   * 更新状态
   * 
   * @param id 分类id
   * @return
   */
  @RequestMapping(value = "update/status/{id}", method = RequestMethod.POST)
  public @ResponseBody Integer updateStatusById(@PathVariable String id, String status) {
    Integer count = 0;
    if (null != id && StringUtils.isNotBlank(status)) {
      count = sysDictTypeService.updateStauts(id, status);
    }
    return count;
  }



  @RequestMapping(value = "{mode}/showlayer", method = RequestMethod.POST)
  public String showLayer(String id, String parentId, @PathVariable("mode") String mode,
      Model model) {
    SysDictType type = null, pType = null;
    if (StringUtils.equalsIgnoreCase(mode, "add")) {
      pType = sysDictTypeService.selectByPrimaryKey(parentId);
    } else if (StringUtils.equalsIgnoreCase(mode, "edit")) {
      type = sysDictTypeService.selectByPrimaryKey(id);
      pType = sysDictTypeService.selectByPrimaryKey(parentId);
    }
    model.addAttribute("pType", pType).addAttribute("type", type);
    return "sys/dict/type/dict-type-save";
  }


  /**
   * 验证标识符是否存在
   * 
   * @param param
   * @return
   */
  @RequestMapping(value = "checkcode", method = RequestMethod.POST)
  public @ResponseBody Map<String, Object> checkCode(String param, String id) {
    Map<String, Object> msg = new HashMap<String, Object>();
    SysDictType st = new SysDictType();
    st.setCode(param);
    int count = sysDictTypeService.selectCount(st);
    if (count > 0) {
      msg.put("info", "标识符已经存在!");
      msg.put("status", "n");
    } else {
      msg.put("status", "y");
    }
    return msg;
  }

  @RequestMapping(value = "check/{field}", method = RequestMethod.POST)
  public @ResponseBody Map<String, Boolean> dels(@PathVariable String field, String id,
      HttpServletRequest request) {
    String value = request.getParameter(field);
    return ValidUtils
        .buildValid(this.sysDictTypeService.unique(SysDictType.class, field, value, id));
  }
}
