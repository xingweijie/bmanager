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
import backstageManager.controllers.processor.ManagerLoginIdentifyProcessor;
import backstageManager.controllers.returnObject.ReturnObject;

@Controller
@SessionAttributes
public class ManagerLogin {
	@RequestMapping(value = "/managerLogin", method = RequestMethod.GET)
	public ModelAndView showLoginViewForManager() {
		return new ModelAndView("manager/login");
	}

	@RequestMapping(value = "/managerLoginIdentify", method = RequestMethod.POST)
	@ResponseBody
	public String loginManager(HttpSession session,
			@RequestParam("username") String username,
			@RequestParam("passwd") String passwd) {
		Processor processor = new ManagerLoginIdentifyProcessor(username,
				passwd, session);
		ReturnObject returnObject = processor.processForPost();
		return returnObject.toJsonStr();
	}
	
	@RequestMapping(value = "/managerMenu", method = RequestMethod.GET)
	public ModelAndView showMenuForManager(HttpSession session) {
		String usertype = (String) session.getAttribute("usertype");
		if (!"manager".equalsIgnoreCase(usertype)) {
			return new ModelAndView("errors/403", "resolveView",
					"/managerLogin.html");
		}
		return new ModelAndView("manager/menu");
	}
	
	@RequestMapping("/managerLogout")
	public ModelAndView logout(HttpSession session, HttpServletRequest request) {
		session.removeAttribute("usertype");
		session.removeAttribute("username");
		return new ModelAndView("redirectTo", "url", request.getContextPath());
	}
}
