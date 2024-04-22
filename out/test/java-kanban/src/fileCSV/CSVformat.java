package filecsv;

import enumtaskmanager.Progress;
import tasks.SubTask;
import tasks.Task;
import tasks.Epic;

public class CSVformat {

    static String head = "id,type,name,status,description,epic";

    public static String toString(Task task) {
        if (task.getClass().equals(SubTask.class)) {

            return task.getId() + "," + task.getType(task) + "," + task.getName() + "," + task.getStatus() + "," + task.getDiscr() + "," + ((SubTask) task).getEpicId();
        }
        return task.getId() + "," + task.getType(task) + "," + task.getName() + "," + task.getStatus() + "," + task.getDiscr();

    }

    public static Task fromString(String value) {
        String[] taskAttributes = value.split(",");
        String id = taskAttributes[0];
        String type = taskAttributes[1];
        String name = taskAttributes[2];
        String status = taskAttributes[3];
        String discr = taskAttributes[4];

        if ("Task".equals(type)) {
            return new Task(Integer.parseInt(id), name, discr, Progress.valueOf(status));
        } else if ("Epic".equals(type)) {
            return new Epic(Integer.parseInt(id),name, discr, Progress.valueOf(status));
        } else if ("SubTask".equals(type)) {
            if (taskAttributes.length > 5) {
                String epicId = taskAttributes[5];
                return new SubTask(Integer.parseInt(id), name, discr, Progress.valueOf(status), Integer.parseInt(epicId));
            }
        }
        return null;
    }

    public static String getHead() {
        return head;
    }
}
