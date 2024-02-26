package managerLogic;

import interfaces.HistoryManager;
import tasks.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    private ArrayList<Task> history;
    private int sizeHistory = 10;

    public InMemoryHistoryManager() {
        history = new ArrayList<>();
    }

    @Override
    public ArrayList<Task> getHistory() {
        return history;
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }

        history.add(task);
        updateHistory();
    }

    private void updateHistory() {
        if (history.size() > sizeHistory) {
            history.remove(0);
        }
    }
}