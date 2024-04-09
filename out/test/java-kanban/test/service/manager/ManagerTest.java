package service.manager;

import interfaces.HistoryManager;
import interfaces.TaskManager;
import managerlogic.Managers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ManagerTest {

    HistoryManager historyManager = Managers.getDefaultHistory();
    TaskManager taskManager = Managers.getDefault();

    @Test
    public void shouldReturnTaskManagerWhenManagersWorks() {
        assertNotNull(taskManager);
    }

    @Test
    public void shouldReturnHistoryManagerWhenManagersWorks() {
        assertNotNull(historyManager);
    }

}