public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Epic epic2 = new Epic("Epic2", "Epic1 discr2");
        taskManager.addEpic(epic2);
        SubTask subtask1 = new SubTask("sub1", "discr1", epic2.getId());
        SubTask subtask2 = new SubTask("sub2", "discr2",epic2.getId());
        taskManager.addSubtask(epic2.getId(), subtask1);
        taskManager.addSubtask(epic2.getId(), subtask2);
        subtask1.updateTaskStatus(subtask1.getTaskId(), Progress.DONE);
        subtask2.updateTaskStatus(subtask2.getTaskId(), Progress.DONE);
        //без понятия как решить проблему со сменой статусов у Эпиков, просидел 6 часов так и не нашел решение
        System.out.println(taskManager.taskStorage);
    }
}
