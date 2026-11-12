package training.tracker;

import java.util.ArrayList;
import java.util.List;

public class TaskService {

    // Модуль 5, переведено на enum: сравнение через ==
    public List<Task> tasksWithStatus(List<Task> tasks, TaskStatus requiredStatus) {
        List<Task> result = new ArrayList<>();

        for (Task task : tasks) {
            if (task.status() == requiredStatus) {
                result.add(task);
            }
        }

        return result;
    }
}
