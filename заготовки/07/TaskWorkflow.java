package training.tracker;

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
}
