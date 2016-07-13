package com.yunmel.frame.sys.model;

import java.util.*;
import javax.persistence.*;

import com.yunmel.commons.model.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
* @author yunmel
*/
@SuppressWarnings({ "unused"})
@Table(name="sys_log")
public class SysLog extends BaseEntity{

	private String type;    //日志类型  
	private String createBy;    //创建者  
	private Long createDate;    //  
	private String remoteAddr;    //操作IP地址  
	private String userAgent;    //用户代理  
	private String requestUri;    //请求URI  
	private String method;    //操作方式  
	private String params;    //操作提交的数据  
	private String exception;    //异常信息  
	private String description;    //描述  
	
	
	public String getType(){
		return this.getString("type");
	}
	
	public void setType(String type){
		this.set("type",type);
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
	public String getRemoteAddr(){
		return this.getString("remoteAddr");
	}
	
	public void setRemoteAddr(String remoteAddr){
		this.set("remoteAddr",remoteAddr);
	}
	public String getUserAgent(){
		return this.getString("userAgent");
	}
	
	public void setUserAgent(String userAgent){
		this.set("userAgent",userAgent);
	}
	public String getRequestUri(){
		return this.getString("requestUri");
	}
	
	public void setRequestUri(String requestUri){
		this.set("requestUri",requestUri);
	}
	public String getMethod(){
		return this.getString("method");
	}
	
	public void setMethod(String method){
		this.set("method",method);
	}
	public String getParams(){
		return this.getString("params");
	}
	
	public void setParams(String params){
		this.set("params",params);
	}
	public String getException(){
		return this.getString("exception");
	}
	
	public void setException(String exception){
		this.set("exception",exception);
	}
	public String getDescription(){
		return this.getString("description");
	}
	
	public void setDescription(String description){
		this.set("description",description);
	}


}
