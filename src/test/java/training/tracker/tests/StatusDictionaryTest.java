package training.tracker.tests;

import org.junit.jupiter.api.Test;
import training.tracker.StatusDictionary;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatusDictionaryTest {

    private final StatusDictionary dictionary = new StatusDictionary();

    @Test
    void shouldReturnUniqueStatuses() {
        List<String> statuses = List.of("OPEN", "DONE", "OPEN", "CANCELLED");

        Set<String> result = dictionary.uniqueStatuses(statuses);

        assertEquals(Set.of("OPEN", "DONE", "CANCELLED"), result);
    }
}
