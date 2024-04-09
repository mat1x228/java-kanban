package service;

import static org.junit.jupiter.api.Assertions.*;

import interfaces.TaskManager;
import managerlogic.Managers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Progress;
import tasks.SubTask;
import tasks.Task;

class TaskTest {
    static TaskManager taskManager;

    @BeforeAll
    public static void beforeAll() {
        taskManager = Managers.getDefault();
    }

    @Test
    public void shouldReturnTrueIfTaskIdsEquals() {
        Task task1 = new Task("name1", "description1",  Progress.NEW);
        Task task2 = new Task("name1", "description1",  Progress.NEW);
        assertEquals(task1.getId(), task2.getId());
    }

    @Test
    public void shouldReturnTrueIfEpicIdsEquals() {
        Epic epic1 = new Epic("name1", "description1");
        Epic epic2 = new Epic("name1", "description1");
        assertEquals(epic1.getId(), epic2.getId());
    }

    @Test
    public void shouldReturnTrueIfSubtaskIdsEquals() {
        SubTask subtask1 = new SubTask("name1", "description1", Progress.NEW, 1);
        SubTask subtask2 = new SubTask("name1", "description1", Progress.NEW, 1);

        assertEquals(subtask1.getId(), subtask2.getId());
    }
}
