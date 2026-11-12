package training.tracker.tests;

import org.junit.jupiter.api.Test;
import training.tracker.TaskFilter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskFilterTest {

    private final TaskFilter filter = new TaskFilter();

    @Test
    void shouldReturnOnlyTasksWithRequiredStatus() {
        List<String> statuses = List.of("OPEN", "DONE", "OPEN", "CANCELLED");

        List<String> result = filter.byStatus(statuses, "OPEN");

        assertEquals(List.of("OPEN", "OPEN"), result);
    }

    @Test
    void shouldReturnEmptyListWhenNoTasksWithRequiredStatus() {
        List<String> statuses = List.of("DONE", "CANCELLED");

        List<String> result = filter.byStatus(statuses, "OPEN");

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnAllElementsWhenAllStatusesMatch() {
        List<String> statuses = List.of("OPEN", "OPEN");

        List<String> result = filter.byStatus(statuses, "OPEN");

        assertEquals(statuses, result);
    }

    @Test
    void sourceListShouldNotChange() {
        List<String> statuses = new ArrayList<>(List.of("OPEN", "DONE", "OPEN"));
        List<String> expectedStatuses = List.of("OPEN", "DONE", "OPEN");

        filter.byStatus(statuses, "OPEN");

        assertEquals(expectedStatuses, statuses);
    }

    // Модуль 4, выходная задача: проверка только size() не замечала неверный результат того же размера — усилили до состава
    @Test
    void shouldReturnTwoOpenTasks() {
        List<String> statuses = List.of("OPEN", "DONE", "OPEN", "CANCELLED");

        List<String> result = filter.byStatus(statuses, "OPEN");

        assertEquals(List.of("OPEN", "OPEN"), result);
    }
}
