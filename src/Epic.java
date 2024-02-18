import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<SubTask> subtasks = new ArrayList<>();

    public Epic(String name, String discr) {
        super(name, discr);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + getName() + '\'' +
                ", discr='" + getDiscr() + '\'' +
                ", id='" + getId() + '\'' +
                ", type='" + getType() + '\'' +
                ", status=" + getStatus() +
                ", subtasks=" + subtasks +
                '}';
    }

    public void setSubtasks(ArrayList<SubTask> subtasks) {
        this.subtasks = new ArrayList<>(subtasks);
    }


    public ArrayList<SubTask> getSubtasks () {
        return subtasks;
    }

    public ArrayList<SubTask> getAllSubtasksForEpicById(int epicId) {
        if (getId() == epicId) {
            return getSubtasks();
        }
        return new ArrayList<>();
    }

    public void checkSubtaskStatus() {
        boolean allDone = true;
        boolean hasSubtasks = !subtasks.isEmpty();

        for (SubTask subtask : subtasks) {
            if (subtask.getStatus() != Progress.DONE) {
                allDone = false;
                break;
            }
        }

        if (!hasSubtasks || allDone) {
            setStatus(Progress.NEW);
        } else {
            setStatus(Progress.IN_PROGRESS);
        }
    }
}
