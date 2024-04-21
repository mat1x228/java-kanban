package managerlogic;

import enumTaskManager.Progress;
import exceptions.ManagerLoadException;
import exceptions.ManagerSaveException;
import fileCSV.CSVformat;
import interfaces.HistoryManager;
import interfaces.TaskManager;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileBackedTaskManager extends InMemoryTaskManager {

   private final File file;
    public FileBackedTaskManager(File file) {
        super();
        this.file = file;
    }

    public void save(){
        try (FileWriter fileWriter = new FileWriter(file)) {

            fileWriter.write(CSVformat.getHead() + "\n");

            for (Task task: taskStorage.values()) {
              String strTask = CSVformat.toString(task);
              fileWriter.write(strTask + "\n");
            }

            for (Epic epic: epicStorage.values()) {
                String strTask = CSVformat.toString(epic);
                fileWriter.write(strTask + "\n");
            }

            for (SubTask subTask: subtaskStorage.values()) {
                String strTask = CSVformat.toString(subTask);
                fileWriter.write(strTask + "\n");
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка сохранения в файл: " + file.getName() + "\n" + e.getMessage());
        }
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
         boolean headerLineSkipped = false;

            while ((line = reader.readLine()) != null) {
                if(!headerLineSkipped) {
                    headerLineSkipped = true;
                    continue;
                }
                Task task = CSVformat.fromString(line);
                if (task instanceof Epic) {
                    taskManager.addEpic((Epic) task);
                } else if (task instanceof SubTask) {
                    taskManager.addSubtask((SubTask) task);
                } else {
                    taskManager.addTask(task);
                }
         }

        } catch (IOException e) {
           throw new ManagerLoadException("Ошибка загрузки из файла:" + file.getName() + "\n" + e.getMessage());
        }
        return taskManager;
    }



    @Override
    public void addTask(Task task) {
    super.addTask(task);
    save();
    }

    @Override
    public void addEpic(Epic epic) {
     super.addEpic(epic);
     save();
    }

    @Override
    public void addSubtask(SubTask subtask) {
        super.addSubtask(subtask);
        save();
    }


    @Override
    public void removeEpicById(int id) {
        super.removeEpicById(id);
        save();
    }

    @Override
    public void removeSubtaskById(int id) {
    super.removeSubtaskById(id);
    save();
    }

    @Override
    public void clearSubtasks() {
     super.clearSubtasks();
     save();
    }

    @Override
    public void clearTasks() {
        super.clearTasks();
        save();
    }

    @Override
    public void clearEpics() {
     super.clearEpics();
     save();
    }

    @Override
    public void updateTask(Task task) {
     super.updateTask(task);
     save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {
     super.updateSubTask(subTask);
     save();

    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

}
