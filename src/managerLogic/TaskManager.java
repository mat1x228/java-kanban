package managerLogic;

import tasks.Epic;
import tasks.Progress;
import tasks.SubTask;
import tasks.Task;

import java.util.HashMap;
import java.util.ArrayList;

public class TaskManager {
    private int counterId;
    private HashMap<Integer, Task> taskStorage;
    private HashMap<Integer, Epic> epicStorage;
    private HashMap<Integer, SubTask> subtaskStorage;

    public TaskManager() {
        taskStorage = new HashMap<>();
        epicStorage = new HashMap<>();
        subtaskStorage = new HashMap<>();
        counterId = 1;
    }

    public void addTask(Task task) {
        task.setId(generateNextId());
        taskStorage.put(task.getTaskId(), task);
    }

    public void addEpic(Epic epic) {
        epic.setId(generateNextId());
        epicStorage.put(epic.getTaskId(), epic);
    }

    public void addSubtask(SubTask subtask) {
        int epicId = subtask.getEpicId();
        Epic epic = epicStorage.get(epicId);
        if (epic == null) {
            return;
        }
        int subTaskId = generateNextId();
        subtask.setId(subTaskId);
        subtaskStorage.put(subTaskId, subtask);
        epic.addSubTask(subtask);
        Integer updatedEpicId = subtask.getEpicId();
        Epic updatedEpic = epicStorage.get(updatedEpicId);
        updateEpicStatus(updatedEpic);
    }

    public int generateNextId() {
        return counterId++;
    }

    public HashMap<Integer, Epic> getEpicStorage() {
        return epicStorage;
    }

    public HashMap<Integer, SubTask> getSubTaskStorage() {
        return subtaskStorage;
    }

    public HashMap<Integer, Task> getTaskStorage() {
        return taskStorage;
    }

    public SubTask getSubtaskById(int id) {
        return subtaskStorage.get(id);
    }

    public Task getTaskById(int id) {
        return taskStorage.get(id);
    }

    public Epic getEpicById(int id) {
        return epicStorage.get(id);
    }

    public void removeTaskById(int id) {
        taskStorage.remove(id);
    }

    public void removeEpicById(int id) {
        Epic currentEpic = epicStorage.get(id);
        ArrayList<SubTask> subTasks = currentEpic.getSubTasks();
        if (!subTasks.isEmpty()) {
            for (SubTask subTask : subTasks) {
                subtaskStorage.remove(subTask);
            }
            epicStorage.remove(id);
        } else {
            epicStorage.remove(id);
        }
    }

    public void removeSubtaskById(int id) {
        SubTask currentSubtask = subtaskStorage.get(id);
        Integer updatedEpicId = currentSubtask.getEpicId();
        Epic updatedEpic = epicStorage.get(updatedEpicId);
        updateEpicStatus(updatedEpic);
        subtaskStorage.remove(id);
    }

    public void clearSubtasks() {
        subtaskStorage.clear();
    }

    public void clearTasks() {
        taskStorage.clear();
    }

    public void clearEpics() {
        epicStorage.clear();
    }

    public void updateTask(Task task) {
        taskStorage.put(task.getId(), task);
    }

    public void updateSubTask(SubTask subTask) {
        Integer updatedEpicId = subTask.getEpicId();
        Epic updatedEpic = epicStorage.get(updatedEpicId);
        updateEpicStatus(updatedEpic);
        subtaskStorage.put(subTask.getId(), subTask);
    }

    public void updateEpic(Epic epic) {
        updateEpicStatus(epic);
        epicStorage.put(epic.getId(), epic);
    }

    public void updateEpicStatus(Epic epic) {
        ArrayList<SubTask> currentSubs = epic.getSubTasks();
        boolean allTasksDone = true;

        if (currentSubs.isEmpty()) {
            epic.setStatus(Progress.NEW);
        } else {
            for (SubTask subs : currentSubs) {
                if (!subs.getStatus().equals(Progress.DONE)) {
                    allTasksDone = false;
                    break;
                }
            }

            if (allTasksDone) {
                epic.setStatus(Progress.DONE);
            } else {
                epic.setStatus(Progress.IN_PROGRESS);
            }
        }
    }

    public ArrayList<SubTask> getEpicsSubtasks(Epic epic) {

        ArrayList<SubTask> subTasksList = new ArrayList<>();
        for (SubTask subTask : subtaskStorage.values()) {
            if (subTask.getEpicId() == epic.getId()) {
                subTasksList.add(subTask);
            }
        }
        return subTasksList;
    }

}
