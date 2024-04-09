package tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<SubTask> subTasks = new ArrayList<>();

    public Epic(String name, String discription) {
        super(name, discription, Progress.NEW);
    }

    public void addSubTask(SubTask subTask) {
        subTasks.add(subTask);
    }

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }

    @Override
    public String toString() {
        return "tasks.Epic{" +
                "name='" + getName() + '\'' +
                ", discr='" + getDiscr() + '\'' +
                ", id='" + getId() + '\'' +
                ", status=" + getStatus() +
                ", subtasks=" + getSubTasks() +
                '}';
    }

}
