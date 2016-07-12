package com.yunmel.commons.model;

public class HttpResult {
  private Integer code;
  private String content;

  public HttpResult() {}

  public HttpResult(Integer code, String content) {
    this.code = code;
    this.content = content;
  }

  public Integer getCode() {
    return this.code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
