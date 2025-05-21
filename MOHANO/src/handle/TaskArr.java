package handle;

import java.util.ArrayList;

public class TaskArr { // task Table을 받아오기 위한 class
	protected int id; // join에 필요한 user table foreign key
	protected int taskId; //user table에 join한 task table primary key
	protected ArrayList<Task> allTask; //task table 저장
	public TaskArr() {
		id = 0;
		taskId = 0;
		allTask = new ArrayList<>();
	}
	public void setAllTask(Task a) {
		allTask.add(a);
	}
	public String getAllTask() {
		return allTask.toString();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
}
