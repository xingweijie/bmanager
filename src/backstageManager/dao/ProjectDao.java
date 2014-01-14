package backstageManager.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import backstageManager.beans.Category;
import backstageManager.beans.Project;
import backstageManager.dao.exception.NotFoundException;
import cn.edu.neu.dateUtils.DateTransfer;
import cn.edu.neu.dbUtil.DBManager;
import cn.edu.neu.dbUtil.DBManagerFactory;

public class ProjectDao {
	public static Map<Project, Category> getAllProjects() throws SQLException {
		DBManager dbManager = DBManagerFactory.getDBManager();
		dbManager.connect();
		String sql = "select project.id, project.title, project.categoryId, project.createTime, project.deadline,"
				+ " category.id, category.name"
				+ " from project, category"
				+ " where project.categoryId=category.id";
		List<String> settings = new ArrayList<String>();
		ResultSet resultSet = dbManager
				.runPreparedQueryStatement(sql, settings);
		Map<Project, Category> map = new HashMap<Project, Category>();
		int projectId;
		String projectTitle;
		int projectCategoryId;
		String projectCreateTime;
		String projectDeadline;
		int categoryId;
		String categoryName;
		while (resultSet.next()) {
			projectId = resultSet.getInt("project.id");
			projectTitle = resultSet.getString("project.title");
			projectCategoryId = resultSet.getInt("project.categoryId");
			projectCreateTime = resultSet.getString("project.createTime");
			projectDeadline = resultSet.getString("project.deadline");
			categoryId = resultSet.getInt("category.id");
			categoryName = resultSet.getString("category.name");
			try {
				map.put(new Project(projectId, projectTitle, projectCategoryId,
						DateTransfer.toDate(projectCreateTime), DateTransfer
								.toDate(projectDeadline)), new Category(
						categoryId, categoryName));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		dbManager.close();
		return map;
	}

	public static Map<Project, Category> getAuthorizedProjectsByManagerId(
			int managerId) throws SQLException {
		DBManager dbManager = DBManagerFactory.getDBManager();
		dbManager.connect();
		String sql = "select project.id, project.title, project.categoryId, project.createTime, project.deadline,"
				+ " category.id, category.name"
				+ " from authority, project, category"
				+ " where authority.managerId=?"
				+ " and authority.projectId=project.id"
				+ " and project.categoryId=category.id;";
		List<String> settings = new ArrayList<String>();
		settings.add(String.valueOf(managerId));
		ResultSet resultSet = dbManager
				.runPreparedQueryStatement(sql, settings);
		Map<Project, Category> map = new HashMap<Project, Category>();
		int projectId;
		String projectTitle;
		int projectCategoryId;
		String projectCreateTime;
		String projectDeadline;
		int categoryId;
		String categoryName;
		while (resultSet.next()) {
			projectId = resultSet.getInt("project.id");
			projectTitle = resultSet.getString("project.title");
			projectCategoryId = resultSet.getInt("project.categoryId");
			projectCreateTime = resultSet.getString("project.createTime");
			projectDeadline = resultSet.getString("project.deadline");
			categoryId = resultSet.getInt("category.id");
			categoryName = resultSet.getString("category.name");
			try {
				map.put(new Project(projectId, projectTitle, projectCategoryId,
						DateTransfer.toDate(projectCreateTime), DateTransfer
								.toDate(projectDeadline)), new Category(
						categoryId, categoryName));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		dbManager.close();
		return map;
	}

	public static Map<Project, Category> getNotAuthorizedProjectsByManagerId(
			int managerId) throws SQLException {
		DBManager dbManager = DBManagerFactory.getDBManager();
		dbManager.connect();
		String sql = "select project.id, project.title, project.categoryId, project.createTime, project.deadline,"
				+ " category.id, category.name"
				+ " from project, category"
				+ " where project.id not in"
				+ "(select distinct projectId from authority where authority.managerId=?)"
				+ " and project.categoryId=category.id;";
		List<String> settings = new ArrayList<String>();
		settings.add(String.valueOf(managerId));
		ResultSet resultSet = dbManager
				.runPreparedQueryStatement(sql, settings);
		Map<Project, Category> map = new HashMap<Project, Category>();
		int projectId;
		String projectTitle;
		int projectCategoryId;
		String projectCreateTime;
		String projectDeadline;
		int categoryId;
		String categoryName;
		while (resultSet.next()) {
			projectId = resultSet.getInt("project.id");
			projectTitle = resultSet.getString("project.title");
			projectCategoryId = resultSet.getInt("project.categoryId");
			projectCreateTime = resultSet.getString("project.createTime");
			projectDeadline = resultSet.getString("project.deadline");
			categoryId = resultSet.getInt("category.id");
			categoryName = resultSet.getString("category.name");
			try {
				map.put(new Project(projectId, projectTitle, projectCategoryId,
						DateTransfer.toDate(projectCreateTime), DateTransfer
								.toDate(projectDeadline)), new Category(
						categoryId, categoryName));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		dbManager.close();
		return map;
	}

	public static void updateProject(int projectId, String title, String name,
			String createTime, String deadline) throws SQLException {
		DBManager dbManager = DBManagerFactory.getDBManager();
		dbManager.connect();
		String sql = "update project " + "set title=?, categoryId="
				+ "(select category.id from category where name=? limit 1),"
				+ " createTime=?, deadline=? where id=?";
		List<String> settings = new ArrayList<String>();
		settings.add(title);
		settings.add(name);
		settings.add(createTime);
		settings.add(deadline);
		settings.add(String.valueOf(projectId));
		dbManager.runPreparedUpdateStatement(sql, settings);
		dbManager.close();
	}

	public static void addProject(String title, String name, String createTime,
			String deadline) throws SQLException {
		DBManager dbManager = DBManagerFactory.getDBManager();
		dbManager.connect();
		String sql = "insert into project(title, categoryId, createTime, deadline)"
				+ " values(?, (select category.id from category where name=? limit 1), ?, ?);";
		List<String> settings = new ArrayList<String>();
		settings.add(title);
		settings.add(name);
		settings.add(createTime);
		settings.add(deadline);
		dbManager.runPreparedStatement(sql, settings);
		dbManager.close();
	}

	public static void deleteProject(int projectId) throws SQLException {
		DBManager dbManager = DBManagerFactory.getDBManager();
		dbManager.connect();

		List<String> settings = new ArrayList<String>();
		settings.add(String.valueOf(projectId));
		String sql = "delete from authority where projectId=?;";
		dbManager.runPreparedUpdateStatement(sql, settings);
		sql = " delete from participator2project where projectId=?;";
		dbManager.runPreparedUpdateStatement(sql, settings);
		sql = " delete from project where id=?;";
		dbManager.runPreparedUpdateStatement(sql, settings);
		
		dbManager.close();
	}

	public static Map<Project, Category> getProjectById(int projectId)
			throws SQLException, NotFoundException {
		DBManager dbManager = DBManagerFactory.getDBManager();
		dbManager.connect();
		String sql = "select project.id, project.title, project.categoryId, project.createTime, project.deadline,"
				+ " category.id, category.name"
				+ " from project, category"
				+ " where project.id=?"
				+ " and project.categoryId=category.id;";
		List<String> settings = new ArrayList<String>();
		settings.add(String.valueOf(projectId));
		ResultSet resultSet = dbManager
				.runPreparedQueryStatement(sql, settings);
		Map<Project, Category> map = new HashMap<Project, Category>();
		String projectTitle;
		int projectCategoryId;
		String projectCreateTime;
		String projectDeadline;
		int categoryId;
		String categoryName;
		if (resultSet.next()) {
			projectId = resultSet.getInt("project.id");
			projectTitle = resultSet.getString("project.title");
			projectCategoryId = resultSet.getInt("project.categoryId");
			projectCreateTime = resultSet.getString("project.createTime");
			projectDeadline = resultSet.getString("project.deadline");
			categoryId = resultSet.getInt("category.id");
			categoryName = resultSet.getString("category.name");
			try {
				map.put(new Project(projectId, projectTitle, projectCategoryId,
						DateTransfer.toDate(projectCreateTime), DateTransfer
								.toDate(projectDeadline)), new Category(
						categoryId, categoryName));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			throw new NotFoundException("project not found. projectId: "
					+ projectId);
		}
		dbManager.close();
		return map;
	}

}
