package service.manager;

import interfaces.HistoryManager;
import interfaces.TaskManager;
import managerlogic.InMemoryTaskManager;
import managerlogic.Managers;
import org.junit.Test;
import tasks.Epic;
import enumTaskManager.Progress;
import tasks.SubTask;
import tasks.Task;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTest {

    @Test
    public void testAddDifferentTasksAndFindById() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        TaskManager taskManager = new InMemoryTaskManager(historyManager);

        Task task = new Task("task1", "Task Description", Progress.NEW);
        taskManager.addTask(task);

        Epic epic = new Epic("epic1", "Epic Description");
        taskManager.addEpic(epic);

        SubTask subTask = new SubTask("name1", "description1", Progress.NEW, 1);
        taskManager.addSubtask(subTask);

        Task foundTask = taskManager.getTaskById(task.getId());
        Epic foundEpic = taskManager.getEpicById(epic.getId());
       // SubTask foundSubTask = taskManager.getSubtaskById(subTask.getId());


        assertNotNull(foundTask);
        assertNotNull(foundEpic);
       // assertNotNull(foundSubTask);
        assertEquals(task.getDiscr(), foundTask.getDiscr());
        assertEquals(epic.getDiscr(), foundEpic.getDiscr());
       // assertEquals(subTask.getDiscr(), foundSubTask.getDiscr());
    }

    @Test
    public void testNoIdConflictBetweenGivenAndGeneratedIds() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        TaskManager taskManager = new InMemoryTaskManager(historyManager);

        Epic epic = new Epic("epic1", "Epic Description");
        epic.setId(5);  // Manually setting an id

        SubTask subTask = new SubTask("name1", "description1", Progress.NEW, 1);
        taskManager.addSubtask(subTask);

        Task generatedTask = new Task("task1", "Task Description", Progress.NEW);
        taskManager.addTask(generatedTask);

        assertNotEquals(epic.getId(), generatedTask.getId());
    }

    @Test
    public void testTaskUnchangedAfterAddingToManager() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        TaskManager taskManager = new InMemoryTaskManager(historyManager);

        Task originalTask = new Task("task1", "Task Description", Progress.IN_PROGRESS);
        Task clonedTask = new Task(originalTask.getName(), originalTask.getDiscr(), originalTask.getStatus());

        taskManager.addTask(clonedTask);

        assertEquals(originalTask.getName(), clonedTask.getName());
        assertEquals(originalTask.getDiscr(), clonedTask.getDiscr());
        assertEquals(originalTask.getStatus(), clonedTask.getStatus());
    }
}