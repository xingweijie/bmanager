package backstageManager.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import backstageManager.beans.Manager;
import backstageManager.dao.exception.NotFoundException;
import cn.edu.neu.dbUtil.DBManager;
import cn.edu.neu.dbUtil.DBManagerFactory;

public class ManagerDao {
	public static List<Manager> getAllManagers() throws SQLException {
		DBManager dbManager = DBManagerFactory.getDBManager();
		dbManager.connect();
		String sql = "select * from manager;";
		List<String> settings = new ArrayList<String>();
		ResultSet resultSet = dbManager
				.runPreparedQueryStatement(sql, settings);
		List<Manager> managers = new ArrayList<Manager>();
		int id;
		String username;
		String passwd;
		boolean isActive;
		while (resultSet.next()) {
			id = resultSet.getInt("id");
			username = resultSet.getString("username");
			passwd = resultSet.getString("passwd");
			isActive = (1 == resultSet.getInt("isActive")) ? true : false;
			managers.add(new Manager(id, username, passwd, isActive));
		}
		dbManager.close();
		return managers;
	}

	public static Manager getManagerById(int id) throws SQLException,
			NotFoundException {
		DBManager dbManager = DBManagerFactory.getDBManager();
		dbManager.connect();
		String sql = "select * from manager where id=?;";
		List<String> settings = new ArrayList<String>();
		settings.add(String.valueOf(id));
		ResultSet resultSet = dbManager
				.runPreparedQueryStatement(sql, settings);
		Manager manager = null;
		String username;
		String passwd;
		boolean isActive;
		while (resultSet.next()) {
			id = resultSet.getInt("id");
			username = resultSet.getString("username");
			passwd = resultSet.getString("passwd");
			isActive = (1 == resultSet.getInt("isActive")) ? true : false;
			manager = new Manager(id, username, passwd, isActive);
		}
		dbManager.close();
		if (null == manager) {
			throw new NotFoundException("manager not found by id: " + id);
		} else {
			return manager;
		}
	}

	public static void addManager(Manager manager) throws SQLException {
		DBManager dbManager = DBManagerFactory.getDBManager();
		dbManager.connect();
		String sql = "insert into manager(username, passwd, isActive) values(?, ?, ?);";
		List<String> settings = new ArrayList<String>();
		settings.add(manager.getUsername());
		settings.add(manager.getPasswd());
		settings.add(manager.getIsActive() ? "1" : "0");
		dbManager.runPreparedStatement(sql, settings);
		dbManager.close();
	}

	public static void updateManager(Manager manager) throws SQLException {
		DBManager dbManager = DBManagerFactory.getDBManager();
		dbManager.connect();
		String sql = "update manager set username=?, passwd=?, isActive=? where id=?";
		List<String> settings = new ArrayList<String>();
		settings.add(manager.getUsername());
		settings.add(manager.getPasswd());
		settings.add(manager.getIsActive() ? "1" : "0");
		settings.add(String.valueOf(manager.getId()));
		dbManager.runPreparedUpdateStatement(sql, settings);
		dbManager.close();
	}

	public static void deleteManagerById(int id) throws SQLException {
		DBManager dbManager = DBManagerFactory.getDBManager();
		dbManager.connect();

		List<String> settings = new ArrayList<String>();
		settings.add(String.valueOf(id));
		String sql = "delete from authority where managerId=?;";
		dbManager.runPreparedUpdateStatement(sql, settings);
		sql = " delete from manager where id=?";
		dbManager.runPreparedUpdateStatement(sql, settings);
		dbManager.close();
	}

	public static boolean isExistManager(String username, String passwd)
			throws SQLException {
		DBManager dbManager = DBManagerFactory.getDBManager();
		dbManager.connect();
		String sql = "select * from manager where username=? and passwd=?;";
		List<String> settings = new ArrayList<String>();
		settings.add(username);
		settings.add(passwd);
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

	public static Manager getManagerByName(String name) throws SQLException,
			NotFoundException {
		DBManager dbManager = DBManagerFactory.getDBManager();
		dbManager.connect();
		String sql = "select * from manager where username=?;";
		List<String> settings = new ArrayList<String>();
		settings.add(name);
		ResultSet resultSet = dbManager
				.runPreparedQueryStatement(sql, settings);
		Manager manager = null;
		int id;
		String username;
		String passwd;
		boolean isActive;
		while (resultSet.next()) {
			id = resultSet.getInt("id");
			username = resultSet.getString("username");
			passwd = resultSet.getString("passwd");
			isActive = (1 == resultSet.getInt("isActive")) ? true : false;
			manager = new Manager(id, username, passwd, isActive);
		}
		dbManager.close();
		if (null == manager) {
			throw new NotFoundException("manager not found by name: " + name);
		} else {
			return manager;
		}
	}

}
