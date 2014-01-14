package backstageManager.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import backstageManager.beans.Category;
import backstageManager.beans.Manager;
import backstageManager.beans.Project;
import backstageManager.controllers.returnObject.DefaultReturn;
import backstageManager.dao.AuthorityDao;
import backstageManager.dao.ManagerDao;
import backstageManager.dao.ProjectDao;
import backstageManager.dao.exception.NotFoundException;

@Controller
@SessionAttributes
public class EditAuthority {
	@RequestMapping(value = "/editAuthority", method = RequestMethod.GET)
	public ModelAndView showAuthority(HttpSession session,
			@RequestParam("managerId") int managerId) {
		if (0 == managerId) {
			return new ModelAndView("errors/500", "errorMessage", "参数错误， 请正确操作");
		}
		String usertype = (String) session.getAttribute("usertype");
		if (!"superuser".equalsIgnoreCase(usertype)) {
			return new ModelAndView("errors/403", "resolveView",
					"/superuserLogin.html");
		}
		try {
			Manager manager = ManagerDao.getManagerById(managerId);
			Map<Project, Category> authorizedMap = ProjectDao
					.getAuthorizedProjectsByManagerId(managerId);
			List<Project> authorizedProjects = new ArrayList<Project>(
					authorizedMap.keySet());
			List<Category> authorizedCategories = new ArrayList<Category>(
					authorizedMap.values());
			Map<Project, Category> notAuthorizedMap = ProjectDao
					.getNotAuthorizedProjectsByManagerId(managerId);
			List<Project> notAuthorizedProjects = new ArrayList<Project>(
					notAuthorizedMap.keySet());
			List<Category> notAuthorizedCategories = new ArrayList<Category>(
					notAuthorizedMap.values());
			ModelAndView modelAndView = new ModelAndView(
					"superuser/editAuthority");
			modelAndView.addObject("manager", manager);
			modelAndView.addObject("authorizedProjects", authorizedProjects);
			modelAndView
					.addObject("authorizedCategories", authorizedCategories);
			modelAndView.addObject("notAuthorizedProjects",
					notAuthorizedProjects);
			modelAndView.addObject("notAuthorizedCategories",
					notAuthorizedCategories);
			return modelAndView;
		} catch (SQLException e) {
			e.printStackTrace();
			return new ModelAndView("errors/500", "errorMessage", e.toString());
		} catch (NotFoundException e) {
			e.printStackTrace();
			return new ModelAndView("errors/500", "errorMessage", e.toString());
		}
	}

	@RequestMapping(value = "/addAuthority", method = RequestMethod.POST)
	public ModelAndView addAuthority(HttpSession session,
			@RequestParam("managerId") int managerId,
			@RequestParam("projectId") int projectId) {
		if (0 == managerId || 0 == projectId) {
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, "参数错误， 请正确操作").toJsonStr());
		}
		String usertype = (String) session.getAttribute("usertype");
		if (!"superuser".equalsIgnoreCase(usertype)) {
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, "403;该页面禁止非超级管理员操作").toJsonStr());
		}
		try {
			AuthorityDao.addAuthority(managerId, projectId);
			return new ModelAndView("empty", "message", new DefaultReturn(true,
					"").toJsonStr());
		} catch (SQLException e) {
			e.printStackTrace();
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, e.toString()).toJsonStr());
		}
	}

	@RequestMapping(value = "/deleteAuthority", method = RequestMethod.POST)
	public ModelAndView deleteAuthority(HttpSession session,
			@RequestParam("managerId") int managerId,
			@RequestParam("projectId") int projectId) {
		if (0 == managerId || 0 == projectId) {
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, "参数错误， 请正确操作").toJsonStr());
		}
		String usertype = (String) session.getAttribute("usertype");
		if (!"superuser".equalsIgnoreCase(usertype)) {
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, "403;该页面禁止非超级管理员操作").toJsonStr());
		}
		try {
			AuthorityDao.deleteAuthority(managerId, projectId);
			return new ModelAndView("empty", "message", new DefaultReturn(true,
					"").toJsonStr());
		} catch (SQLException e) {
			e.printStackTrace();
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, e.toString()).toJsonStr());
		}
	}
}
