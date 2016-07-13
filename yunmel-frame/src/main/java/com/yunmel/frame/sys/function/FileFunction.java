package com.yunmel.frame.sys.function;

import org.springframework.stereotype.Component;

@Component
public class FileFunction {
	
	public String formatFileSize(Long size){
		if(size == null){
			return "未知";
		}
		return "";//FileUtils.getHumanSize(size);
	}
}
