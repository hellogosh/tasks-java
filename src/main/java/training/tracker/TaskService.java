package training.tracker;


import training.tracker.models.Task;
import training.tracker.models.TaskStatus;

import java.util.ArrayList;
import java.util.List;

public class TaskService {

    public List<Task> tasksWithStatus(List<Task> tasks, TaskStatus requiredStatus) {
        List<Task> result = new ArrayList<>();

        for (Task task : tasks) {
            if (task.status() == requiredStatus) {
                result.add(task);
            }
        }

        return result;
    }

    // Модуль 6: switch по enum; default не нужен — новый статус подсветит компилятор
    public boolean isFinal(TaskStatus status) {
        return switch (status) {
            case DONE, CANCELLED -> true;
            case OPEN, IN_PROGRESS -> false;
        };
    }
}
