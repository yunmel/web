package com.yunmel.frame.common.beetl.function;

import java.util.Date;

import org.apache.commons.lang3.StringEscapeUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class StrutilFunction {

  public String subStringTo(String str, int start, int end) {
    if (str != null && str.length() > 0) {
      return str.substring(start, end);
    }
    return "";
  }

  public String subStringLen(String str, int len) {
    if (str != null && str.length() > 0 && len < str.length()) {
      return str.substring(0, len) + "…";
    }
    return str;
  }

  public String formatDate(Date date) {
    return new DateTime(date).toString("yyyy-MM-dd HH:mm:ss");
  }
  
  public String formatDateLong(Long date) {
    return new DateTime(date).toString("yyyy-MM-dd HH:mm:ss");
  }

  public String toHtml(String str) {
    return StringEscapeUtils.unescapeHtml4(str);
  }

  public String toHtml(String str, int len) {
    str = StringEscapeUtils.unescapeHtml4(str);
    return subStringLen(str, len);
  }
}
