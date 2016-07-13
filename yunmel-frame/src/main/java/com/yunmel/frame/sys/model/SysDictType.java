package com.yunmel.frame.sys.model;

import java.util.*;
import javax.persistence.*;

import com.yunmel.commons.model.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author yunmel
 */
@SuppressWarnings({"unused"})
@Table(name = "sys_dict_type")
public class SysDictType extends BaseEntity {

  private String parentId; //
  private String parentIds; // 所有父级编号
  private String code; // 区域编码
  private String name; // 区域名称
  private String createBy; // 创建者
  private Long createDate; //
  private String updateBy; // 更新者
  private Long updateDate; //
  private String remarks; // 备注信息
  private String delFlag; // 删除标记(0活null 正常 1,删除)
  private Integer sort; //
  private String status; //

  @Transient
  private String oldParentIds; // 旧的pids,非表中字段，用作更新用
  @Transient
  private String oldName;// 用做添加时对比

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


  public String getParentId() {
    return this.getString("parentId");
  }

  public void setParentId(String parentId) {
    this.set("parentId", parentId);
  }

  public String getParentIds() {
    return this.getString("parentIds");
  }

  public void setParentIds(String parentIds) {
    this.set("parentIds", parentIds);
  }

  public String getCode() {
    return this.getString("code");
  }

  public void setCode(String code) {
    this.set("code", code);
  }

  public String getName() {
    return this.getString("name");
  }

  public void setName(String name) {
    this.set("name", name);
  }

  public String getCreateBy() {
    return this.getString("createBy");
  }

  public void setCreateBy(String createBy) {
    this.set("createBy", createBy);
  }

  public Long getCreateDate() {
    return this.getLong("createDate");
  }

  public void setCreateDate(Long createDate) {
    this.set("createDate", createDate);
  }

  public String getUpdateBy() {
    return this.getString("updateBy");
  }

  public void setUpdateBy(String updateBy) {
    this.set("updateBy", updateBy);
  }

  public Long getUpdateDate() {
    return this.getLong("updateDate");
  }

  public void setUpdateDate(Long updateDate) {
    this.set("updateDate", updateDate);
  }

  public String getRemarks() {
    return this.getString("remarks");
  }

  public void setRemarks(String remarks) {
    this.set("remarks", remarks);
  }

  public String getDelFlag() {
    return this.getString("delFlag");
  }

  public void setDelFlag(String delFlag) {
    this.set("delFlag", delFlag);
  }

  public Integer getSort() {
    return this.getInteger("sort");
  }

  public void setSort(Integer sort) {
    this.set("sort", sort);
  }

  public String getStatus() {
    return this.getString("status");
  }

  public void setStatus(String status) {
    this.set("status", status);
  }


}
