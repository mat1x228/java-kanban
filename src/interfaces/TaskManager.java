package interfaces;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.List;

public interface TaskManager {

    List<Task> getHistory();

    void addTask(Task task);

    void addEpic(Epic epic);

    void addSubtask(SubTask subtask);

    List<Epic> getEpicStorage();

    List<SubTask> getSubTaskStorage();

    List<Task> getTaskStorage();

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

    List<SubTask> getEpicsSubtasks(int epicId);
}
