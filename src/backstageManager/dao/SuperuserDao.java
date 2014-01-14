package backstageManager.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import backstageManager.beans.Superuser;
import cn.edu.neu.dbUtil.DBManager;
import cn.edu.neu.dbUtil.DBManagerFactory;

public class SuperuserDao {

	public static boolean isExistSuperuser(Superuser superuser)
			throws SQLException {
		DBManager dbManager = DBManagerFactory.getDBManager();
		dbManager.connect();
		String sql = "select * from superuser where username=? and passwd=?;";
		List<String> settings = new ArrayList<String>();
		settings.add(superuser.getUsername());
		settings.add(superuser.getPasswd());
		ResultSet resultSet = dbManager
				.runPreparedQueryStatement(sql, settings);
		if (resultSet.next()) {
			dbManager.close();
			return true;
		} else {
			dbManager.close();
			return false;
		}
	}

}
