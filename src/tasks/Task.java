package tasks;

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

}
