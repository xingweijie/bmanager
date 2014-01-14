package backstageManager.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import backstageManager.beans.Participator;
import backstageManager.controllers.returnObject.DefaultReturn;
import backstageManager.dao.ParticipatorDao;
import cn.edu.neu.dateUtils.DefaultFormat;
import cn.edu.neu.random.KeyGenerator;

@Controller
@SessionAttributes
public class ExportParticipators {
	@RequestMapping(value = "/exportParticipators", method = RequestMethod.GET)
	public ModelAndView showExportParticipatorsView(HttpSession session) {
		String usertype = (String) session.getAttribute("usertype");
		if (!"superuser".equalsIgnoreCase(usertype)
				&& !"manager".equalsIgnoreCase(usertype)) {
			return new ModelAndView("errors/403", "resolveView",
					"/superuserLogin.html");
		} else {
			ModelAndView modelAndView = new ModelAndView(
					"superuser/exportParticipators");
			return modelAndView;
		}
	}

	@RequestMapping(value = "/getDownloadKey", method = RequestMethod.POST)
	public ModelAndView getDownloadKey(HttpSession session) {
		String usertype = (String) session.getAttribute("usertype");
		if (!"superuser".equalsIgnoreCase(usertype)
				&& !"manager".equalsIgnoreCase(usertype)) {
			return new ModelAndView("empty", "message", new DefaultReturn(
					false, "403;该页面禁止非超级管理员操作").toJsonStr());
		} else {
			String downloadKey = KeyGenerator.genrate();
			session.setAttribute("downloadKey", downloadKey);
			return new ModelAndView("empty", "message", new DefaultReturn(true,
					downloadKey).toJsonStr());
		}
	}

	@RequestMapping(value = "/downloadfile", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> download(HttpSession session,
			@RequestParam("key") String key) throws IOException {

		if (null == key || "".equals(key)) {
			return new ResponseEntity<byte[]>(HttpStatus.METHOD_FAILURE);
		}

		String seperator = ",";
		String endCharacter = "\n";
		String downloadKey = (String) session.getAttribute("downloadKey");
		if (!key.equalsIgnoreCase(downloadKey)) {
			return new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}
		session.removeAttribute("downloadKey");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "participators.csv");
		StringBuffer fileStr = new StringBuffer();
		try {
			List<Participator> participators = ParticipatorDao
					.getParticipators();
			for (Participator participator : participators) {
				fileStr.append(participator.getName());
				fileStr.append(seperator);
				fileStr.append(participator.getSex() ? "男" : "女");
				fileStr.append(seperator);
				fileStr.append(participator.getPhone());
				fileStr.append(seperator);
				fileStr.append(participator.getEmail());
				fileStr.append(seperator);
				fileStr.append(participator.getProvince());
				fileStr.append(seperator);
				fileStr.append(participator.getCity());
				fileStr.append(seperator);
				fileStr.append(participator.getArea());
				fileStr.append(seperator);
				fileStr.append(participator.getAddress());
				fileStr.append(seperator);
				fileStr.append(participator.getQq());
				fileStr.append(seperator);
				fileStr.append(new SimpleDateFormat(DefaultFormat
						.getDefaultDateformat()).format(participator.getTime()));
				fileStr.append(endCharacter);
			}
			return new ResponseEntity<byte[]>(fileStr.toString().getBytes(),
					headers, HttpStatus.CREATED);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<byte[]>(HttpStatus.METHOD_FAILURE);
		}
	}
}
