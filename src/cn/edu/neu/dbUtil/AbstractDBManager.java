package cn.edu.neu.dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class AbstractDBManager implements DBManager {
	private Connection connection = null;
	private Statement statement = null;

	@Override
	public void connect() {
		try {
			if (null == connection) {
//				connection = DriverManager.getConnection(getDbUrl(),
//						getUsername(), getPassword());
				connection = DriverManager.getConnection("proxool.bm_read");
			}
			if (null == statement) {
				statement = connection.createStatement();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected abstract String getPassword();

	protected abstract String getUsername();

	protected abstract String getDbUrl();

	@Override
	public void close() throws SQLException {
		if (null != statement) {
			statement.close();
			statement = null;
		}
		if (null != connection) {
			connection.close();
			connection = null;
		}
	}

	@Override
	public ResultSet excute(String sql) throws SQLException {
		ResultSet resultSet = null;
		if (null == statement) {
			connect();
		}
		resultSet = statement.executeQuery(sql);
		return resultSet;
	}

	@Override
	public void update(String sql) throws SQLException {
		if (null == statement) {
			connect();
		}
		statement.executeUpdate(sql);
	}

	@Override
	public void runPreparedUpdateStatement(String sql, List<String> settings)
			throws SQLException {
		if (null == connection) {
			connect();
		}
		PreparedStatement statement = connection.prepareStatement(sql);
		String setting;
		for (int i = 0; i < settings.size(); i += 1) {
			setting = settings.get(i);
			statement.setString(i + 1, setting);
		}
		statement.executeUpdate();
	}

	@Override
	public void runPreparedStatement(String sql, List<String> settings)
			throws SQLException {
		if (null == connection) {
			connect();
		}
		PreparedStatement statement = connection.prepareStatement(sql);
		String setting;
		for (int i = 0; i < settings.size(); i += 1) {
			setting = settings.get(i);
			statement.setString(i + 1, setting);
		}
		statement.execute();
	}

	@Override
	public ResultSet runPreparedQueryStatement(String sql, List<String> settings)
			throws SQLException {
		if (null == connection) {
			connect();
		}
		PreparedStatement statement = connection.prepareStatement(sql);
		String setting;
		for (int i = 0; i < settings.size(); i += 1) {
			setting = settings.get(i);
			statement.setString(i + 1, setting);
		}
		return statement.executeQuery();
	}
}
