package backstageManager.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import backstageManager.beans.Participator;
import cn.edu.neu.dateUtils.DateTransfer;
import cn.edu.neu.dbUtil.DBManager;
import cn.edu.neu.dbUtil.DBManagerFactory;

public class ParticipatorDao {

	public static List<Participator> getParticipators() throws SQLException {
		DBManager dbManager = DBManagerFactory.getDBManager();
		dbManager.connect();
		String sql = "select * from participator;";
		List<String> settings = new ArrayList<String>();
		ResultSet resultSet = dbManager
				.runPreparedQueryStatement(sql, settings);
		List<Participator> participators = new ArrayList<Participator>();
		int id;
		String name;
		boolean sex;
		String phone;
		String email;
		String province;
		String city;
		String area;
		String address;
		String qq;
		String time;
		while (resultSet.next()) {
			id = resultSet.getInt("id");
			name = resultSet.getString("name");
			sex = (1 == resultSet.getInt("sex"));
			phone = resultSet.getString("phone");
			email = resultSet.getString("email");
			province = resultSet.getString("province");
			city = resultSet.getString("city");
			area = resultSet.getString("area");
			address = resultSet.getString("address");
			qq = resultSet.getString("qq");
			time = resultSet.getString("time");
			try {
				participators.add(new Participator(id, name, sex, phone, email,
						province, city, area, address, qq, DateTransfer
								.toDate(time)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		dbManager.close();
		return participators;
	}

	public static List<Participator> getParticipatorsInProject(int projectId)
			throws SQLException {
		DBManager dbManager = DBManagerFactory.getDBManager();
		dbManager.connect();
		String sql = "select"
				+ " participator.id, participator.name, participator.sex,"
				+ " participator.phone, participator.email, participator.province,"
				+ " participator.city, participator.area, participator.address,"
				+ " participator.qq, participator.time "
				+ " from participator2project, participator"
				+ " where participator2project.projectId=?"
				+ " and participator2project.participatorId=participator.id;";
		List<String> settings = new ArrayList<String>();
		settings.add(String.valueOf(projectId));
		ResultSet resultSet = dbManager
				.runPreparedQueryStatement(sql, settings);
		List<Participator> participators = new ArrayList<Participator>();
		int id;
		String name;
		boolean sex;
		String phone;
		String email;
		String province;
		String city;
		String area;
		String address;
		String qq;
		String time;
		while (resultSet.next()) {
			id = resultSet.getInt("id");
			name = resultSet.getString("name");
			sex = (1 == resultSet.getInt("sex"));
			phone = resultSet.getString("phone");
			email = resultSet.getString("email");
			province = resultSet.getString("province");
			city = resultSet.getString("city");
			area = resultSet.getString("area");
			address = resultSet.getString("address");
			qq = resultSet.getString("qq");
			time = resultSet.getString("time");
			try {
				participators.add(new Participator(id, name, sex, phone, email,
						province, city, area, address, qq, DateTransfer
								.toDate(time)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		dbManager.close();
		return participators;
	}

	public static List<Participator> getNotInvolvedParticipatorsInProject(
			int projectId) throws SQLException {
		DBManager dbManager = DBManagerFactory.getDBManager();
		dbManager.connect();
		String sql = "select"
				+ " participator.id, participator.name, participator.sex,"
				+ " participator.phone, participator.email, participator.province,"
				+ " participator.city, participator.area, participator.address,"
				+ " participator.qq, participator.time "
				+ " from participator"
				+ " where participator.id not in(select distinct participatorId from participator2project where projectId=?)";
		List<String> settings = new ArrayList<String>();
		settings.add(String.valueOf(projectId));
		ResultSet resultSet = dbManager
				.runPreparedQueryStatement(sql, settings);
		List<Participator> participators = new ArrayList<Participator>();
		int id;
		String name;
		boolean sex;
		String phone;
		String email;
		String province;
		String city;
		String area;
		String address;
		String qq;
		String time;
		while (resultSet.next()) {
			id = resultSet.getInt("id");
			name = resultSet.getString("name");
			sex = (1 == resultSet.getInt("sex"));
			phone = resultSet.getString("phone");
			email = resultSet.getString("email");
			province = resultSet.getString("province");
			city = resultSet.getString("city");
			area = resultSet.getString("area");
			address = resultSet.getString("address");
			qq = resultSet.getString("qq");
			time = resultSet.getString("time");
			try {
				participators.add(new Participator(id, name, sex, phone, email,
						province, city, area, address, qq, DateTransfer
								.toDate(time)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		dbManager.close();
		return participators;
	}

	public static void updateParticipator(int participatorId, String name,
			String sex, String phone, String email, String province,
			String city, String area, String address, String qq, String time)
			throws SQLException {
		DBManager dbManager = DBManagerFactory.getDBManager();
		dbManager.connect();
		String sql = "update participator"
				+ " set name=?, sex=?, phone=?, email=?, province=?,"
				+ " city=?, area=?, address=?, qq=?, time=?" + " where id=?";
		List<String> settings = new ArrayList<String>();
		settings.add(name);
		settings.add("ÄÐ".equalsIgnoreCase(sex) ? "1" : "0");
		settings.add(phone);
		settings.add(email);
		settings.add(province);
		settings.add(city);
		settings.add(area);
		settings.add(address);
		settings.add(qq);
		settings.add(time);
		settings.add(String.valueOf(participatorId));
		dbManager.runPreparedUpdateStatement(sql, settings);
		dbManager.close();
	}

	public static void addParticipatorIntoProject(int projectId, String name,
			String sex, String phone, String email, String province,
			String city, String area, String address, String qq, String time)
			throws SQLException {
		DBManager dbManager = DBManagerFactory.getDBManager();
		dbManager.connect();
		String sql = "insert into"
				+ " participator(name, sex, phone, email, province, city, area, address, qq, time)"
				+ " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		String sql2 = " insert into participator2project(participatorId, projectId) values(LAST_INSERT_ID(), ?);";
		List<String> settings = new ArrayList<String>();
		settings.add(name);
		settings.add("ÄÐ".equalsIgnoreCase(sex) ? "1" : "0");
		settings.add(phone);
		settings.add(email);
		settings.add(province);
		settings.add(city);
		settings.add(area);
		settings.add(address);
		settings.add(qq);
		settings.add(time);
		dbManager.runPreparedStatement(sql, settings);

		settings.clear();
		settings.add(String.valueOf(projectId));
		dbManager.runPreparedStatement(sql2, settings);

		dbManager.close();
	}

	public static void addParticipator(String name, String sex, String phone,
			String email, String province, String city, String area,
			String address, String qq, String time) throws SQLException {
		DBManager dbManager = DBManagerFactory.getDBManager();
		dbManager.connect();
		String sql = "insert into"
				+ " participator(name, sex, phone, email, province, city, area, address, qq, time)"
				+ " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		List<String> settings = new ArrayList<String>();
		settings.add(name);
		settings.add("ÄÐ".equalsIgnoreCase(sex) ? "1" : "0");
		settings.add(phone);
		settings.add(email);
		settings.add(province);
		settings.add(city);
		settings.add(area);
		settings.add(address);
		settings.add(qq);
		settings.add(time);
		dbManager.runPreparedStatement(sql, settings);

		dbManager.close();
	}

	public static void addNotInvolvedParticipatorIntoProject(int projectId,
			int notInvolvedParticipatorId) throws SQLException {
		DBManager dbManager = DBManagerFactory.getDBManager();
		dbManager.connect();
		String sql = " insert into participator2project(participatorId, projectId) values(?, ?);";
		List<String> settings = new ArrayList<String>();
		settings.add(String.valueOf(notInvolvedParticipatorId));
		settings.add(String.valueOf(projectId));
		dbManager.runPreparedStatement(sql, settings);
		dbManager.close();
	}

	public static void deleteParticipator(int participatorId, int projectId)
			throws SQLException {
		DBManager dbManager = DBManagerFactory.getDBManager();
		dbManager.connect();

		List<String> settings = new ArrayList<String>();
		String sql = "delete from participator2project where participatorId=? and projectId=?;";
		settings.add(String.valueOf(participatorId));
		settings.add(String.valueOf(projectId));
		dbManager.runPreparedUpdateStatement(sql, settings);

		settings.clear();
		sql = "select * from participator2project where participatorId=?";
		settings.add(String.valueOf(participatorId));
		ResultSet resultSet = dbManager
				.runPreparedQueryStatement(sql, settings);
		if (!resultSet.next()) {
			settings.clear();
			sql = "delete from participator where id=?;";
			settings.add(String.valueOf(participatorId));
			dbManager.runPreparedUpdateStatement(sql, settings);
		}

		dbManager.close();
	}

	public static void deleteParticipator(int participatorId)
			throws SQLException {
		DBManager dbManager = DBManagerFactory.getDBManager();
		dbManager.connect();

		List<String> settings = new ArrayList<String>();
		settings.add(String.valueOf(participatorId));
		String sql = "delete from participator2project where participatorId=?;";
		dbManager.runPreparedUpdateStatement(sql, settings);
		sql = " delete from participator where id=?;";
		dbManager.runPreparedUpdateStatement(sql, settings);

		dbManager.close();
	}

	/**
	 * exist, return id of the record while not exist, return -1
	 * 
	 * @param name
	 * @param sex
	 * @param phone
	 * @param email
	 * @param province
	 * @param city
	 * @param area
	 * @param address
	 * @param qq
	 * @param time
	 * @return
	 * @throws SQLException
	 */
	public static int isExist(String name, String sex, String phone,
			String email, String province, String city, String area,
			String address, String qq, String time) throws SQLException {
		DBManager dbManager = DBManagerFactory.getDBManager();
		dbManager.connect();
		String sql = "select * from participator" + " where name=? and sex=?"
				+ " and phone=? and email=? and province=?"
				+ " and city=? and area=? and address=?"
				+ " and qq=? and time=?;";
		List<String> settings = new ArrayList<String>();
		settings.add(name);
		settings.add("ÄÐ".equalsIgnoreCase(sex) ? "1" : "0");
		settings.add(phone);
		settings.add(email);
		settings.add(province);
		settings.add(city);
		settings.add(area);
		settings.add(address);
		settings.add(qq);
		settings.add(time);
		ResultSet resultSet = dbManager
				.runPreparedQueryStatement(sql, settings);
		int id;
		if (resultSet.next()) {
			id = resultSet.getInt("id");
			dbManager.close();
			return id;
		} else {
			dbManager.close();
			return -1;
		}
	}

	public static void main(String args[]) {
		try {
			ParticipatorDao.deleteParticipator(100);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
