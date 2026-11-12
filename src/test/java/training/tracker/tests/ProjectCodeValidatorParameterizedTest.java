package training.tracker.tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import training.tracker.validators.ProjectCodeValidator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProjectCodeValidatorParameterizedTest {

    @ParameterizedTest
    @CsvSource({
            "'', false",
            "Q, false",
            "QA, true",
            "PROJECT123, true",
            "PROJECT1234, false"
    })
    void codeLengthShouldMatchValidationRule(String code, boolean expectedValid) {
        ProjectCodeValidator validator = new ProjectCodeValidator();

        boolean actualValid = validator.isValid(code);

        assertEquals(expectedValid, actualValid);
    }
}
