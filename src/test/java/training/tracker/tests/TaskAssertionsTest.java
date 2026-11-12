package training.tracker.tests;

import org.junit.jupiter.api.Test;
import training.tracker.assertions.TaskAssertJAssertions;
import training.tracker.assertions.TaskAssertions;
import training.tracker.builders.TaskBuilder;
import training.tracker.models.Priority;
import training.tracker.models.Task;
import training.tracker.models.TaskStatus;

import static training.tracker.assertions.TaskAssertions.assertTask;

class TaskAssertionsTest {

    @Test
    void shouldCheckTaskFields() {
        Task task = new TaskBuilder()
                .withKey("QA-15")
                .withStatus(TaskStatus.IN_PROGRESS)
                .withPriority(Priority.HIGH)
                .build();

        assertTask(task, "QA-15", TaskStatus.IN_PROGRESS, Priority.HIGH);
    }

    @Test
    void shouldCheckTaskFieldsWithReusableAssertion() {
        Task task = new TaskBuilder()
                .withKey("QA-15")
                .withStatus(TaskStatus.IN_PROGRESS)
                .withPriority(Priority.HIGH)
                .build();

        TaskAssertions.assertTask(task, "QA-15", TaskStatus.IN_PROGRESS, Priority.HIGH);
    }

    @Test
    void shouldCheckTaskFieldsWithAssertJ() {
        Task task = new TaskBuilder()
                .withKey("QA-15")
                .withStatus(TaskStatus.IN_PROGRESS)
                .withPriority(Priority.HIGH)
                .build();

        TaskAssertJAssertions.assertTask(task, "QA-15", TaskStatus.IN_PROGRESS, Priority.HIGH);
    }
}
