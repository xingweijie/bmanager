package backstageManager.beans;


public class Manager {
	private int id;
	private String username;
	private String passwd;
	private boolean isActive;

	public Manager(int id, String username, String passwd, boolean isActive) {
		super();
		this.id = id;
		this.username = username;
		this.passwd = passwd;
		this.isActive = isActive;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

}
