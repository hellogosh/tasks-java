package training.tracker;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskServiceTest {

    @Test
    void shouldReturnOnlyTasksWithRequiredStatus() {
        TaskService service = new TaskService();

        List<Task> result = service.tasksWithStatus(
                List.of(task("QA-1", TaskStatus.OPEN), task("QA-2", TaskStatus.DONE), task("QA-3", TaskStatus.OPEN)),
                TaskStatus.OPEN
        );

        assertEquals(List.of(task("QA-1", TaskStatus.OPEN), task("QA-3", TaskStatus.OPEN)), result);
    }

    @Test
    void shouldReturnEmptyListWhenNoTasksMatchStatus() {
        TaskService service = new TaskService();

        List<Task> result = service.tasksWithStatus(List.of(task("QA-2", TaskStatus.DONE)), TaskStatus.OPEN);

        assertTrue(result.isEmpty());
    }

    @Test
    void filteredTaskShouldKeepAllFields() {
        TaskService service = new TaskService();
        Task task = new Task("QA-1", "Write tests", TaskStatus.OPEN, Priority.HIGH);

        Task actualTask = service.tasksWithStatus(List.of(task), TaskStatus.OPEN).getFirst();

        assertAll(
                () -> assertEquals("QA-1", actualTask.key(), "key"),
                () -> assertEquals("Write tests", actualTask.title(), "title"),
                () -> assertEquals(TaskStatus.OPEN, actualTask.status(), "status"),
                () -> assertEquals(Priority.HIGH, actualTask.priority(), "priority")
        );
    }

    @Test
    void openTasksShouldKeepSourceOrder() {
        TaskService service = new TaskService();
        Task firstOpen = new Task("QA-1", "Write tests", TaskStatus.OPEN, Priority.HIGH);
        Task secondOpen = new Task("QA-3", "Review checks", TaskStatus.OPEN, Priority.LOW);

        List<Task> result = service.tasksWithStatus(
                List.of(firstOpen, task("QA-2", TaskStatus.DONE), secondOpen),
                TaskStatus.OPEN
        );

        assertEquals(List.of(firstOpen, secondOpen), result);
    }

    private Task task(String key, TaskStatus status) {
        return new Task(key, "Default title", status, Priority.MEDIUM);
    }
}
