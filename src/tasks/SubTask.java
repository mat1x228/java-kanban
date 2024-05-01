package tasks;

import enumtaskmanager.Progress;

import java.time.LocalDateTime;

public class SubTask extends Task {
    private int epicId;

    public SubTask(String name, String discr, Progress progress, int epicId) {
        super(name, discr, progress);
        this.epicId = epicId;

    }

    public SubTask(int id, String name, String discr, Progress progress, int epicId, long duration, LocalDateTime startTime) {
        super(id, name, discr, progress, duration, startTime);
        this.epicId = epicId;

    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }


    @Override
    public String toString() {
        return "tasks.SubTask{" +
                "subtaskId=" + getId() +
                ", epicId=" + epicId +
                ", status=" + getStatus() +
                '}';
    }
}
