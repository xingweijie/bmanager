package backstageManager.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.neu.dbUtil.DBManager;
import cn.edu.neu.dbUtil.DBManagerFactory;

public class AuthorityDao {

	public static void addAuthority(int managerId, int projectId)
			throws SQLException {
		DBManager dbManager = DBManagerFactory.getDBManager();
		dbManager.connect();
		String sql = "insert into authority(managerId, projectId) values(?, ?);";
		List<String> settings = new ArrayList<String>();
		settings.add(String.valueOf(managerId));
		settings.add(String.valueOf(projectId));
		dbManager.runPreparedStatement(sql, settings);
		dbManager.close();
	}

	public static void deleteAuthority(int managerId, int projectId)
			throws SQLException {
		DBManager dbManager = DBManagerFactory.getDBManager();
		dbManager.connect();
		String sql = "delete from authority where managerId=? and projectId=?";
		List<String> settings = new ArrayList<String>();
		settings.add(String.valueOf(managerId));
		settings.add(String.valueOf(projectId));
		dbManager.runPreparedUpdateStatement(sql, settings);
		dbManager.close();
	}
}
