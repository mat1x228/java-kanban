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
        taskStorage.put(task.getId(), task);
    }

    public void addEpic(Epic epic) {
        epic.setId(generateNextId());
        epicStorage.put(epic.getId(), epic);
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
        updateEpicStatus(epic);
    }

    public int generateNextId() {
        return counterId++;
    }

    public ArrayList<Epic> getEpicStorage() {
        ArrayList<Epic> epicList = new ArrayList<>(epicStorage.values());
        return epicList;
    }

    public ArrayList<SubTask> getSubTaskStorage() {
        ArrayList<SubTask> subtaskList = new ArrayList<>(subtaskStorage.values());
        return subtaskList;
    }

    public ArrayList<Task> getTaskStorage() {
        ArrayList<Task> taskList = new ArrayList<>(taskStorage.values());
        return taskList;
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
                subtaskStorage.remove(subTask.getId());
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

    /*похоже придется отрефакторить способ хранения сабтаск в рамках следующих ТЗ, ибо это доставляет слишком много лишних хлопот, оставлю пока в таком виде,
    на данный момент цель допердеть до ТЗ-5 учитывая минимальные временные ресурсы)
    * */
    public void clearSubtasks() {

        ArrayList<SubTask> allSubTasks = new ArrayList<>(subtaskStorage.values());
        for (SubTask subTask : allSubTasks) {
            Integer epicId = subTask.getEpicId();
            Epic epic = epicStorage.get(epicId);
            if (epic != null) {
                epic.getSubTasks().clear();
                epic.setStatus(Progress.NEW);
            }
        }
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
        Integer oldSubTaskId = subTask.getId();
        taskStorage.remove(oldSubTaskId);
        updatedEpic.getSubTasks().remove(oldSubTaskId);
        subtaskStorage.put(subTask.getId(), subTask);
        updatedEpic.addSubTask(subTask);
        updateEpicStatus(updatedEpic);

    }

    public void updateEpic(Epic epic) {
        updateEpicStatus(epic);
        epicStorage.put(epic.getId(), epic);
    }

    private void updateEpicStatus(Epic epic) {
        ArrayList<SubTask> currentSubs = epic.getSubTasks();
        boolean allTasksDone = true;
        boolean allTasksNew = true;

        if (currentSubs.isEmpty() || currentSubs == null) {
            epic.setStatus(Progress.NEW);
        } else {
            for (SubTask subs : currentSubs) {
                if (!subs.getStatus().equals(Progress.DONE)) {
                    allTasksDone = false;
                }
                if (!subs.getStatus().equals(Progress.NEW)) {
                    allTasksNew = false;
                }
            }

            if (allTasksDone) {
                epic.setStatus(Progress.DONE);
            } else if (allTasksNew) {
                epic.setStatus(Progress.NEW);
            } else {
                epic.setStatus(Progress.IN_PROGRESS);
            }
        }
    }

    public ArrayList<SubTask> getEpicsSubtasks(int epicId) {
        if (epicStorage.containsKey(epicId)) {
            return new ArrayList<>(epicStorage.get(epicId).getSubTasks());
        } else {
            return new ArrayList<>();
        }
    }

}
