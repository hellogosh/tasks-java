package training.tracker.tests;

import org.junit.jupiter.api.Test;
import training.tracker.StatusCounter;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StatusCounterTest {

    private final StatusCounter counter = new StatusCounter();

    @Test
    void shouldCountTasksByStatus() {
        List<String> statuses = List.of("OPEN", "DONE", "OPEN");

        Map<String, Integer> result = counter.countByStatus(statuses);

        assertEquals(Map.of("OPEN", 2, "DONE", 1), result);
    }

    @Test
    void shouldReturnEmptyMapForEmptyStatusList() {
        Map<String, Integer> result = counter.countByStatus(List.of());

        assertTrue(result.isEmpty());
    }

    @Test
    void missingStatusShouldNotAppearInMap() {
        List<String> statuses = List.of("OPEN", "DONE", "OPEN");

        Map<String, Integer> result = counter.countByStatus(statuses);

        assertThat(result).doesNotContainKey("CANCELLED");
    }
}
