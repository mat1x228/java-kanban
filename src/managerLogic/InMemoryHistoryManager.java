package managerLogic;

import interfaces.HistoryManager;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;


public class InMemoryHistoryManager implements HistoryManager {

    HashMap<Integer, Node> historyTask;
    private Node head;
    private Node tail;


    public InMemoryHistoryManager() {
        historyTask = new HashMap<>();
    }

    @Override
    public ArrayList<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void add(Task task) {
        if (historyTask.containsKey(task.getId())) {
            remove(task.getId());
        } else {
            linkLast(task);
            historyTask.put(task.getId(), tail);
        }
    }

    @Override
    public void remove(int id) {
        removeNode(historyTask.remove(id));
    }

    public static class Node {
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
        final Node newNode = new Node(e, null, oldTail);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.prev = newNode;

        }
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> taskList = new ArrayList<>();

        Node currentNode = tail;
        while (currentNode != null) {
            taskList.add(currentNode.data);
            if (currentNode == head) {
                break;
            }
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
    }

    public void clearHistory() {
        historyTask.clear();
        head = null;
        tail = null;
    }

}