package com.yunmel.frame.sys.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yunmel.commons.service.BaseService;
import com.yunmel.commons.service.RedisService;
import com.yunmel.frame.consts.RedisKey;
import com.yunmel.frame.consts.SysConfigKey;
import com.yunmel.frame.sys.mapper.SysConfigMapper;
import com.yunmel.frame.sys.model.SysConfig;
import com.yunmel.frame.sys.vo.LoginConfig;

@Service("sysConfigService")
public class SysConfigService extends BaseService<SysConfig>{
	private final static Logger LOG = LoggerFactory.getLogger(SysConfigService.class);
	
	@Resource
	private SysConfigMapper sysConfigMapper;
	
	@Resource
	private RedisService redisService;


	private int saveConfig(String label,String value) {
		int count = 0;
		SysConfig sc = new SysConfig();
		sc.setLabel(label);
		List<SysConfig> scs = this.select(sc);
		if(scs.size() == 0){
			sc.setValue(value);
			count = this.insertSelective(sc);
		}else{
			sc.setValue(value);
			sc.setId(scs.get(0).getId());
			count = this.updateByPrimaryKeySelective(sc);
		}
		return count;
	}

	
	/**
	 * 从redis中获取，如果不存在读取数据库，并将值放到redis
	 * @param key
	 * @return
	 */
	public String findByKeyFromRedis(String key){
		String json = redisService.get(RedisKey.getConfig(key));
		if(json != null){
			return json;
		}
		String value = findByKey(key);
		if(StringUtils.isNotBlank(value)){
			redisService.set(RedisKey.getConfig(key), value);
		}
		return value;
	}
	
	
	public String findByKey(String key){
		SysConfig sysConfig = new SysConfig();
		sysConfig.set("label", key);
		List<SysConfig> configs = this.select(sysConfig);
		if(configs.size() > 0){
			return configs.get(0).getValue();
		}
		return null;
	}
	

	//保存登录配置
	public Integer saveLoginConfig(LoginConfig config) {
		int count = 0;
		count += saveConfig(SysConfigKey.LOGIN_CAPTCHA_ON,config.getLoginCaptchaOn());
		count += saveConfig(SysConfigKey.LOGIN_ERROR_COUNT,config.getLoginErrorCount());
		count += saveConfig(SysConfigKey.LOGIN_UNLOCK_TIME,config.getLoginUnlockTime());
		count += saveConfig(SysConfigKey.PWD_FIRST_LOGIN_MOD,config.getPwdFristLoginMod());
		count += saveConfig(SysConfigKey.PWD_NEXT_MOD_TIME,config.getPwdNextModTime());
		count += saveConfig(SysConfigKey.PWD_STRONG_VERIFCATION,config.getPwdStrongVerifcation());
		putRedis();
		return count;
	}

	
	//上传附件配置 
	public Integer saveAttachConfig(String path, String size) {
		int count = 0;
		count += saveConfig(SysConfigKey.ATTACH_SIZE_LIMIT,size);
		count += saveConfig(SysConfigKey.FILE_SAVE_ROOT_PATH,path);
		putRedis();
		return count;
	}


	//组织名称配置
	public Integer saveOrg(String orgName, String loginShowName,String indexShowName) {
		int count = 0;
		count += saveConfig(SysConfigKey.ORG_NAME,orgName);
		count += saveConfig(SysConfigKey.LOGIN_SHOW_NAME,loginShowName);
		count += saveConfig(SysConfigKey.INDEX_SHOW_NAME,indexShowName);
		putRedis();
		return count;
	}
	
	public void putRedis(){
		try{
			List<SysConfig> configs = select(new SysConfig());
			for (SysConfig config : configs) {
				redisService.set(RedisKey.getConfig(config.getLabel()), config.getValue());
			}
		}catch (Exception e) {
			LOG.error("redis 放入系统配置数据出错",e);
		}
	}
	
}
