package backstageManager.controllers;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import backstageManager.beans.Manager;
import backstageManager.controllers.returnObject.DefaultReturn;
import backstageManager.dao.ManagerDao;

@Controller
@SessionAttributes
public class EditManager {
	@RequestMapping(value = "/editManager", method = RequestMethod.GET)
	public ModelAndView showEditManagerView(HttpSession session) {
		String usertype = (String) session.getAttribute("usertype");
		if (!"superuser".equalsIgnoreCase(usertype)) {
			return new ModelAndView("errors/403", "resolveView",
					"/superuserLogin.html");
		} else {
			try {
				List<Manager> managers = ManagerDao.getAllManagers();
				return new ModelAndView("superuser/editManager", "managers",
						managers);
			} catch (SQLException e) {
				e.printStackTrace();
				return new ModelAndView("errors/500", "errorMessage",
						e.toString());
			}
		}
	}

	@RequestMapping(value = "/addManager", method = RequestMethod.POST)
	public ModelAndView addManager(HttpSession session,
			@RequestParam("username") String username,
			@RequestParam("passwd") String passwd,
			@RequestParam("isActive") String isActive) {
		if (null == username || null == passwd || null == isActive
				|| "".equalsIgnoreCase(username) || "".equalsIgnoreCase(passwd)
				|| "".equalsIgnoreCase(isActive)) {
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, "参数错误， 请正确操作").toJsonStr());
		}
		String usertype = (String) session.getAttribute("usertype");
		if (!"superuser".equalsIgnoreCase(usertype)) {
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, "403;该页面禁止非超级管理员操作").toJsonStr());
		}
		try {
			ManagerDao.addManager(new Manager(-1, username, passwd, "true"
					.equalsIgnoreCase(isActive)));
			return new ModelAndView("empty", "message", new DefaultReturn(true,
					"").toJsonStr());
		} catch (SQLException e) {
			e.printStackTrace();
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, e.toString()).toJsonStr());
		}
	}

	@RequestMapping(value = "/updateManager", method = RequestMethod.POST)
	public ModelAndView updateManager(HttpSession session,
			@RequestParam("id") int id,
			@RequestParam("username") String username,
			@RequestParam("passwd") String passwd,
			@RequestParam("isActive") String isActive) {
		if (0 == id || null == username || null == passwd || null == isActive
				|| "".equalsIgnoreCase(username) || "".equalsIgnoreCase(passwd)
				|| "".equalsIgnoreCase(isActive)) {
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, "参数错误， 请正确操作").toJsonStr());
		}
		String usertype = (String) session.getAttribute("usertype");
		if (!"superuser".equalsIgnoreCase(usertype)) {
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, "403;该页面禁止非超级管理员操作").toJsonStr());
		}
		try {
			ManagerDao.updateManager(new Manager(id, username, passwd, "true"
					.equalsIgnoreCase(isActive)));
			return new ModelAndView("empty", "message", new DefaultReturn(true,
					"").toJsonStr());
		} catch (SQLException e) {
			e.printStackTrace();
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, e.toString()).toJsonStr());
		}
	}

	@RequestMapping(value = "/deleteManager", method = RequestMethod.POST)
	public ModelAndView updateManager(HttpSession session,
			@RequestParam("id") int id) {
		if (0 == id) {
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, "参数错误， 请正确操作").toJsonStr());
		}
		String usertype = (String) session.getAttribute("usertype");
		if (!"superuser".equalsIgnoreCase(usertype)) {
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, "403;该页面禁止非超级管理员操作").toJsonStr());
		}
		try {
			ManagerDao.deleteManagerById(id);
			return new ModelAndView("empty", "message", new DefaultReturn(true,
					"").toJsonStr());
		} catch (SQLException e) {
			e.printStackTrace();
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, e.toString()).toJsonStr());
		}

	}

}
