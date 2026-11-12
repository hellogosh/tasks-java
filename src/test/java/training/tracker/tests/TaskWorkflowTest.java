package training.tracker.tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import training.tracker.builders.TaskBuilder;
import training.tracker.models.Task;
import training.tracker.models.TaskStatus;
import training.tracker.services.TaskWorkflow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskWorkflowTest {

    @Test
    @Tag("smoke")
    void openTaskShouldBeStarted() {
        TaskWorkflow workflow = new TaskWorkflow();
        Task task = new TaskBuilder().withStatus(TaskStatus.OPEN).build();

        Task startedTask = workflow.start(task);

        assertEquals(TaskStatus.IN_PROGRESS, startedTask.status());
    }

    @Test
    void taskWithTitleShouldBeClosed() {
        TaskWorkflow workflow = new TaskWorkflow();
        Task task = new TaskBuilder()
                .withTitle("Write tests")
                .withStatus(TaskStatus.IN_PROGRESS)
                .build();

        Task closedTask = workflow.close(task);

        assertEquals(TaskStatus.DONE, closedTask.status());
    }

    @Test
    void taskWithoutTitleShouldNotBeClosed() {
        TaskWorkflow workflow = new TaskWorkflow();
        Task task = new TaskBuilder()
                .withTitle(null)
                .build();

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> workflow.close(task));

        assertEquals("Task title is required",
                exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t"})
    void taskWithBlankTitleShouldNotBeClosed(String title) {
        TaskWorkflow workflow = new TaskWorkflow();
        Task task = new TaskBuilder()
                .withTitle(title)
                .build();

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> workflow.close(task));

        assertEquals("Task title is required",
                exception.getMessage());
    }

    @Test
    void doneTaskShouldNotBeStarted() {
        TaskWorkflow workflow = new TaskWorkflow();
        Task task = new TaskBuilder()
                .withStatus(TaskStatus.DONE)
                .build();

        IllegalStateException exception =
                assertThrows(
                        IllegalStateException.class,
                        () -> workflow.start(task));

        assertEquals("Only OPEN task can be started",
                exception.getMessage());
    }
}
