package com.yunmel.frame.sys.model;

import java.util.*;
import javax.persistence.*;

import com.yunmel.commons.model.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author yunmel
 */
@SuppressWarnings({"unused"})
@Table(name = "sys_role")
public class SysRole extends BaseEntity {
  
  private String officeId; //
  private String name; // 角色名称
  private String dataScope; // 数据范围
  private String createBy; // 创建者
  private Long createDate; //
  private String updateBy; // 更新者
  private Long updateDate; //
  private String remarks; // 备注信息
  private String delFlag; // 删除标记
  private String level; // 角色等级

  @Transient
  private String[] resourceIds; // 持有的资源
  @Transient
  private String[] officeIds; // 机构
  @Transient
  private String[] userIds; // 绑定的用户

  public String[] getUserIds() {
    return (String[]) this.get("userIds");
  }

  public void setUserIds(String[] userIds) {
    this.set("userIds", userIds);
  }

  public String[] getOfficeIds() {
    return (String[]) this.get("officeIds");
  }

  public void setOfficeIds(String[] officeIds) {
    this.set("officeIds", officeIds);
  }

  public String[] getResourceIds() {
    return (String[]) this.get("resourceIds");
  }

  public void setResourceIds(String[] resourceIds) {
    this.set("resourceIds", resourceIds);
  }

  public String getOfficeId() {
    return this.getString("officeId");
  }

  public void setOfficeId(String officeId) {
    this.set("officeId", officeId);
  }

  public String getName() {
    return this.getString("name");
  }

  public void setName(String name) {
    this.set("name", name);
  }

  public String getDataScope() {
    return this.getString("dataScope");
  }

  public void setDataScope(String dataScope) {
    this.set("dataScope", dataScope);
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

  public String getLevel() {
    return this.getString("level");
  }

  public void setLevel(String level) {
    this.set("level", level);
  }


}
