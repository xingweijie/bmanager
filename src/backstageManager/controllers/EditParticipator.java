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
import backstageManager.beans.Participator;
import backstageManager.beans.Project;
import backstageManager.controllers.returnObject.DefaultReturn;
import backstageManager.dao.ParticipatorDao;
import backstageManager.dao.ProjectDao;
import backstageManager.dao.exception.NotFoundException;

@Controller
@SessionAttributes
public class EditParticipator {
	@RequestMapping(value = "/editParticipator", method = RequestMethod.GET)
	public ModelAndView showEditParticipatorView(HttpSession session,
			@RequestParam("projectId") int projectId) {
		if (0 == projectId) {
			return new ModelAndView("errors/500", "errorMessage", "参数错误， 请正确操作");
		}
		String usertype = (String) session.getAttribute("usertype");
		if (!"superuser".equalsIgnoreCase(usertype)
				&& !"manager".equalsIgnoreCase(usertype)) {
			return new ModelAndView("errors/403", "resolveView",
					"/superuserLogin.html");
		} else {
			try {
				Map<Project, Category> map = ProjectDao
						.getProjectById(projectId);
				List<Project> projects = new ArrayList<Project>(map.keySet());
				List<Category> categories = new ArrayList<Category>(
						map.values());
				List<Participator> participators = ParticipatorDao
						.getParticipatorsInProject(projectId);
				ModelAndView modelAndView = new ModelAndView(
						"superuser/editParticipator");
				modelAndView.addObject("project", projects.get(0));
				modelAndView.addObject("category", categories.get(0));
				modelAndView.addObject("participators", participators);
				List<Participator> notInvolvedParticipators = ParticipatorDao
						.getNotInvolvedParticipatorsInProject(projectId);
				modelAndView.addObject("notInvolvedParticipators",
						notInvolvedParticipators);
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
		}
	}

	@RequestMapping(value = "/updateParticipator", method = RequestMethod.POST)
	public ModelAndView updateParticipator(HttpSession session,
			@RequestParam("participatorId") int participatorId,
			@RequestParam("name") String name, @RequestParam("sex") String sex,
			@RequestParam("phone") String phone,
			@RequestParam("email") String email,
			@RequestParam("province") String province,
			@RequestParam("city") String city,
			@RequestParam("area") String area,
			@RequestParam("address") String address,
			@RequestParam("qq") String qq, @RequestParam("time") String time) {
		if (0 == participatorId || null == name || null == sex || null == phone
				|| null == email || null == province || null == city
				|| null == area || null == address || null == qq
				|| null == time || "".equalsIgnoreCase(name)
				|| "".equalsIgnoreCase(sex) || "".equalsIgnoreCase(phone)
				|| "".equalsIgnoreCase(email) || "".equalsIgnoreCase(province)
				|| "".equalsIgnoreCase(city) || "".equalsIgnoreCase(area)
				|| "".equalsIgnoreCase(address) || "".equalsIgnoreCase(qq)
				|| "".equalsIgnoreCase(time) || !DateValidator.isValid(time)) {
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, "参数错误， 请正确操作").toJsonStr());
		}
		String usertype = (String) session.getAttribute("usertype");
		if (!"superuser".equalsIgnoreCase(usertype)
				&& !"manager".equalsIgnoreCase(usertype)) {
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, "403;该页面禁止非管理员操作").toJsonStr());
		} else {
			try {
				ParticipatorDao.updateParticipator(participatorId, name, sex,
						phone, email, province, city, area, address, qq, time);
				return new ModelAndView("empty", "message", new DefaultReturn(
						true, "").toJsonStr());
			} catch (SQLException e) {
				e.printStackTrace();
				return new ModelAndView("empty", "message", new DefaultReturn(
						false, e.toString()).toJsonStr());
			}
		}
	}

	@RequestMapping(value = "/addParticipator", method = RequestMethod.POST)
	public ModelAndView addParticipator(HttpSession session,
			@RequestParam("projectId") int projectId,
			@RequestParam("name") String name, 
			@RequestParam("sex") String sex,
			@RequestParam("phone") String phone,
			@RequestParam("email") String email,
			@RequestParam("province") String province,
			@RequestParam("city") String city,
			@RequestParam("area") String area,
			@RequestParam("address") String address,
			@RequestParam("qq") String qq, 
			@RequestParam("time") String time) {
		if (0 == projectId || null == name || null == sex || null == phone
				|| null == email || null == province || null == city
				|| null == area || null == address || null == qq
				|| null == time || "".equalsIgnoreCase(name)
				|| "".equalsIgnoreCase(sex) || "".equalsIgnoreCase(phone)
				|| "".equalsIgnoreCase(email) || "".equalsIgnoreCase(province)
				|| "".equalsIgnoreCase(city) || "".equalsIgnoreCase(area)
				|| "".equalsIgnoreCase(address) || "".equalsIgnoreCase(qq)
				|| "".equalsIgnoreCase(time) || !DateValidator.isValid(time)) {
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, "参数错误， 请正确操作").toJsonStr());
		}
		String usertype = (String) session.getAttribute("usertype");
		if (!"superuser".equalsIgnoreCase(usertype)
				&& !"manager".equalsIgnoreCase(usertype)) {
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, "403;该页面禁止非管理员操作").toJsonStr());
		} else {
			try {
				ParticipatorDao.addParticipatorIntoProject(projectId, name,
						sex, phone, email, province, city, area, address, qq,
						time);
				return new ModelAndView("empty", "message", new DefaultReturn(
						true, "").toJsonStr());
			} catch (SQLException e) {
				e.printStackTrace();
				return new ModelAndView("empty", "message", new DefaultReturn(
						false, e.toString()).toJsonStr());
			}
		}
	}

	@RequestMapping(value = "/deleteParticipator", method = RequestMethod.POST)
	public ModelAndView deleteParticipator(HttpSession session,
			@RequestParam("participatorId") int participatorId,
			@RequestParam("projectId") int projectId) {
		if (0 == participatorId || 0 == projectId) {
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
			ParticipatorDao.deleteParticipator(participatorId, projectId);
			return new ModelAndView("empty", "message", new DefaultReturn(true,
					"").toJsonStr());
		} catch (SQLException e) {
			e.printStackTrace();
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, e.toString()).toJsonStr());
		}
	}

	@RequestMapping(value = "/addNotInvolvedParticipator", method = RequestMethod.POST)
	public ModelAndView addNotInvolvedParticipatorId(
			HttpSession session,
			@RequestParam("projectId") int projectId,
			@RequestParam("notInvolvedParticipatorId") int notInvolvedParticipatorId) {
		if (0 == projectId || 0 == notInvolvedParticipatorId) {
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, "参数错误， 请正确操作").toJsonStr());
		}
		String usertype = (String) session.getAttribute("usertype");
		if (!"superuser".equalsIgnoreCase(usertype)
				&& !"manager".equalsIgnoreCase(usertype)) {
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, "403;该页面禁止非管理员操作").toJsonStr());
		} else {
			try {
				ParticipatorDao.addNotInvolvedParticipatorIntoProject(
						projectId, notInvolvedParticipatorId);
				return new ModelAndView("empty", "message", new DefaultReturn(
						true, "").toJsonStr());
			} catch (SQLException e) {
				e.printStackTrace();
				return new ModelAndView("empty", "message", new DefaultReturn(
						false, e.toString()).toJsonStr());
			}
		}
	}

}
