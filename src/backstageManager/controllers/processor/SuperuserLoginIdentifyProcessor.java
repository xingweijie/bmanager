package backstageManager.controllers.processor;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import backstageManager.beans.Superuser;
import backstageManager.controllers.returnObject.ReturnObject;
import backstageManager.controllers.returnObject.SuperuserLoginIdentifyReturn;
import backstageManager.dao.SuperuserDao;

public class SuperuserLoginIdentifyProcessor implements Processor {

	private String username;
	private String passwd;
	private HttpSession session;

	public SuperuserLoginIdentifyProcessor(String username, String passwd,
			HttpSession session) {
		this.username = username;
		this.passwd = passwd;
		this.session = session;
	}

	@Override
	public ReturnObject processForGet() {
		return new SuperuserLoginIdentifyReturn(false, "请使用post方法");
	}

	@Override
	public ReturnObject processForPost() {
		if (null == username || null == passwd) {
			return new SuperuserLoginIdentifyReturn(false, "parameter error");
		}
		Superuser superuser = new Superuser(0, username, passwd);
		try {
			if (SuperuserDao.isExistSuperuser(superuser)) {
				session.setAttribute("usertype", "superuser");
				session.setAttribute("username", superuser.getUsername());
				return new SuperuserLoginIdentifyReturn(true, "");
			} else {
				return new SuperuserLoginIdentifyReturn(false, "incorrect username or password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new SuperuserLoginIdentifyReturn(false, "database error: " + e);
		}
	}

}
