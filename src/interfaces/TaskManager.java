package interfaces;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;

public interface TaskManager {
    void addTask(Task task);

    void addEpic(Epic epic);

    void addSubtask(SubTask subtask);

    int generateNextId();

    ArrayList<Epic> getEpicStorage();

    ArrayList<SubTask> getSubTaskStorage();

    ArrayList<Task> getTaskStorage();

    SubTask getSubtaskById(int id);

    Task getTaskById(int id);

    Epic getEpicById(int id);

    void removeTaskById(int id);

    void removeEpicById(int id);

    void removeSubtaskById(int id);

    void clearSubtasks();

    void clearTasks();

    void clearEpics();

    void updateTask(Task task);

    void updateSubTask(SubTask subTask);

    void updateEpic(Epic epic);

    void updateEpicStatus(Epic epic);

    ArrayList<SubTask> getEpicsSubtasks(int epicId);
}
