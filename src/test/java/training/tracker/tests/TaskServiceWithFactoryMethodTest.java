package training.tracker.tests;

import org.junit.jupiter.api.Test;
import training.tracker.TaskService;
import training.tracker.models.Priority;
import training.tracker.models.Task;
import training.tracker.models.TaskStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskServiceWithFactoryMethodTest {

    @Test
    void shouldReturnOnlyOpenTasks() {
        TaskService service = new TaskService();

        List<Task> result = service.tasksWithStatus(
                List.of(
                        task("QA-1", TaskStatus.OPEN),
                        task("QA-2", TaskStatus.DONE),
                        task("QA-3", TaskStatus.OPEN)
                ),
                TaskStatus.OPEN
        );

        assertEquals(List.of(task("QA-1", TaskStatus.OPEN), task("QA-3", TaskStatus.OPEN)), result);
    }

    private Task task(String key, TaskStatus status) {
        return new Task(key, "Default title", status, Priority.MEDIUM);
    }
}
