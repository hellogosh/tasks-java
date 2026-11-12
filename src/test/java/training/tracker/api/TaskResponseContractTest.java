package training.tracker.api;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskResponseContractTest {
    private final TaskResponseContract contract = new TaskResponseContract();

    @Test
    void unknowStatusShouldBeInvalid() {
        TaskResponse response = new TaskResponse("QA-1", "Write tests", "UNKNOWN", "HIGH", "ivanov");
        assertFalse(contract.isValid(response));
    }
    @Test
    void nullStatusShouldBeInvalid(){
        TaskResponse response = new TaskResponse("QA-1", "Write tests", null, "HIGH", "ivanov");
        assertFalse(contract.isValid(response));

    }
    @Test
    void validResponseShouldMatchContract() {
        TaskResponse response = new TaskResponse("QA-1", "Write tests", "OPEN", "HIGH", "ivanov");
        assertTrue(contract.isValid(response));
    }

    @ParameterizedTest
    @ValueSource(strings = {"OPEN", "IN_PROGRESS", "DONE", "CANCELLED"})
    void allowedStatusShouldMatchContract(String status) {
        TaskResponse response = new TaskResponse("QA-1", "Write tests", status, "HIGH", "ivanov");
        assertTrue(contract.isValid(response), status + " должен быть допустимым статусом");
    }

    @ParameterizedTest
    @MethodSource("invalidResponces")
    @Tag("regression")
    void responseWithoutRequiredFieldShouldNotMatchContract(TaskResponse response) {
        assertFalse(contract.isValid(response), "ответ должен быть невалиден" + response);
    }

    static Stream<TaskResponse> invalidResponces() {
        return Stream.of(new TaskResponse(null, "Write tests", "OPEN", "HIGH", "ivanov"),
                new TaskResponse( " ", "Write tests", "OPEN", "HIGH", "ivanov"),
                new TaskResponse( "", "Write tests", "UNKNOWN", "HIGH", "ivanov"),
                new TaskResponse( "", "Write tests", "OPEN", "BLOCKER", "ivanov"),
                new TaskResponse( null, "Write tests", "OPEN", "HIGH", "ivanov")

        );
    }



}
