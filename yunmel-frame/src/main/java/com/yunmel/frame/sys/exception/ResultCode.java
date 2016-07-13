package com.yunmel.frame.sys.exception;



/**
 * 状态码
 * @author taosq
 *
 */
public enum ResultCode {
	
	/**
	 * 常用的状态码
	 */
	OK(1000,"请求成功"),
	ERROR(1001,"请求失败"),
	TOKEN_ERROR(1004,"认证过期"),
	PLATFORM_ERROR(1006,"平台错误"),
	ILLEGAL_OPERATION(1007,"非法操作"),
	REFUSE_REQUEST(1008,"拒绝访问"),
	
	SERVER_ERROR(1901,"服务器错误"),
	PARAMS_ERROR(1902,"参数错误"),
	
	
	//用户相关的状态码
	ACCOUNT_NOT_FOUND(2000,"用户不存在"),
	ACCOUNT_EXCEPTION(2001,"用户异常"),
	ACCOUNT_NOT_NEW(2003,"不是新用户"),
	ACCOUNT_NOT_THE_SAME(2004,"用户不相同"),
	ACCOUNT_PASSWORD_ERRO(2005,"密码错误"),
	REGISTER_PARAMS_ERROR(2006,"用户注册参数错误"),
	REGISTER_ROLEORDEPART_ERROR(2007,"用户角色错误"),
	REGISTER_USERNAME_EXIST(2008,"用户名已经存在"),
	
	
	//上传相关
	TOWER_NOT_FOUND(3000,"杆塔不存在") ,
	SECTION_NOT_FOUND(4000,"标段不存在") ,
	DISLINE_NOT_FOUND(5000,"放线段不存在") ,
	PROCESS_NOT_FOUND(6000,"分类不存在") ,
	PROCESS_ERROR(6001,"分类错误") ,
	
	
	
	PIC_NOT_FOUND(8000,"图片不存在"),
	PIC_STATUS_PASS_ERROR(8001,"文件已通过审批"),
	
	FILE_NOT_FOUND(7000,"文件不存在"),
	FILE_LENGTH_ERROR(7001,"文件长度错误"),
	FILE_UPLOAD_ERROR(7002,"文件上传错误");
	
	
	
	private Integer code = 0;
	private String desc = "";
	
	ResultCode(Integer code,String desc){
		this.code = code;
		this.desc = desc;
	}
	
	public static ResultCode get(Integer code){
		for(ResultCode c: ResultCode.values()){
			if(c.getCode().intValue() == code.intValue()){
				return c;
			}
		}
		return null;
	}
	
	public Integer getCode(){
		return code;
	}
	
	public String getDesc() {
		return desc;
	}
	
     
}
