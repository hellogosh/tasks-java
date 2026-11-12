package training.tracker.builders;

import training.tracker.Priority;
import training.tracker.Task;
import training.tracker.TaskStatus;

public class TaskBuilder {

    private String key = "QA-1";
    private String title = "Default task";
    private TaskStatus status = TaskStatus.OPEN;
    private Priority priority = Priority.MEDIUM;

    public TaskBuilder withKey(String key) {
        this.key = key;
        return this;
    }

    public TaskBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public TaskBuilder withStatus(TaskStatus status) {
        this.status = status;
        return this;
    }

    public TaskBuilder withPriority(Priority priority) {
        this.priority = priority;
        return this;
    }

    public Task build() {
        return new Task(key, title, status, priority);
    }
}
