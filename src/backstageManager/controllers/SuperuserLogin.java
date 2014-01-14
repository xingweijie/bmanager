package backstageManager.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import backstageManager.controllers.processor.Processor;
import backstageManager.controllers.processor.SuperuserLoginIdentifyProcessor;
import backstageManager.controllers.returnObject.ReturnObject;

@Controller
@SessionAttributes
public class SuperuserLogin {
	@RequestMapping(value = "/superuserLogin", method = RequestMethod.GET)
	public ModelAndView showLoginViewForSuperuser() {
		return new ModelAndView("superuser/login");
	}

	@RequestMapping(value = "/superuserLoginIdentify", method = RequestMethod.POST)
	@ResponseBody
	public String loginSuperuser(HttpSession session,
			@RequestParam("username") String username,
			@RequestParam("passwd") String passwd) {
		Processor processor = new SuperuserLoginIdentifyProcessor(username,
				passwd, session);
		ReturnObject returnObject = processor.processForPost();
		return returnObject.toJsonStr();
	}

	@RequestMapping(value = "/superuserMenu", method = RequestMethod.GET)
	public ModelAndView showMenuForSuperuser(HttpSession session) {
		String usertype = (String) session.getAttribute("usertype");
		if (!"superuser".equalsIgnoreCase(usertype)) {
			return new ModelAndView("errors/403", "resolveView",
					"/superuserLogin.html");
		}
		return new ModelAndView("superuser/menu");
	}

	@RequestMapping("/superuserLogout")
	public ModelAndView logout(HttpSession session, HttpServletRequest request) {
		session.removeAttribute("usertype");
		session.removeAttribute("username");
		return new ModelAndView("redirectTo", "url", request.getContextPath());
	}
}
