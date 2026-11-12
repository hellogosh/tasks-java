package training.tracker.services;

import training.tracker.models.Task;
import training.tracker.models.TaskStatus;

public class TaskWorkflow {

    public Task close(Task task) {
        if (task.title() == null || task.title().isBlank()) {
            throw new IllegalArgumentException("Task title is required");
        }

        return new Task(
                task.key(),
                task.title(),
                TaskStatus.DONE,
                task.priority()
        );
    }

    // Модуль 7: свой throw — начать можно только открытую задачу
    public Task start(Task task) {
        if (task.status() != TaskStatus.OPEN) {
            throw new IllegalStateException("Only OPEN task can be started");
        }

        return new Task(task.key(), task.title(), TaskStatus.IN_PROGRESS, task.priority());
    }
}
