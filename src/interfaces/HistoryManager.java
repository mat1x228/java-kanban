package interfaces;

import tasks.Task;

public interface HistoryManager {

    void add(Task task);

    void remove(int id);

    void clearHistory();

}
