package backstageManager.beans;

import java.util.Date;

public class Project {
	private int id;
	private String title;
	private int categoryId;
	private Date createTime;
	private Date deadline;

	public Project(int id, String title, int categoryId, Date createTime,
			Date deadline) {
		super();
		this.id = id;
		this.title = title;
		this.categoryId = categoryId;
		this.createTime = createTime;
		this.deadline = deadline;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

}
