package managerLogic;

import interfaces.HistoryManager;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    HashMap<Integer,Node> historyTask;
    private Node head;
    private Node tail;
    int size = 0;

    public InMemoryHistoryManager() {
        historyTask = new HashMap<>();
    }

    @Override
    public ArrayList<Task> getHistory() {
        return (ArrayList<Task>) getTasks();
    }

    @Override
    public void add(Task task) {
        if (historyTask.containsKey(task.getId())) {
            remove(task.getId());
        } else {
            Task taskForHistory = new Task(task.getName(), task.getDiscr(), task.getStatus());
            linkLast(taskForHistory);
            historyTask.put(taskForHistory.getId(), tail);
        }
    }

    @Override
   public void remove(int id) {
        Node task = historyTask.get(id);
    removeNode(task);
    historyTask.remove(id);
    }

    public class Node {
        Task data;
        Node next;
        Node prev;

        Node(Task data, Node next, Node prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    public void linkLast(Task e) {
        final Node oldTail = tail;
        final Node newNode = new Node(e, null , oldTail);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.prev = newNode;
            size++;
        }
    }

    public ArrayList<Task> getTasks() {
     ArrayList<Task> taskList = new ArrayList<>();
        for (Map.Entry<Integer, Node> entry : historyTask.entrySet()) {
            Node value = entry.getValue();
            taskList.add(value.data);
        }
     return taskList;
    }

    public void removeNode(Node node) {
        if (node == null) {
            return;
        }
        if (node == head) {
          head = node.next;
        }
        if (node == tail) {
            tail = node.prev;
        }
        if (node.prev != null) {
            node.prev.next = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        }

        node.prev = null;
        node.next = null;

        size--;
    }

}