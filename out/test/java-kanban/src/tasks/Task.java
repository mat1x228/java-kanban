package tasks;

import enumtaskmanager.Progress;

public class Task {
    private int id;
    private String name;
    private String discr;
    private Progress status;

    public Task(String name, String discr, Progress status) {
        this.name = name;
        this.discr = discr;
        this.status = status;
    }

    public Task(int id, String name, String discr, Progress status) {
        this.id = id;
        this.name = name;
        this.discr = discr;
        this.status = status;
    }



    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "tasks.Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", discr='" + discr + '\'' +
                ", status=" + getStatus() +
                '}';
    }

    public void setStatus(Progress status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getDiscr() {
        return discr;
    }

    public Progress getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getType(Task task) {
        String taskType = task.getClass().toString();
        int index = taskType.indexOf(".");
        taskType = taskType.substring(index + 1);
        return taskType;
    }

}
