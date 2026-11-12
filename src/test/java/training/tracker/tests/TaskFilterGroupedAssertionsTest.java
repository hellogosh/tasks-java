package training.tracker.tests;

import org.junit.jupiter.api.Test;
import training.tracker.TaskFilter;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class TaskFilterGroupedAssertionsTest {

    private final TaskFilter filter = new TaskFilter();

    @Test
    void resultShouldContainOnlyRequiredStatus() {
        List<String> statuses = List.of("OPEN", "DONE", "OPEN");

        List<String> result = filter.byStatus(statuses, "OPEN");

        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertEquals("OPEN", result.get(0)),
                () -> assertEquals("OPEN", result.get(1)),
                () -> assertFalse(result.contains("DONE"))
        );
    }
}
