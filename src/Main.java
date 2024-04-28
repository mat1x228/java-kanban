import enumtaskmanager.Progress;
import filecsv.CSVformat;
import managerlogic.FileBackedTaskManager;
import managerlogic.InMemoryHistoryManager;
import tasks.*;
import managerlogic.InMemoryTaskManager;

import java.io.File;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        CSVformat csvString = new CSVformat();

        File file = new File("src/resoursers/file.txt");

        FileBackedTaskManager manager = new FileBackedTaskManager(file);


//        Task task1 = new Task("Task1", "Task1 discr", Progress.NEW);
//        manager.addTask(task1);
//        String str = csvString.toString(task1);
//        System.out.println(str);
//
//        Epic epic1 = new Epic("Epic1", "DISCR");
//        manager.addEpic(epic1);
//
//        SubTask subTask1 = new SubTask("Subtask","zalupa", Progress.IN_PROGRESS, epic1.getId());
//        manager.addSubtask(subTask1);
//
//        FileBackedTaskManager loadManager = FileBackedTaskManager.loadFromFile(file);

//        System.out.println(loadManager.getTaskStorage());


        Task task1 = new Task("Task 1", "Description 1", Progress.NEW, 60, LocalDateTime.now());

        Task task2 = new Task("Task 2", "Description 2", Progress.NEW, 65, LocalDateTime.now().plusDays(20));

        Task task3 = new Task("Task 3", "Description 1", Progress.NEW, 65, LocalDateTime.now().plusDays(1));
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        System.out.println(taskManager.getPrioritizedTasks());

    }
}

