package training.tracker.assertions;

import training.tracker.models.Priority;
import training.tracker.models.Task;
import training.tracker.models.TaskStatus;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskAssertions {

    public static void assertTask(
            Task task,
            String expectedKey,
            TaskStatus expectedStatus,
            Priority expectedPriority
    ) {
        assertAll(
                () -> assertEquals(expectedKey, task.key(), "key"),
                () -> assertEquals(expectedStatus, task.status(), "status"),
                () -> assertEquals(expectedPriority, task.priority(), "priority")
        );
    }
}
