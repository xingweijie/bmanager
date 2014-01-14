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

import cn.edu.neu.dateUtils.DateValidator;

import backstageManager.beans.Category;
import backstageManager.beans.Manager;
import backstageManager.beans.Project;
import backstageManager.controllers.returnObject.DefaultReturn;
import backstageManager.dao.ManagerDao;
import backstageManager.dao.ProjectDao;
import backstageManager.dao.exception.NotFoundException;

@Controller
@SessionAttributes
public class EditProject {
	@RequestMapping(value = "/editProject", method = RequestMethod.GET)
	public ModelAndView showProjectView(HttpSession session) {
		String usertype = (String) session.getAttribute("usertype");
		if ("superuser".equalsIgnoreCase(usertype)) {
			try {
				Map<Project, Category> map = ProjectDao.getAllProjects();
				List<Project> projects = new ArrayList<Project>(map.keySet());
				List<Category> categories = new ArrayList<Category>(
						map.values());
				ModelAndView modelAndView = new ModelAndView(
						"superuser/editProject");
				String username = (String) session.getAttribute("username");
				modelAndView.addObject("username", username);
				modelAndView.addObject("projects", projects);
				modelAndView.addObject("categories", categories);
				return modelAndView;
			} catch (SQLException e) {
				e.printStackTrace();
				return new ModelAndView("errors/500", "errorMessage",
						e.toString());
			}
		} else if ("manager".equalsIgnoreCase(usertype)) {
			String name = (String) session.getAttribute("username");
			try {
				Manager manager = ManagerDao.getManagerByName(name);
				ModelAndView modelAndView = new ModelAndView(
						"manager/editProject");
				modelAndView.addObject("manager", manager);
				if (manager.getIsActive()) {
					Map<Project, Category> map = ProjectDao
							.getAuthorizedProjectsByManagerId(manager.getId());
					List<Project> projects = new ArrayList<Project>(
							map.keySet());
					List<Category> categories = new ArrayList<Category>(
							map.values());
					modelAndView.addObject("projects", projects);
					modelAndView.addObject("categories", categories);
				}
				return modelAndView;
			} catch (SQLException e) {
				e.printStackTrace();
				return new ModelAndView("errors/500", "errorMessage",
						e.toString());
			} catch (NotFoundException e) {
				e.printStackTrace();
				return new ModelAndView("errors/500", "errorMessage",
						e.toString());
			}
		} else {
			return new ModelAndView("errors/403", "resolveView", "/");
		}
	}

	@RequestMapping(value = "/addProject", method = RequestMethod.POST)
	public ModelAndView addProject(HttpSession session,
			@RequestParam("title") String title,
			@RequestParam("name") String name,
			@RequestParam("createTime") String createTime,
			@RequestParam("deadline") String deadline) {
		if (null == title || null == name || null == createTime
				|| null == deadline || "".equalsIgnoreCase(title)
				|| "".equalsIgnoreCase(name) || "".equalsIgnoreCase(createTime)
				|| "".equalsIgnoreCase(deadline)
				|| !DateValidator.isValid(createTime)
				|| !DateValidator.isValid(deadline)) {
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, "参数错误， 请正确操作").toJsonStr());
		}
		String usertype = (String) session.getAttribute("usertype");
		if (!"superuser".equalsIgnoreCase(usertype)) {
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, "403;该页面禁止非超级管理员操作").toJsonStr());
		}
		try {
			ProjectDao.addProject(title, name, createTime, deadline);
			return new ModelAndView("empty", "message", new DefaultReturn(true,
					"").toJsonStr());
		} catch (SQLException e) {
			e.printStackTrace();
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, e.toString()).toJsonStr());
		}
	}

	@RequestMapping(value = "/deleteProject", method = RequestMethod.POST)
	public ModelAndView deleteProject(HttpSession session,
			@RequestParam("projectId") int projectId) {
		if (0 == projectId) {
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, "参数错误， 请正确操作").toJsonStr());
		}
		String usertype = (String) session.getAttribute("usertype");
		if (!"superuser".equalsIgnoreCase(usertype)
				&& !"manager".equalsIgnoreCase(usertype)) {
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, "403;该页面禁止非管理员操作").toJsonStr());
		}
		try {
			ProjectDao.deleteProject(projectId);
			return new ModelAndView("empty", "message", new DefaultReturn(true,
					"").toJsonStr());
		} catch (SQLException e) {
			e.printStackTrace();
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, e.toString()).toJsonStr());
		}
	}

	@RequestMapping(value = "/updateProject", method = RequestMethod.POST)
	public ModelAndView updateProject(HttpSession session,
			@RequestParam("projectId") int projectId,
			@RequestParam("title") String title,
			@RequestParam("name") String name,
			@RequestParam("createTime") String createTime,
			@RequestParam("deadline") String deadline) {
		if (0 == projectId || null == title || null == name
				|| null == createTime || null == deadline
				|| "".equalsIgnoreCase(title) || "".equalsIgnoreCase(name)
				|| "".equalsIgnoreCase(createTime)
				|| "".equalsIgnoreCase(deadline)
				|| !DateValidator.isValid(createTime)
				|| !DateValidator.isValid(deadline)) {
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, "参数错误， 请正确操作").toJsonStr());
		}
		String usertype = (String) session.getAttribute("usertype");
		if (!"superuser".equalsIgnoreCase(usertype)
				&& !"manager".equalsIgnoreCase(usertype)) {
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, "403;该页面禁止非管理员操作").toJsonStr());
		}
		try {
			ProjectDao.updateProject(projectId, title, name, createTime,
					deadline);
			return new ModelAndView("empty", "message", new DefaultReturn(true,
					"").toJsonStr());
		} catch (SQLException e) {
			e.printStackTrace();
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, e.toString()).toJsonStr());
		}
	}
}
