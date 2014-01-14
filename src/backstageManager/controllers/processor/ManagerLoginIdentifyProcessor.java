package backstageManager.controllers.processor;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import backstageManager.controllers.returnObject.ManagerLoginIdentifyReturn;
import backstageManager.controllers.returnObject.ReturnObject;
import backstageManager.dao.ManagerDao;

public class ManagerLoginIdentifyProcessor implements Processor {

	private String username;
	private String passwd;
	private HttpSession session;

	public ManagerLoginIdentifyProcessor(String username, String passwd,
			HttpSession session) {
		this.username = username;
		this.passwd = passwd;
		this.session = session;
	}

	@Override
	public ReturnObject processForGet() {
		return new ManagerLoginIdentifyReturn(false, "请使用post方法");
	}

	@Override
	public ReturnObject processForPost() {
		if (null == username || null == passwd) {
			return new ManagerLoginIdentifyReturn(false, "parameter error");
		}
		try {
			if (ManagerDao.isExistManager(username, passwd)) {
				session.setAttribute("usertype", "manager");
				session.setAttribute("username", username);
				return new ManagerLoginIdentifyReturn(true, "");
			} else {
				return new ManagerLoginIdentifyReturn(false,
						"incorrect username or password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new ManagerLoginIdentifyReturn(false, "database error: " + e);
		}
	}

}
