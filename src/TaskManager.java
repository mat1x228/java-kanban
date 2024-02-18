
import java.util.HashMap;
import java.util.ArrayList;
public class TaskManager  {
   private int counterId;
    HashMap <Integer, Task> taskStorage;

    TaskManager() {
        taskStorage = new HashMap<>();
        counterId = 1;
    }

    public void addTask(Task task) {
        task.setId(generateNextId());
        task.setStatus(Progress.NEW);

        if (task instanceof Epic) {
            task.setType(Types.EPIC_TASK);
        } else if (task instanceof SubTask) {
            task.setType(Types.SUBTASK);
        } else {
            task.setType(Types.SIMPLE_TASK);
        }

        taskStorage.put(task.getId(), task);
    }

    public void addEpic(Epic epic) {
        epic.setId(generateNextId());
        epic.setStatus(Progress.NEW);
        epic.setType(Types.EPIC_TASK);

        ArrayList<SubTask> subtasks = epic.getSubtasks();
        if (subtasks != null) {
            for (SubTask subtask : subtasks) {
                subtask.setId(generateNextId());
                subtask.setStatus(Progress.NEW);
                subtask.setEpicId(epic.getId());
                subtask.setType(Types.SUBTASK);
            }
        }

        taskStorage.put(epic.getId(), epic);
    }

    public void addSubtask(int epicId, SubTask subtask) {
        for (Task task : taskStorage.values()) {
            if (task instanceof Epic && task.getId() == epicId) {
                Epic epic = (Epic) task;
                ArrayList<SubTask> subTasks = epic.getSubtasks();
                if (subTasks == null) {
                    subTasks = new ArrayList<>();
                }

                subtask.setStatus(Progress.NEW);
                subtask.setType(Types.SUBTASK);
                subtask.setEpicId(epicId);

                int nextSubTaskId = 1;
                if (!subTasks.isEmpty()) {
                    nextSubTaskId = subTasks.get(subTasks.size() - 1).getSubtaskId() + 1;
                }
                subtask.setSubtaskId(nextSubTaskId);

                subTasks.add(subtask);
                epic.setSubtasks(subTasks);
            }
        }
    }
    int generateNextId() {
      return counterId++;
   }

   public Task getTaskById(int taskId) {
        for (Integer id : taskStorage.keySet()) {
            if (id == taskId) {
                Task foundTask = taskStorage.get(taskId);
                return foundTask;
            }
        }
       return null;
   }

   public void deleteAllTasks() {
       taskStorage.clear();
   }

    public void deleteTaskById(int taskId) {
        ArrayList<Integer> tasksToDelete = new ArrayList<>();

        for (Integer id : taskStorage.keySet()) {
            if (id.equals(taskId)) {
                tasksToDelete.add(id);
            }
        }

        for (Integer id : tasksToDelete) {
            taskStorage.remove(id);
        }
    }
}
