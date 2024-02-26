package managerLogic;

import interfaces.HistoryManager;
import interfaces.TaskManager;

public class Managers {
    public static TaskManager getDefault(){
        return getInMemoryTaskManager();
    }

    public static InMemoryTaskManager getInMemoryTaskManager(){
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        return new InMemoryTaskManager(historyManager);
    }

    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
}