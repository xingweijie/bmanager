package cn.edu.neu.dbUtil;

public class DBManagerFactory {
	private static final boolean local = true;

	public static DBManager getDBManager() {
		if (local) {
			return new LocalDBManager();
		} else {
			return null;
		}
	}

}
