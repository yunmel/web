package com.yunmel.frame.common.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;

public class ControllerUtils {

  /**
   * 处理controller list请求等，form表单中的中文编码问题
   * @param params   
   * @param cloum  中文内容的字段名称
   * @return
   */
  public static Map<String, Object> dealCoding(Map<String, Object> params,String cloum){
    if(params.get(cloum)!=null && !params.get(cloum).equals("")){
      String params_cloum_str = (String) params.get(cloum);
      try {
          byte[] bytes = params_cloum_str.getBytes("iso8859-1");
          params_cloum_str = new String(bytes, Charset.forName("UTF-8"));
          params.put(cloum, params_cloum_str);
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }
    }
    return params;
  }
  
  
//  public static List<T> getExportData(Map<String, Object> params, String type,BaseService service,List<T> list){
//    if (params.containsKey("sortName")) {
//      // if("sname".equals(params.get("sortName"))){
//      // params.put("sortName", "school");
//      // }else if("cname".equals(params.get("sortName"))){
//      // params.put("sortName", "classes");
//      // }else if("gotype".equals(params.get("sortName"))){
//      // params.put("sortName", "go_type");
//      // }else if("gowhere".equals(params.get("sortName"))){
//      // params.put("sortName", "go_where");
//      // }
//    }
//    // params.put("dataScope", SysUserUtils.dataScopeFilterString("o",null));
//    if ("curPage".equals(type)) {
//      // 本页
//      list = service.findPageInfo(params).getList();
//    } else if ("form".equals(type)) {
//      // 搜索结果
//      params.remove("pageNum");
//      params.remove("pageSize");
//      list = service.findByParam(params);
//    } else {
//      list = service.findAll();
//    }
//    
//    
//    return list;
//  }
}
