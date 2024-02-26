import interfaces.HistoryManager;
import managerLogic.InMemoryHistoryManager;
import tasks.*;
import managerLogic.InMemoryTaskManager;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        InMemoryTaskManager taskManager = new InMemoryTaskManager(historyManager);

        Task task1 = new Task("Task1", "Task1 discr", Progress.NEW);
        Task task2 = new Task("Task2", "Task1 discr", Progress.DONE);
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Epic epic1 = new Epic("Epic1", "Epic1 description");
        taskManager.addEpic(epic1);

        SubTask subTask1 = new SubTask("SubTask1", "SubTask1 desc", Progress.NEW, epic1.getId());
        SubTask subTask2 = new SubTask("SubTask2", "SubTask2 desc", Progress.NEW, epic1.getId());
        SubTask subTask3 = new SubTask("SubTask3", "SubTask3 desc", Progress.DONE, epic1.getId());
        taskManager.addSubtask(subTask1);
        taskManager.addSubtask(subTask2);
        taskManager.addSubtask(subTask3);

        Epic epic2 = new Epic("Epic2", "Epic2 description");
        taskManager.addEpic(epic2);

        SubTask subTask4 = new SubTask("SubTask4", "SubTask4 desc", Progress.IN_PROGRESS, epic2.getId());
        taskManager.addSubtask(subTask4);

        taskManager.getEpicById(epic2.getId());
        taskManager.getEpicById(epic1.getId());
        taskManager.getTaskById(task1.getId());

        Epic epic = taskManager.getEpicById(epic2.getId());

        System.out.println("Вывод найденного эпика:");
        System.out.println(epic);

        ArrayList<Task> history = historyManager.getHistory();

        System.out.println("\nИстория просмотров задач:");
        for (Task viewedTask : history) {
            System.out.println("- " + viewedTask);
        }
    }

}

