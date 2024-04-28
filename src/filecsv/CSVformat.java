package filecsv;

import enumtaskmanager.Progress;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import java.time.Duration;
import java.time.LocalDateTime;

public class CSVformat {

    static String head = "id,type,name,status,description,epic,duration,startTime";

    public static String toString(Task task) {
        String taskString = task.getId() + "," + task.getType(task) + "," + task.getName() + "," + task.getStatus() + "," +
                task.getDiscr();

        if (task instanceof SubTask) {
            taskString += "," + ((SubTask) task).getEpicId();
        }

        taskString += "," + task.getDuration().toMinutes() + "," + task.getStartTime().toString();
        return taskString;
    }

    public static Task fromString(String value) {
        String[] attributes = value.split(",");
        String id = attributes[0];
        String type = attributes[1];
        String name = attributes[2];
        String status = attributes[3];
        String description = attributes[4];
        long duration = Long.parseLong(attributes[6]);
        LocalDateTime startTime = LocalDateTime.parse(attributes[7]);

        if ("Task".equals(type)) {
            return new Task(name, description, Progress.valueOf(status), duration, startTime);
        } else if ("Epic".equals(type)) {
            return new Epic(Integer.parseInt(id), name, description, Progress.valueOf(status), duration, startTime);
        } else if ("SubTask".equals(type)) {
            String epicId = attributes[5];
            return new SubTask(Integer.parseInt(id), name, description, Progress.valueOf(status), Integer.parseInt(epicId), duration, startTime);
        }

        return null;
    }

    public static String getHead() {
        return head;
    }
}
