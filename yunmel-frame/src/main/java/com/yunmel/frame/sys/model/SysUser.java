package com.yunmel.frame.sys.model;

import java.util.*;

import javax.persistence.*;

import com.yunmel.commons.model.BaseEntity;
import com.yunmel.frame.consts.Constant;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author yunmel
 */
@SuppressWarnings({"unused"})
@Table(name = "sys_user")
public class SysUser extends BaseEntity {

  private String unitId; // 所在单位
  private String username; // 登录名
  private String password; // 密码
  private String name; // 姓名
  private String sex; //
  private String email; // 邮箱
  private String phone; // 电话
  private String mobile; // 手机
  private String userType; // 用户类型(0.普通 1.系统超级管理员)
  // private String userSource; // 用户来源(1教师，2学生)
  private String loginIp; // 最后登陆IP
  private Long loginDate; // 最后登陆时间
  private String createBy; // 创建者
  private Long createDate; //
  private String updateBy; // 更新者
  private Long updateDate; //
  private String remarks; // 备注信息
  private String delFlag; // 删除标记
  private Integer status; // 0-禁用，1-启用，2-待审核，3-审核未通过
  private Long lastLoginDate; //
  private String lastLoginIp; //
  private String lastLoginErrorMsg; // 上一次登录错误消息
  private Integer errorCount; // 错误次数
  private Long nextLoginDate; //
  private Long nextModPwdDate; //
  private String hxUid; // 环信UID
  private String avatar; // 头像地址
  /** 地址 */
  private String address;
  /** 出生年月 */
  private String birthDay;
  /** 婚姻状况 */
  private String maritalStatus;
  /** 紧急联系人 */
  private String otherContact;
  /** 毕业时间 */
  private String graduationDate;
  /** 家庭住址 */
  private String homeAddress;
  private String sign; // 签名


  @Transient
  private String[] roleIds; // 角色
  @Transient
  private String officeName; // 部门名称



  public void setRoleIds(String[] roleIds) {
    this.set("roleIds", roleIds);
  }

  public String[] getRoleIds() {
    return (String[]) this.get("roleIds");
  }

  public String getOfficeName() {
    return this.getString("officeName");
  }

  public void setOfficeName(String officeName) {
    this.set("officeName", officeName);
  }

  // 是否是超级管理员
  public boolean isAdmin() {
    return Constant.SUPER_ADMIN.equals(this.getUserType()) ? true : false;
  }

  public String getUnitId() {
    return this.getString("unitId");
  }

  public void setUnitId(String unitId) {
    this.set("unitId", unitId);
  }

  public String getUsername() {
    return this.getString("username");
  }

  public void setUsername(String username) {
    this.set("username", username);
  }

  public String getPassword() {
    return this.getString("password");
  }

  public void setPassword(String password) {
    this.set("password", password);
  }

  public String getName() {
    return this.getString("name");
  }

  public void setName(String name) {
    this.set("name", name);
  }

  public String getEmail() {
    return this.getString("email");
  }

  public void setEmail(String email) {
    this.set("email", email);
  }

  public String getPhone() {
    return this.getString("phone");
  }

  public void setPhone(String phone) {
    this.set("phone", phone);
  }

  public String getMobile() {
    return this.getString("mobile");
  }

  public void setMobile(String mobile) {
    this.set("mobile", mobile);
  }

  public String getUserType() {
    return this.getString("userType");
  }

  public void setUserType(String userType) {
    this.set("userType", userType);
  }

  // public String getUserSource() {
  // return this.getString("userSource");
  // }
  //
  // public void setUserSource(String userSource) {
  // this.set("userSource", userSource);
  // }

  public String getLoginIp() {
    return this.getString("loginIp");
  }

  public void setLoginIp(String loginIp) {
    this.set("loginIp", loginIp);
  }

  public Long getLoginDate() {
    return this.getLong("loginDate");
  }

  public void setLoginDate(Long loginDate) {
    this.set("loginDate", loginDate);
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

  public Integer getStatus() {
    return this.getInteger("status");
  }

  public void setStatus(Integer status) {
    this.set("status", status);
  }

  public Long getLastLoginDate() {
    return this.getLong("lastLoginDate");
  }

  public void setLastLoginDate(Long lastLoginDate) {
    this.set("lastLoginDate", lastLoginDate);
  }

  public String getLastLoginIp() {
    return this.getString("lastLoginIp");
  }

  public void setLastLoginIp(String lastLoginIp) {
    this.set("lastLoginIp", lastLoginIp);
  }

  public String getLastLoginErrorMsg() {
    return this.getString("lastLoginErrorMsg");
  }

  public void setLastLoginErrorMsg(String lastLoginErrorMsg) {
    this.set("lastLoginErrorMsg", lastLoginErrorMsg);
  }

  public Integer getErrorCount() {
    return this.getInteger("errorCount");
  }

  public void setErrorCount(Integer errorCount) {
    this.set("errorCount", errorCount);
  }

  public Long getNextLoginDate() {
    return this.getLong("nextLoginDate");
  }

  public void setNextLoginDate(Long nextLoginDate) {
    this.set("nextLoginDate", nextLoginDate);
  }

  public Long getNextModPwdDate() {
    return this.getLong("nextModPwdDate");
  }

  public void setNextModPwdDate(Long nextModPwdDate) {
    this.set("nextModPwdDate", nextModPwdDate);
  }

  public String getHxUid() {
    return this.getString("hxUid");
  }

  public void setHxUid(String hxUid) {
    this.set("hxUid", hxUid);
  }

  public String getAvatar() {
    return this.getString("avatar");
  }

  public void setAvatar(String avatar) {
    this.set("avatar", avatar);
  }

  public String getSex() {
    return this.getString("sex");
  }

  public void setSex(String sex) {
    this.set("sex", sex);
  }

  public String getAddress() {
    return this.getString("address");
  }

  public void setAddress(String address) {
    this.set("address", address);
  }

  public String getBirthDay() {
    return this.getString("birthDay");
  }

  public void setBirthDay(String birthDay) {
    this.set("birthDay", birthDay);
  }

  public String getMaritalStatus() {
    return this.getString("maritalStatus");
  }

  public void setMaritalStatus(String maritalStatus) {
    this.set("maritalStatus", maritalStatus);
  }

  public String getOtherContact() {
    return this.getString("otherContact");
  }

  public void setOtherContact(String otherContact) {
    this.set("otherContact", otherContact);
  }

  public String getGraduationDate() {
    return this.getString("graduationDate");
  }

  public void setGraduationDate(String graduationDate) {
    this.set("graduationDate", graduationDate);
  }

  public String getHomeAddress() {
    return this.getString("homeAddress");
  }

  public void setHomeAddress(String homeAddress) {
    this.set("homeAddress", homeAddress);
  }
  public String getSign() {
    return this.getString("sign");
  }

  public void setSign(String sign) {
    this.set("sign", sign);
  }

}
