package com.yunmel.frame.sys.model;

import java.util.*;
import javax.persistence.*;

import com.yunmel.commons.model.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
* @author yunmel
*/
@SuppressWarnings({ "unused"})
@Table(name="sys_unit_type")
public class UnitType extends BaseEntity{
	
	private String name;    //类型名称  
	private String remarks;    //备注信息  
	private String createBy;    //创建者  
	private Long createDate;    //  
	private String updateBy;    //更新者  
	private Long updateDate;    //  
	private String delFlag;    //删除标记  
	
	
	public String getName(){
		return this.getString("name");
	}
	
	public void setName(String name){
		this.set("name",name);
	}
	public String getRemarks(){
		return this.getString("remarks");
	}
	
	public void setRemarks(String remarks){
		this.set("remarks",remarks);
	}
	public String getCreateBy(){
		return this.getString("createBy");
	}
	
	public void setCreateBy(String createBy){
		this.set("createBy",createBy);
	}
	public Long getCreateDate(){
		return this.getLong("createDate");
	}
	
	public void setCreateDate(Long createDate){
		this.set("createDate",createDate);
	}
	public String getUpdateBy(){
		return this.getString("updateBy");
	}
	
	public void setUpdateBy(String updateBy){
		this.set("updateBy",updateBy);
	}
	public Long getUpdateDate(){
		return this.getLong("updateDate");
	}
	
	public void setUpdateDate(Long updateDate){
		this.set("updateDate",updateDate);
	}
	public String getDelFlag(){
		return this.getString("delFlag");
	}
	
	public void setDelFlag(String delFlag){
		this.set("delFlag",delFlag);
	}


}
