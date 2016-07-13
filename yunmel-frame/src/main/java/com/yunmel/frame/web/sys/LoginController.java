package com.yunmel.frame.web.sys;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunmel.commons.controller.BaseController;
import com.yunmel.commons.utils.EncryptUtils;
import com.yunmel.commons.utils.StrUtils;
import com.yunmel.frame.common.utils.SysUserUtils;
import com.yunmel.frame.sys.model.SysUser;
import com.yunmel.frame.sys.service.SysConfigService;

@Controller
public class LoginController extends BaseController {
  private Logger logger = LoggerFactory.getLogger(LoginController.class);
  @Resource
  private SysConfigService sysConfigService;

  /**
   * 管理主页
   * 
   * @param model
   * @param request
   * @return
   */
  @RequestMapping(value = "/")
  public String index(Model model, HttpServletRequest request) {
    return index(model, request, "all");
  }

  @RequestMapping(value = "index")
  private String index(Model model, HttpServletRequest request, String moduleName) {
    request.getSession().removeAttribute("code"); // 清除code
    SysUser user = SysUserUtils.getCacheLoginUser();
    if (user == null) {
      return "redirect:/login";
    }
    model.addAttribute("menuList", SysUserUtils.getUserResources());
    model.addAttribute("onlineCount", SysUserUtils.getOnlineUser().size());
    return "index";
  }

  /**
   * GET 登录
   * 
   * @return {String}
   */
  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String login(HttpServletRequest request, HttpServletResponse response) {
    logger.info("GET请求登录");
    if (SecurityUtils.getSubject().isAuthenticated()) {
      return "redirect:/";
    }

    if (request.getHeader("x-requested-with") != null
        && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest"))// 如果是ajax请求响应头会有，x-requested-with；
    {
      response.setHeader("sessionstatus", "timeout");// 在响应头设置session状态
      return null;
    }

    return "login";
  }

  /**
   * POST 登录 shiro 写法
   *
   * @param username 用户名
   * @param password 密码
   * @return
   */
  @RequestMapping(value = "login", method = RequestMethod.POST)
  public @ResponseBody Map<String, Object> loginPost(String username, String returnUrl,
      String password, String code) {
    Map<String, Object> msg = new HashMap<String, Object>();
    String secPwd = EncryptUtils.SHA1_HEX(EncryptUtils.SHA1_HEX(password));// 加密后的密码
    Subject user = SecurityUtils.getSubject();
    UsernamePasswordToken token = new UsernamePasswordToken(username, secPwd.toCharArray());
    token.setRememberMe(true);
    try {
      user.login(token);
      if (StrUtils.isNotBlank(returnUrl)) {
        msg.put("url", returnUrl);
      }
    } catch (UnknownAccountException e) {
      logger.error("账号不存在：{}", e);
    }
    return msg;
  }

  /**
   * 未授权
   * 
   * @return {String}
   */
  @RequestMapping(value = "/unauth")
  public String unauth() {
    if (SecurityUtils.getSubject().isAuthenticated() == false) {
      return "redirect:/login";
    }
    return "unauth";
  }

  /**
   * 退出
   * 
   * @return {Result}
   */
  @RequestMapping(value = "/logout")
  public String logout() {
    logger.info("登出");
    Subject subject = SecurityUtils.getSubject();
    subject.logout();
    return "redirect:/login";
  }
}
