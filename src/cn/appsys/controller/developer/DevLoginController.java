package cn.appsys.controller.developer;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.DevUser;
import cn.appsys.service.developer.DevUserService;
import cn.appsys.tools.Constants;

@Controller
@RequestMapping("/devuser")
public class DevLoginController {
	/*	private Logger logger = Logger.getLogger(DevLoginController.class);*/

	@Resource
	DevUserService devUserService;

	@RequestMapping("/login")
	public String login() {
		return "devlogin";
	}
	/**
	 * 前台登陆
	 * @param devCode
	 * @param devPassword
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/dologin")
	public String dologin(@RequestParam String devCode,@RequestParam String devPassword,HttpServletRequest request,HttpSession session) {
		DevUser devUser = null;
		try {
			devUser = devUserService.login(devCode, devPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(devUser != null) {//登陆成功
			session.setAttribute(Constants.DEV_USER_SESSION, devUser);
			return "redirect:/devuser/main";
		}else {
			//页面跳转（login.jsp）带出提示信息--转发
			request.setAttribute("erroor", "用户名或密码不正确");
			return "devlogin";
		}
	}
	
	/**
	 * session为空就重新登陆
	 * @param session
	 * @return
	 */
	@RequestMapping("/main")
	public String mian(HttpSession session) {
		if(session.getAttribute(Constants.DEV_USER_SESSION) == null) {
			return "redirect:/devuser/login";
		}
		return "developer/main";
	}

	/**
	 * 退出登陆
	 * @param session
	 * @return
	 */
	@RequestMapping("/logout")
	public String loginout(HttpSession session) {
		//清除session
		session.removeAttribute(Constants.DEV_USER_SESSION);
		return "devlogin";
	}


}

