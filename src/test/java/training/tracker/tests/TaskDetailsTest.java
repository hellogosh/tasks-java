package training.tracker.tests;

import org.junit.jupiter.api.Test;
import training.tracker.TaskService;
import training.tracker.models.Priority;
import training.tracker.models.Task;
import training.tracker.models.TaskStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskDetailsTest {

    @Test
    void filteredTaskShouldKeepAllFields() {
        TaskService service = new TaskService();
        Task task = new Task("QA-1", "Write tests", TaskStatus.OPEN, Priority.HIGH);

        List<Task> result = service.tasksWithStatus(List.of(task), TaskStatus.OPEN);
        Task actualTask = result.getFirst();

        assertAll(
                () -> assertEquals("QA-1", actualTask.key()),
                () -> assertEquals("Write tests", actualTask.title()),
                () -> assertEquals(TaskStatus.OPEN, actualTask.status()),
                () -> assertEquals(Priority.HIGH, actualTask.priority())
        );
    }
}
