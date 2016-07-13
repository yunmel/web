package com.yunmel.frame.sys.model;

import java.util.*;
import javax.persistence.*;

import com.yunmel.commons.model.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
* @author yunmel
*/
@SuppressWarnings({ "unused"})
@Table(name="sys_config")
public class SysConfig extends BaseEntity{
	 
	private String label;    //  
	private String value;    //  
	private String delFlag;    //  
	private String createBy;    //创建者  
	private Long createDate;    //  
	private String updateBy;    //更新者  
	private Long updateDate;    //  
	
	
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
	public String getDelFlag(){
		return this.getString("delFlag");
	}
	
	public void setDelFlag(String delFlag){
		this.set("delFlag",delFlag);
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


}
