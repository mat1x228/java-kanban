package tests.manager;

import managerlogic.InMemoryHistoryManager;
import interfaces.HistoryManager;
import org.junit.jupiter.api.Test;
import tasks.Progress;
import tasks.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryHistoryManagerTest {

    @Test
    void testPreviousTaskSavedInHistory() {
        HistoryManager historyManager = new InMemoryHistoryManager();

        Task task1 = new Task("task1", "Task Description", Progress.NEW);
        Task task2 = new Task("task1", "Task Description", Progress.NEW);

        historyManager.add(task1);
        historyManager.add(task2);

        List<Task> history = historyManager.getHistory();

        assertEquals(1, history.size());
        assertEquals(task1, history.get(0));
    }
}