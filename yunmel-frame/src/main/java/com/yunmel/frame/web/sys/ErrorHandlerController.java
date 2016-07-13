package com.yunmel.frame.web.sys;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yunmel.frame.common.utils.LogUtils;


@Controller
@RequestMapping("sys/error/handler")
public class ErrorHandlerController {

  @RequestMapping
  public String errorHandler(HttpServletRequest request) {
    LogUtils.logPageError(request);
    return "error/error";
  }

}
