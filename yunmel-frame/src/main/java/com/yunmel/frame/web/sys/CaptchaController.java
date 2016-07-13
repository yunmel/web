package com.yunmel.frame.web.sys;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yunmel.frame.common.utils.ValidateCode;


@Controller
public class CaptchaController {
	
	private static final Logger logger = LoggerFactory.getLogger(CaptchaController.class);

	@RequestMapping("sys/captcha")
	public void codeImage(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "120") int width,
			@RequestParam(defaultValue = "40") int height){
		try {
			// 设置响应的类型格式为图片格式
			response.setContentType("image/jpeg");
			// 禁止图像缓存。
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);

			HttpSession session = request.getSession();

			ValidateCode vCode = new ValidateCode(width, height, 4, 50);
			session.setAttribute("code", vCode.getCode());
			vCode.write(response.getOutputStream());
		} catch (IOException e) {
			logger.error("系统登录验证码生成时发生错误：", e);
		}
	}

}
