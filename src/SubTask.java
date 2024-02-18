public class SubTask extends Epic {

    private int subtaskId;
    private int epicId;

    @Override
    public String toString() {
        return "SubTask{" +
                "subtaskId=" + subtaskId +
                ", epicId=" + epicId +
                ", type='" + getType() + '\'' +
                ", status=" + getStatus() +
                '}';
    }

    SubTask(String name, String discr, int epicId) {
        super(name, discr);
        this.epicId = epicId;

    }

    public void setSubtaskId (int subtaskId) {
        this.subtaskId = subtaskId;
    }

    public int getSubtaskId() {
        return subtaskId;
    }

    public void setEpicId(int epicId){
        this.epicId = epicId;
    }
    public int getEpicId() {
        return epicId;
    }


    @Override
    public void updateTaskStatus(int taskId, Progress newStatus) {
        if (this.getId() == taskId) {
            this.setStatus(newStatus);
        }
    }
}
