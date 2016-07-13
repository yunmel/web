package com.yunmel.frame.web.sys;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yunmel.commons.service.RedisService;


@Controller
@RequestMapping("sys/monitor/redis")
public class RedisMonitorController {
	
	@Resource
	private RedisService redisService;
}
