package backstageManager.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import backstageManager.beans.Category;
import backstageManager.beans.Project;
import backstageManager.controllers.returnObject.DefaultReturn;
import backstageManager.dao.ParticipatorDao;
import backstageManager.dao.ProjectDao;

@Controller
@SessionAttributes
public class ImportParticipators {
	@RequestMapping(value = "/importParticipators", method = RequestMethod.GET)
	public ModelAndView showImportParticipatorsView(HttpSession session) {
		String usertype = (String) session.getAttribute("usertype");
		if (!"superuser".equalsIgnoreCase(usertype)
				&& !"manager".equalsIgnoreCase(usertype)) {
			return new ModelAndView("errors/403", "resolveView",
					"/superuserLogin.html");
		} else {
			try {
				Map<Project, Category> map = ProjectDao.getAllProjects();
				List<Project> projects = new ArrayList<Project>(map.keySet());
				List<Category> categories = new ArrayList<Category>(
						map.values());
				ModelAndView modelAndView = new ModelAndView(
						"superuser/importParticipators");
				modelAndView.addObject("projects", projects);
				modelAndView.addObject("categories", categories);
				return modelAndView;
			} catch (SQLException e) {
				e.printStackTrace();
				return new ModelAndView("errors/500", "errorMessage",
						e.toString());
			}
		}
	}

	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
	public ModelAndView uploadParticipatorFile(HttpSession session,
			@RequestParam("participatorFile") MultipartFile file,
			@RequestParam("projectId") int projectId) {
		String usertype = (String) session.getAttribute("usertype");
		if (!"superuser".equalsIgnoreCase(usertype)
				&& !"manager".equalsIgnoreCase(usertype)) {
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, "403;该页面禁止非超级管理员操作").toJsonStr());
		} else {
			String seperator = ",";
			Reader inputStreamReader;
			try {
				inputStreamReader = new InputStreamReader(file.getInputStream());
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				String line;
				String informations[];
				while (null != (line = bufferedReader.readLine())) {
					informations = line.split(seperator);
					int id = ParticipatorDao.isExist(informations[0],
							informations[1], informations[2], informations[3],
							informations[4], informations[5], informations[6],
							informations[7], informations[8], informations[9]);
					if (-1 == id) {
						ParticipatorDao.addParticipatorIntoProject(projectId,
								informations[0], informations[1],
								informations[2], informations[3],
								informations[4], informations[5],
								informations[6], informations[7],
								informations[8], informations[9]);
					} else {
						ParticipatorDao.addNotInvolvedParticipatorIntoProject(
								projectId, id);
					}
				}
				close(bufferedReader, inputStreamReader);
				return new ModelAndView("empty", "message", new DefaultReturn(
						true, "").toJsonStr());
			} catch (IOException e) {
				e.printStackTrace();
				return new ModelAndView("empty", "message", new DefaultReturn(
						false, e.toString()).toJsonStr());
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return new ModelAndView("empty", "message", new DefaultReturn(
						false, e.toString()).toJsonStr());
			} catch (SQLException e) {
				e.printStackTrace();
				return new ModelAndView("empty", "message", new DefaultReturn(
						false, e.toString()).toJsonStr());
			}
		}
	}

	private void close(BufferedReader bufferedReader, Reader inputStreamReader)
			throws IOException {
		if (null != bufferedReader) {
			bufferedReader.close();
		}
		if (null != inputStreamReader) {
			inputStreamReader.close();
		}
	}
}
