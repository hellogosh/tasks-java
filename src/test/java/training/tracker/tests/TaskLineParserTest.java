package training.tracker.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import training.tracker.models.Priority;
import training.tracker.models.Task;
import training.tracker.models.TaskStatus;
import training.tracker.services.TaskLineParser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskLineParserTest {

    private final TaskLineParser parser = new TaskLineParser();

    @Test
    void shouldParseTaskLine() {
        Task task = parser.parse("QA-1;Write tests;OPEN;HIGH");

        assertEquals(new Task("QA-1", "Write tests", TaskStatus.OPEN, Priority.HIGH), task);
    }

    @Test
    void lineWithThreeFieldsShouldNotBeParsed() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse("QA-1;Write tests;OPEN"));

        assertEquals("Task line must have 4 fields, but got 3: QA-1;Write tests;OPEN",
                exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "QA-1;Write tests;OPEN;HIGH;extra",
            "QA-1;Write tests;OPEN;HIGH;",
            "QA-1;Write tests;;OPEN;HIGH"
    })
    void lineWithExtraFieldsShouldNotBeParsed(String line) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse(line));

        assertTrue(exception.getMessage().startsWith("Task line must have 4 fields"));
    }
}
