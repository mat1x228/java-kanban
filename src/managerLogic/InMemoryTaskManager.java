package managerLogic;

import interfaces.HistoryManager;
import interfaces.TaskManager;
import tasks.Epic;
import tasks.Progress;
import tasks.SubTask;
import tasks.Task;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


public class InMemoryTaskManager implements TaskManager {
    private int counterId;
    private HashMap<Integer, Task> taskStorage;
    private HashMap<Integer, Epic> epicStorage;
    private HashMap<Integer, SubTask> subtaskStorage;
    private HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.taskStorage = new HashMap<>();
        this.epicStorage = new HashMap<>();
        this.subtaskStorage = new HashMap<>();
        this.counterId = 1;
        this.historyManager = historyManager;
    }

    @Override
    public void addTask(Task task) {
        task.setId(generateNextId());
        taskStorage.put(task.getId(), task);
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(generateNextId());
        epicStorage.put(epic.getId(), epic);
    }

    @Override
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

    @Override
    public List<Epic> getEpicStorage() {
        return new ArrayList<>(epicStorage.values());
    }

    @Override
    public List<SubTask> getSubTaskStorage() {
        return new ArrayList<>(subtaskStorage.values());
    }

    @Override
    public List<Task> getTaskStorage() {
        return new ArrayList<>(taskStorage.values());
    }

    @Override
    public Task getTaskById(int id) {
        Task task = taskStorage.get(id);
        if (task != null) {
            addTaskToHistory(task, historyManager);
        }
        return task;
    }

    @Override
    public SubTask getSubtaskById(int id) {
        SubTask subTask = subtaskStorage.get(id);
        if (subTask != null) {
            addTaskToHistory(subTask, historyManager);
        }
        return subTask;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epicStorage.get(id);
        if (epic != null) {
            addTaskToHistory(epic, historyManager);
        }
        return epic;
    }

    private void addTaskToHistory(Task task, HistoryManager historyManager) {
        historyManager.add(task);
    }

    // Остальные методы оставляем без изменений


    @Override
    public void removeTaskById(int id) {
        taskStorage.remove(id);
    }

    @Override
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

    @Override
    public void removeSubtaskById(int id) {
        SubTask currentSubtask = subtaskStorage.get(id);
        Integer updatedEpicId = currentSubtask.getEpicId();
        Epic updatedEpic = epicStorage.get(updatedEpicId);
        updateEpicStatus(updatedEpic);
        subtaskStorage.remove(id);
    }


    @Override
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

    @Override
    public void clearTasks() {
        taskStorage.clear();
    }

    @Override
    public void clearEpics() {
        epicStorage.clear();
    }

    @Override
    public void updateTask(Task task) {
        taskStorage.put(task.getId(), task);
    }

    @Override
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

    @Override
    public void updateEpic(Epic epic) {
        updateEpicStatus(epic);
        epicStorage.put(epic.getId(), epic);
    }


    public void updateEpicStatus(Epic epic) {
        List<SubTask> currentSubs = epic.getSubTasks();
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

    @Override
    public List<SubTask> getEpicsSubtasks(int epicId) {
        if (epicStorage.containsKey(epicId)) {
            return new ArrayList<>(epicStorage.get(epicId).getSubTasks());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Task> getHistory(){
        return historyManager.getHistory();
    }


}
