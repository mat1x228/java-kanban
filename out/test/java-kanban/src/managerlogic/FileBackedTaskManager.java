package managerlogic;

import enumTaskManager.Progress;
import interfaces.HistoryManager;
import interfaces.TaskManager;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileBackedTaskManager extends InMemoryTaskManager {


    public FileBackedTaskManager(HistoryManager historyManager) {
        super(historyManager);
    }

    public void save () {

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
