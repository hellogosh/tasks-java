package training.tracker.tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import training.tracker.validators.EstimateValidator;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Модуль 3: два теста разминки (0, 160) свёрнуты в @CsvSource, добавлены -1, 1, 161
class EstimateValidatorTest {

    @ParameterizedTest
    @CsvSource({
            "-1, false",
            "0, false",
            "1, true",
            "160, true",
            "161, false"
    })
    void estimateShouldMatchAllowedRange(int hours, boolean expectedValid) {
        EstimateValidator validator = new EstimateValidator();

        boolean actualValid = validator.isValid(hours);

        assertEquals(expectedValid, actualValid, "Оценка " + hours + " ч");
    }
}
