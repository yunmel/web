package com.yunmel.commons.model;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Result {
  private static final ObjectMapper MAPPER = new ObjectMapper();
  private Integer status;
  private String msg;
  private Object data;

  public static Result build(Integer status, String msg, Object data) {
    return new Result(status, msg, data);
  }

  public static Result ok(Object data) {
    return new Result(data);
  }

  public static Result ok() {
    return new Result(null);
  }

  public Result() {}

  public static Result build(Integer status, String msg) {
    return new Result(status, msg, null);
  }

  public Result(Integer status, String msg, Object data) {
    this.status = status;
    this.msg = msg;
    this.data = data;
  }

  public Result(Object data) {
    this.status = Integer.valueOf(200);
    this.msg = "OK";
    this.data = data;
  }

  public Boolean isOK() {
    return Boolean.valueOf(this.status.intValue() == 200);
  }

  public Integer getStatus() {
    return this.status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getMsg() {
    return this.msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public Object getData() {
    return this.data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public static Result formatToPojo(String jsonData, Class<?> clazz) {
    try {
      if (clazz == null) {
        return (Result) MAPPER.readValue(jsonData, Result.class);
      }
      JsonNode jsonNode = MAPPER.readTree(jsonData);
      JsonNode data = jsonNode.get("data");
      Object obj = null;
      if (clazz != null) {
        if (data.isObject())
          obj = MAPPER.readValue(data.traverse(), clazz);
        else if (data.isTextual()) {
          obj = MAPPER.readValue(data.asText(), clazz);
        }
      }
      return build(Integer.valueOf(jsonNode.get("status").intValue()), jsonNode.get("msg").asText(),
          obj);
    } catch (Exception e) {
    }
    return null;
  }

  public static Result format(String json) {
    try {
      return (Result) MAPPER.readValue(json, Result.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static Result formatToList(String jsonData, Class<?> clazz) {
    try {
      JsonNode jsonNode = MAPPER.readTree(jsonData);
      JsonNode data = jsonNode.get("data");
      Object obj = null;
      if ((data.isArray()) && (data.size() > 0)) {
        obj = MAPPER.readValue(data.traverse(),
            MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
      }
      return build(Integer.valueOf(jsonNode.get("status").intValue()), jsonNode.get("msg").asText(),
          obj);
    } catch (Exception e) {
    }
    return null;
  }
}
