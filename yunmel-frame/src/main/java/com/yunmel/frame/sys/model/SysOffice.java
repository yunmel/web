package com.yunmel.frame.sys.model;

import java.util.*;
import javax.persistence.*;

import com.yunmel.commons.model.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author yunmel
 */
@SuppressWarnings({"unused"})
@Table(name = "sys_office")
public class SysOffice extends BaseEntity {

  private String parentId; //
  private String parentIds; // 所有父级编号
  private String areaId; //
  private String code; // 区域编码
  private String name; // 机构名称
  private String type; //
  private String grade; // 机构等级
  private String address; // 联系地址
  private String zipCode; // 邮政编码
  private String master; // 负责人
  private String phone; // 电话
  private String fax; // 传真
  private String email; // 邮箱
  private String createBy; // 创建者
  private String updateBy; // 更新者
  private String remarks; // 备注信息
  private String delFlag; // 删除标记
  private String icon; //
  private Long createDate; //
  private Long updateDate; //

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

  public String getAreaId() {
    return this.getString("areaId");
  }

  public void setAreaId(String areaId) {
    this.set("areaId", areaId);
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

  public String getType() {
    return this.getString("type");
  }

  public void setType(String type) {
    this.set("type", type);
  }

  public String getGrade() {
    return this.getString("grade");
  }

  public void setGrade(String grade) {
    this.set("grade", grade);
  }

  public String getAddress() {
    return this.getString("address");
  }

  public void setAddress(String address) {
    this.set("address", address);
  }

  public String getZipCode() {
    return this.getString("zipCode");
  }

  public void setZipCode(String zipCode) {
    this.set("zipCode", zipCode);
  }

  public String getMaster() {
    return this.getString("master");
  }

  public void setMaster(String master) {
    this.set("master", master);
  }

  public String getPhone() {
    return this.getString("phone");
  }

  public void setPhone(String phone) {
    this.set("phone", phone);
  }

  public String getFax() {
    return this.getString("fax");
  }

  public void setFax(String fax) {
    this.set("fax", fax);
  }

  public String getEmail() {
    return this.getString("email");
  }

  public void setEmail(String email) {
    this.set("email", email);
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

  public String getIcon() {
    return this.getString("icon");
  }

  public void setIcon(String icon) {
    this.set("icon", icon);
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


}
