package com.yunmel.frame.web.sys;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.yunmel.commons.utils.StrUtils;
import com.yunmel.frame.consts.Constant;
import com.yunmel.frame.sys.service.SysLogService;

@Controller
@RequestMapping("sys/monitor/db")
public class SQLExecutorController {

  @Resource
  private SysLogService sysLogService;


  @RequestMapping("druid")
  public String showDruid() {
    return "sys/monitor/db/druid";
  }

  @RequestMapping(value = "sql", method = RequestMethod.GET)
  public String showSQLForm() {
    return "sys/monitor/db/sqlForm";
  }


  @RequestMapping(value = "result", method = RequestMethod.POST)
  public String toResult(@RequestParam Map<String, Object> params, String sql, final Model model) {
    try {
      if (sql != null) {
        String lowerCaseSQL = sql.trim().toLowerCase();
        final boolean isDQL = lowerCaseSQL.startsWith("select");
        if (!isDQL) {
          model.addAttribute(Constant.MSG, "您执行的SQL不允许，只允许查询语句");
        } else {
          params.put("sql", sql);
          params.put("pageSize", 1);
          params.put("pageNum", 1);
          PageInfo<Map<String, Object>> page = sysLogService.selectList(params);
          if (page == null || page.getTotal() == 0) {
            model.addAttribute(Constant.MSG, "没有查询结果");
          } else {
            model.addAttribute("columns", getColumns(page.getList())).addAttribute("sql", sql);
          }
        }
      }
    } catch (Exception e) {
      StringWriter sw = new StringWriter();
      e.printStackTrace(new PrintWriter(sw));
      model.addAttribute(Constant.MSG, sw.toString());
    }
    return "sys/monitor/db/result";
  }



  @RequestMapping(value = "sql/execute", method = RequestMethod.POST)
  public @ResponseBody PageInfo<Map<String, Object>> executeSQL(
      @RequestParam Map<String, Object> params, String sql) {
    String lowerCaseSQL = sql.trim().toLowerCase();
    final boolean isDQL = lowerCaseSQL.startsWith("select");
    if (!isDQL) {
      return null;
    }
    params.put("sql", sql);
    return sysLogService.selectList(params);
  }



  public Map<String, String> getColumns(List<Map<String, Object>> list) {
    Map<String, String> columns = Maps.newHashMap();
    if (list != null && list.size() > 0) {
      Map<String, Object> keyMap = null;
      int length = 0;
      for (Map<String, Object> objectMap : list) {
        if (objectMap.size() > length) {
          length = objectMap.size();
          keyMap = objectMap;
        }
      }
      if (keyMap != null && length > 0) {
        Set<Map.Entry<String, Object>> entry = keyMap.entrySet();
        for (Map.Entry<String, Object> objectEntry : entry) {
          columns.put(objectEntry.getKey(), StrUtils.camelhumpToUnderline(objectEntry.getKey()));
        }
      }
    }
    return columns;
  }

}
