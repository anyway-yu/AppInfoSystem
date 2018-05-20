package cn.appsys.controller.backend;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import cn.appsys.pojo.BackendUser;
import cn.appsys.service.backend.BackendUserService;
import cn.appsys.tools.Constants;

@Controller
@RequestMapping("/manager")
public class UserLoginController {
	@Resource
	BackendUserService backendUserService;

	@RequestMapping("/login")
	public String login() {
		return "backendlogin";
	}

	/**
	 * 后台登陆
	 * @param userCode
	 * @param userPassword
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/dologin")
	public String dologin(@RequestParam String userCode,@RequestParam String userPassword,HttpServletRequest request,HttpSession session) {
		BackendUser backendUser = null;
		try {
			backendUser = backendUserService.login(userCode, userPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//用户登陆
		if(backendUser != null) {
			session.setAttribute(Constants.USER_SESSION, backendUser);
			return "redirect:/manager/backend/main";
		}else {
			request.setAttribute("error", "用户名或密码不正确");
			return "backendlogin";
		}
	}

	/**
	 * session为空就重新登陆
	 * @param session
	 * @return
	 */
	@RequestMapping("/backend/main")
	public String main(HttpSession session) {
		if(session.getAttribute(Constants.USER_SESSION) == null) {
			return "redirect:/manager/login";
		}
		return "backend/main";
	}

	/**
	 * 后台退出
	 * @param session
	 * @return
	 */
	@RequestMapping("/logout")
	public String lonout(HttpSession session) {
		session.removeAttribute(Constants.USER_SESSION);
		return "backendlogin";
	}

}
