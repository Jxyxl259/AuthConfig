package com.yaic.auth.interior.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yaic.auth.thirdparty.controller.AccountController;

@Controller
@RequestMapping("/")
public class APPLoginController {
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@RequestMapping(value="home")
	public String denglu1() throws Exception{
		return "redirect:/index.html";
	}
	
    
    /**
     * 无权限页面跳转
     */
	@RequestMapping(value = {"/403"})
    public String unauthorized() throws Exception {
        logger.info("enter unauthorized page.");
        return "403";
    }
    
	@RequestMapping(value = "/login")
    public String login(HttpServletRequest request, Model model) throws Exception {
		//shiroLoginFailure:就是shiro异常类的全类名.
        String exceptionClassName = (String)request.getAttribute("shiroLoginFailure");
        String error = null;
        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(LockedAccountException.class.getName().equals(exceptionClassName)){
        	error = "账户被锁定，请联系管理员解锁" ;
        }else if(ExcessiveAttemptsException.class.getName().equals(exceptionClassName)) {
            error = "登陆错误次数已超过5次,账户被锁定" ;
        }
        if(request.getParameter("forceLogout") != null) {
            model.addAttribute("error", "您已经被管理员强制退出，请重新登录");
        }
        
        logger.error(error);
        model.addAttribute("userCode", request.getParameter("username"));
        model.addAttribute("error", error);
        
        String htmlUrl = "login";
        String userName = request.getParameter("username");
        if(StringUtils.isNotBlank(userName)){
        	Session session = SecurityUtils.getSubject().getSession();
        	if(session != null){
        		htmlUrl = "redirect:/index.html";
        	}
        }
        	
        return htmlUrl;
        
    }
	
	/**
	 * 切换账户
	 */
//	@RequestMapping(value="changeLoginUser")
//	public String changeLoginUser(){
//		
//		Session session = SecurityUtils.getSubject().getSession();
//		String loginUserCode = (String) session.getAttribute(Constants.LOGIN_USER_CODE_KEY);
//		
//		session.removeAttribute(Constants.LOGIN_USER_CODE_KEY);
//		
//		String loginUserCode1 = (String) session.getAttribute(Constants.LOGIN_USER_CODE_KEY);
//		
//		return "login";
//	}
	
	
	
}
