package training.tracker.assertions;

import training.tracker.models.Priority;
import training.tracker.models.Task;
import training.tracker.models.TaskStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class TaskAssertJAssertions {

    public static void assertTask(
            Task task,
            String expectedKey,
            TaskStatus expectedStatus,
            Priority expectedPriority
    ) {
        assertThat(task.key()).as("key").isEqualTo(expectedKey);
        assertThat(task.status()).as("status").isEqualTo(expectedStatus);
        assertThat(task.priority()).as("priority").isEqualTo(expectedPriority);
    }
}
