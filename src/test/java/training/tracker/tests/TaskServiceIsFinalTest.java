package training.tracker.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import training.tracker.TaskService;
import training.tracker.models.TaskStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Модуль 6: switch по enum; JUnit сам превращает "DONE" в TaskStatus.DONE
class TaskServiceIsFinalTest {

    @ParameterizedTest
    @CsvSource({"OPEN, false", "IN_PROGRESS, false", "DONE, true", "CANCELLED, true"})
    void statusShouldBeFinalOnlyForDoneAndCancelled(TaskStatus status, boolean expected) {
        Assertions.assertEquals(expected, new TaskService().isFinal(status));
    }
}
