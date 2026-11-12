package training.tracker.tests;

import org.junit.jupiter.api.Test;
import training.tracker.TaskFilter;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TaskFilterAssertJTest {

    private final TaskFilter filter = new TaskFilter();

    @Test
    void shouldReturnOnlyTasksWithRequiredStatus() {
        List<String> statuses = List.of("OPEN", "DONE", "OPEN", "CANCELLED");

        List<String> result = filter.byStatus(statuses, "OPEN");

        assertThat(result)
                .containsExactly("OPEN", "OPEN")
                .doesNotContain("DONE", "CANCELLED");
    }
}
