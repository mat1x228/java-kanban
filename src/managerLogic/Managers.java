package managerLogic;

import interfaces.HistoryManager;
import interfaces.TaskManager;

public class Managers {
    public static TaskManager getDefault(){
        return getInMemoryTaskManager(getDefaultHistory());
    }

    public static InMemoryTaskManager getInMemoryTaskManager(HistoryManager historyManager){
        return new InMemoryTaskManager(historyManager);
    }

    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
}