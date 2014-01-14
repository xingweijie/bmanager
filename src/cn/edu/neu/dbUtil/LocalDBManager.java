package cn.edu.neu.dbUtil;

public class LocalDBManager extends AbstractDBManager implements DBManager {
	private static final String user = "root";
	private static final String passwd = "123456";
	private static final String host = "localhost:3306";
	private static final String database = "backstageManagement";
	private static final String dbUrl = "jdbc:mysql://" + host + "/" + database
			+ "?useUnicode=true&characterEncoding=utf8";

	public LocalDBManager() {
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String getPassword() {
		return passwd;
	}

	@Override
	protected String getUsername() {
		return user;
	}

	@Override
	protected String getDbUrl() {
		return dbUrl;
	}

	public static void main(String args[]) {

	}
}
