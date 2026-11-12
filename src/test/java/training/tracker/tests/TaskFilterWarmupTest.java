package training.tracker.tests;

import org.junit.jupiter.api.Test;
import training.tracker.TaskFilter;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskFilterWarmupTest {

    private final TaskFilter filter = new TaskFilter();

    @Test
    void shouldReturnOnlyOpenStatuses() {
        List<String> statuses = List.of("OPEN", "DONE", "OPEN");

        List<String> result = filter.byStatus(statuses, "OPEN");

        assertEquals(List.of("OPEN", "OPEN"), result);
    }

    @Test
    void shouldReturnEmptyListWhenNoStatusMatches() {
        List<String> statuses = List.of("OPEN", "DONE", "OPEN");

        List<String> result = filter.byStatus(statuses, "CANCELLED");

        assertTrue(result.isEmpty());
    }
}
