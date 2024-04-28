package tasks;

import enumtaskmanager.Progress;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    private LocalDateTime endTime;
    private ArrayList<SubTask> subTasks = new ArrayList<>();

    public Epic(String name, String discription) {
        super(name, discription, Progress.NEW);
    }

    public Epic(int id, String name, String discription, Progress progress,long duration, LocalDateTime startTime) {
        super(id, name, discription, progress, duration, startTime);
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
                ", endTime =" + getEndTime() +
                '}';
    }

    public Duration getDuration() {
        Duration epicDuration = null;
        for (SubTask subTask : subTasks) {
            epicDuration = epicDuration.plus(subTask.getDuration());
        }
        return epicDuration;
    }

    @Override
    public LocalDateTime getStartTime() {
        LocalDateTime earliestTime = subTasks.stream()
                .map(SubTask::getStartTime)
                .min(LocalDateTime::compareTo)
                .orElse(null);

        return earliestTime;
    }

    public SubTask getLastSubTask() {
        if (subTasks.isEmpty()) {
            return null;
        } else {
            return subTasks.get(subTasks.size() - 1);
        }
    }

    @Override
    public LocalDateTime getEndTime() {
        LocalDateTime latestTime = subTasks.stream()
                .map(Task::getStartTime)
                .max(LocalDateTime::compareTo)
                .orElse(null);
        return latestTime.plus(getLastSubTask().getDuration());
    }
}
