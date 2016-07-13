package com.yunmel.frame.sys.model;

import java.util.*;
import javax.persistence.*;

import com.yunmel.commons.model.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
* @author yunmel
*/
@SuppressWarnings({ "unused"})
@Table(name="sys_dict")
public class SysDict extends BaseEntity{
 
	private String label;    //标签名  
	private String value;    //数据值  
	private String type;    //类型  
	private String description;    //描述  
	private Integer sort;    //排序（升序）  
	private String createBy;    //创建者  
	private Long createDate;    //  
	private String updateBy;    //更新者  
	private Long updateDate;    //  
	private String remarks;    //备注信息  
	private String delFlag;    //删除标记  
	private String status;    //  
	
	
	public String getLabel(){
		return this.getString("label");
	}
	
	public void setLabel(String label){
		this.set("label",label);
	}
	public String getValue(){
		return this.getString("value");
	}
	
	public void setValue(String value){
		this.set("value",value);
	}
	public String getType(){
		return this.getString("type");
	}
	
	public void setType(String type){
		this.set("type",type);
	}
	public String getDescription(){
		return this.getString("description");
	}
	
	public void setDescription(String description){
		this.set("description",description);
	}
	public Integer getSort(){
		return this.getInteger("sort");
	}
	
	public void setSort(Integer sort){
		this.set("sort",sort);
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
	public String getRemarks(){
		return this.getString("remarks");
	}
	
	public void setRemarks(String remarks){
		this.set("remarks",remarks);
	}
	public String getDelFlag(){
		return this.getString("delFlag");
	}
	
	public void setDelFlag(String delFlag){
		this.set("delFlag",delFlag);
	}
	public String getStatus(){
		return this.getString("status");
	}
	
	public void setStatus(String status){
		this.set("status",status);
	}


}
