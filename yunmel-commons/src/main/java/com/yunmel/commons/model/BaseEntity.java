package com.yunmel.commons.model;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.alibaba.fastjson.JSONObject;

@Entity
public class BaseEntity extends JSONObject {
  private static final long serialVersionUID = 1L;
  @Id
  private String id; // id <主键id>

  @Transient
  private String userDataScope; // 用户的数据范围

  public BaseEntity() {
    super();
  }

  public String getId() {
    return this.getString("id");
  }

  public void setId(String id) {
    this.set("id", id);
  }

  public String getUserDataScope() {
    return this.getString("userDataScope");
  }

  public void setUserDataScope(String userDataScope) {
    this.set("userDataScope", userDataScope);
  }

  public BaseEntity(Map<String, Object> map) {
    super(map);
  }

  public BaseEntity getEntity(String key) {
    Object value = this.get(key);
    if (value instanceof BaseEntity) {
      return (BaseEntity) value;
    }

    JSONObject jobj = null;

    if (value instanceof JSONObject) {
      jobj = (JSONObject) value;
    } else {
      jobj = (JSONObject) toJSON(value);
    }

    return jobj == null ? null : new BaseEntity(jobj);
  }

  public BaseEntity set(String key, Object value, boolean force) {
    if (force || value != null) {
      super.put(key, value);
    }
    return this;
  }

  public BaseEntity set(String key, Object value) {
    return this.set(key, value, true);
  }

  public BaseEntity setAll(Map<? extends String, ? extends Object> m) {
    super.putAll(m);
    return this;
  }

  public static BaseEntity err(int errCode) {
    return new BaseEntity().set("errCode", errCode);
  }

  public static BaseEntity err(int errCode, String errMsg) {
    return new BaseEntity().set("errCode", errCode).set("errMsg", errMsg);
  }
}
