package cn.edu.neu.dbUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface DBManager {
	public abstract void connect();

	public abstract void close() throws SQLException;

	public abstract ResultSet excute(String sql) throws SQLException;

	public abstract void update(String sql) throws SQLException;

	public abstract void runPreparedUpdateStatement(String sql,
			List<String> settings) throws SQLException;

	public abstract void runPreparedStatement(String sql, List<String> settings)
			throws SQLException;

	public abstract ResultSet runPreparedQueryStatement(String sql,
			List<String> settings) throws SQLException;

}
