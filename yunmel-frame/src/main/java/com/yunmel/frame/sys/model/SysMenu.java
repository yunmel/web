package com.yunmel.frame.sys.model;

import java.util.*;
import javax.persistence.*;

import com.yunmel.commons.model.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author yunmel
 */
@SuppressWarnings({"unused"})
@Table(name = "sys_menu")
public class SysMenu extends BaseEntity {

  private String name; // 资源名称
  private String common; // 是否是公共资源(0.不是 1.是)
  private String icon; // 图标
  private Integer sort; // 排序号
  private String parentId; //
  private String type; // 类型(0.菜单 1.按钮)
  private String url; // 链接
  private String description; // 描述
  private String status; // 状态(0.正常 1.禁用)
  private String parentIds; // 父级集合
  private Long createDate; //
  private Long updateDate; //
  private String createBy; //
  private String updateBy; //
  private String delFlag; //
  private String permissionStr; //
  private String module; //


  @Transient
  private String oldParentIds; // 旧的pids,非表中字段，用作更新用

  @Transient
  private String oldName;// 用做添加时对比

  @Transient
  private String menuType; // 处理修改菜单的父级菜单时，会将菜单类型的单选按钮值置为空，故替换


  public String getOldName() {
    return getString("oldName");
  }

  public void setOldName(String oldName) {
    this.set("oldName", oldName);
  }

  public String getOldParentIds() {
    return this.getString("oldParentIds");
  }

  public void setOldParentIds(String oldParentIds) {
    this.set("oldParentIds", oldParentIds);
  }

  public String getName() {
    return this.getString("name");
  }

  public void setName(String name) {
    this.set("name", name);
  }

  public String getCommon() {
    return this.getString("common");
  }

  public void setCommon(String common) {
    this.set("common", common);
  }

  public String getIcon() {
    return this.getString("icon");
  }

  public void setIcon(String icon) {
    this.set("icon", icon);
  }

  public Integer getSort() {
    return this.getInteger("sort");
  }

  public void setSort(Integer sort) {
    this.set("sort", sort);
  }

  public String getParentId() {
    return this.getString("parentId");
  }

  public void setParentId(String parentId) {
    this.set("parentId", parentId);
  }

  public String getType() {
    return this.getString("type");
  }

  public void setType(String type) {
    this.set("type", type);
  }

  public String getUrl() {
    return this.getString("url");
  }

  public void setUrl(String url) {
    this.set("url", url);
  }

  public String getDescription() {
    return this.getString("description");
  }

  public void setDescription(String description) {
    this.set("description", description);
  }

  public String getStatus() {
    return this.getString("status");
  }

  public void setStatus(String status) {
    this.set("status", status);
  }

  public String getParentIds() {
    return this.getString("parentIds");
  }

  public void setParentIds(String parentIds) {
    this.set("parentIds", parentIds);
  }

  public Long getCreateDate() {
    return this.getLong("createDate");
  }

  public void setCreateDate(Long createDate) {
    this.set("createDate", createDate);
  }

  public Long getUpdateDate() {
    return this.getLong("updateDate");
  }

  public void setUpdateDate(Long updateDate) {
    this.set("updateDate", updateDate);
  }

  public String getCreateBy() {
    return this.getString("createBy");
  }

  public void setCreateBy(String createBy) {
    this.set("createBy", createBy);
  }

  public String getUpdateBy() {
    return this.getString("updateBy");
  }

  public void setUpdateBy(String updateBy) {
    this.set("updateBy", updateBy);
  }

  public String getDelFlag() {
    return this.getString("delFlag");
  }

  public void setDelFlag(String delFlag) {
    this.set("delFlag", delFlag);
  }

  public String getPermissionStr() {
    return this.getString("permissionStr");
  }

  public void setPermissionStr(String permissionStr) {
    this.set("permissionStr", permissionStr);
  }

  public String getModule() {
    return this.getString("module");
  }

  public void setModule(String module) {
    this.set("module", module);
  }


}
