package com.yunmel.frame.web.sys;

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
import com.yunmel.commons.controller.BaseController;
import com.yunmel.frame.common.utils.ValidUtils;
import com.yunmel.frame.sys.model.UnitType;
import com.yunmel.frame.sys.service.UnitTypeService;

/**
 * 单位类型
 * 
 * @author I am Unruly
 *
 */
@Controller
@RequestMapping("sys/unit")
public class SysUnitTypeController extends BaseController {
  @Resource
  private UnitTypeService unitTypeService;

  @RequestMapping
  public String toIndex(Model model) {
    return "sys/unitType/index";
  }

  @RequestMapping(value = "list", method = RequestMethod.POST)
  public @ResponseBody PageInfo<UnitType> list(@RequestParam Map<String, Object> params) {
    return unitTypeService.findPageInfo(params);
  }


  @RequestMapping(value = "{mode}/dialog")
  public String toDialog(@PathVariable String mode, Model model, String id) {
    if ("add".equals(mode)) {
      return "sys/unitType/unitype-form";
    } else if ("edit".equals(mode)) {
      model.addAttribute("unitType", unitTypeService.selectByPrimaryKey(id));
      return "sys/unitType/unitype-form";
    } else {
      model.addAttribute("unitType", unitTypeService.selectByPrimaryKey(id));
      return "sys/unitType/unitype-detail";
    }
  }

  @RequestMapping(value = "save", method = RequestMethod.POST)
  public @ResponseBody Integer save(@ModelAttribute UnitType unitType) {
    return unitTypeService.saveUnitType(unitType);
  }

  /**
   * 删除
   * 
   * @param
   * @return
   */
  @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
  public @ResponseBody Integer del(@PathVariable String id) {
    return unitTypeService.updateDelFlagToDelStatusById(UnitType.class, id);
  }

  /**
   * 批量删除
   * 
   * @param
   * @return
   */
  @RequestMapping(value = "deletes", method = RequestMethod.POST)
  public @ResponseBody Integer dels(@RequestParam(value = "ids[]") String[] ids) {
    return unitTypeService.deleteUnitType(ids);
  }

  @RequestMapping(value = "check/{field}", method = RequestMethod.POST)
  public @ResponseBody Map<String, Boolean> check(@PathVariable String field, String id,
      HttpServletRequest request) {
    String value = request.getParameter(field);
    return ValidUtils.buildValid(this.unitTypeService.unique(UnitType.class, field, value, id));
  }

}
