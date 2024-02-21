
import managerLogic.TaskManager;
import tasks.Progress;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Task task1 = new Task("Task1", "Task1 discr", Progress.NEW);
        Task task2 = new Task("Task2", "Task1 discr", Progress.DONE);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        Epic epic1 = new Epic("Task2", "Task1 discr");
        taskManager.addEpic(epic1);
        SubTask subTask1 = new SubTask("Task2", "Task1 discr", Progress.DONE, epic1.getId());
        SubTask subTask2 = new SubTask("Task2", "Task1 discr", Progress.DONE, epic1.getId());
        SubTask subTask3 = new SubTask("Task2", "Task1 discr", Progress.IN_PROGRESS, epic1.getId());
        taskManager.addSubtask(subTask1);
        taskManager.addSubtask(subTask2);
        taskManager.addSubtask(subTask3);
        taskManager.updateEpicStatus(epic1);
        Epic epic2 = new Epic("Epic2", "Task1 discr");
        taskManager.addEpic(epic2);
        SubTask subTask4 = new SubTask("Task4", "Task1 discr", Progress.IN_PROGRESS, epic2.getId());
        System.out.println(taskManager.getEpicById(epic2.getId()));
    }
}

