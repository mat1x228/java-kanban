package service;
import static org.junit.jupiter.api.Assertions.*;

import interfaces.TaskManager;
import managerlogic.Managers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tasks.*;


class TaskTest {
    static TaskManager taskManager;

    @BeforeAll
    public static void beforeAll() {
        taskManager = Managers.getDefault();
    }

    @Test
    public void shouldReturnTrueIfTaskIdsEquals() {
        Task task1 = new Task("name1", "description1",  Progress.NEW);
        Task task2 = new Task("name2", "description2",  Progress.NEW);
        assertEquals(task1, task2);
    }

    @Test
    public void shouldReturnTrueIfEpicIdsEquals() {
        Epic epic1 = new Epic("name1", "description1");
        Epic epic2 = new Epic("name2", "description2");
        assertEquals(epic1, epic2);
    }

    @Test
    public void shouldReturnTrueIfSubtaskIdsEquals() {
        SubTask subtask1 = new SubTask("name1", "description1", Progress.NEW, 1);
        SubTask subtask2 = new SubTask("name2", "description", Progress.NEW, 2);

        assertEquals(subtask1, subtask2);
    }
}

