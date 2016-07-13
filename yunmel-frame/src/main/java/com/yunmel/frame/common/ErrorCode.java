package com.yunmel.frame.common;

import com.yunmel.frame.sys.exception.ResultCode;

public enum ErrorCode {
  OK(0,"成功"),
  ERROR(-1,"失败"),
  PARAMS_HAS_NULL(-2,"参数有空值"),
  UNAUTHORIZED(-3,"用户校验失败(没有传递用户名密码或者用户名密码错误)"),
  API_ERROR(-4,"接口调用失败。"),
  
  
  /******* 用户相关错误代码 从-1000~-1100 *****************/
  USERNAME_EXIST(-1001,"用户名已经存在"),
  USER_NOT_EXIST(-1002,"用户不存在"),
  USER_PASSWORD_ERROR(-1003,"用户名密码不匹配"),
  
  GM(-1000000,"失败");
  private Integer code = 0;
  private String desc = "";

  ErrorCode(Integer code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public static ResultCode get(Integer code) {
    for (ResultCode c : ResultCode.values()) {
      if (c.getCode().intValue() == code.intValue()) {
        return c;
      }
    }
    return null;
  }

  public Integer getCode() {
    return code;
  }

  public String getDesc() {
    return desc;
  }

}
