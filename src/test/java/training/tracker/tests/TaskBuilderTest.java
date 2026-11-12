package training.tracker.tests;

import org.junit.jupiter.api.Test;
import training.tracker.builders.TaskBuilder;
import training.tracker.models.Priority;
import training.tracker.models.Task;
import training.tracker.models.TaskStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Модуль 6: значения builder по умолчанию — тест покраснеет честно, если их поменять
class TaskBuilderTest {

    @Test
    void builderWithoutSettingsShouldCreateDefaultTask() {
        Task task = new TaskBuilder().build();

        assertEquals(new Task("QA-1", "Default task", TaskStatus.OPEN, Priority.MEDIUM), task);
    }
}
