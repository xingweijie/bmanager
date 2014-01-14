package backstageManager.beans;

public class Authority {
	private int id;
	private int managerId;
	private int projectId;
	
	public Authority(int id, int managerId, int projectId) {
		super();
		this.id = id;
		this.managerId = managerId;
		this.projectId = projectId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

}
