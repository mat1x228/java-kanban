import java.util.Objects;

public class Task {
   private int id;
   private String name;
   private String discr;
   private Progress status;
   private Types type;

   public void setType(Types type) {
       this.type = type;
   }

    public Types getType () {
        return type;
    }

    Task(String name, String discr) {
        this.name = name;
        this.discr = discr;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", discr='" + discr + '\'' +
                ", status=" + status +
                ", type=" + type +
                '}';
    }

    public void setStatus(Progress status) {
        this.status = status;
    }

    public int getTaskId(){
        return id;
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
    public void updateTaskStatus(int taskId, Progress newStatus) {
        if (this.getId() == taskId) {
            this.setStatus(newStatus);
        }
    }
}
