package training.tracker.tests;

import org.junit.jupiter.api.Test;
import training.tracker.validators.ProjectCodeValidator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProjectCodeValidatorTest {

    @Test
    void validProjectCodeShouldPassValidation() {
        ProjectCodeValidator validator = new ProjectCodeValidator();

        assertTrue(validator.isValid("QA"));
    }

    @Test
    void projectCodeWithDigitsShouldPassValidation() {
        ProjectCodeValidator validator = new ProjectCodeValidator();

        assertTrue(validator.isValid("QA2026"));
    }

    @Test
    void nullCodeShouldBeInvalid() {
        ProjectCodeValidator validator = new ProjectCodeValidator();

        boolean valid = validator.isValid(null);

        assertFalse(valid, "null не может быть кодом проекта");
    }

    @Test
    void codeWithDigitAfterFirstLetterShouldBeValid() {
        ProjectCodeValidator validator = new ProjectCodeValidator();

        boolean valid = validator.isValid("QA1");

        assertTrue(valid);
    }

    // Изолирует правило символов: первая буква правильная, нарушена только вторая
    @Test
    void codeWithLowercaseLetterAfterFirstShouldBeInvalid() {
        ProjectCodeValidator validator = new ProjectCodeValidator();

        boolean valid = validator.isValid("Qa");

        assertFalse(valid, "Код проекта должен состоять из заглавных латинских букв и цифр");
    }

    @Test
    void codeWithUnderscoreShouldBeInvalid() {
        ProjectCodeValidator validator = new ProjectCodeValidator();

        boolean valid = validator.isValid("Q_A");

        assertFalse(valid, "Подчёркивание не входит в допустимые символы");
    }

    // Дополнительный случай: "qa" нарушает сразу два условия и не заменяет "Qa"
    @Test
    void lowercaseCodeShouldBeInvalid() {
        ProjectCodeValidator validator = new ProjectCodeValidator();

        boolean valid = validator.isValid("qa");

        assertFalse(valid);
    }

    @Test
    void codeWithHyphenShouldBeInvalid() {
        ProjectCodeValidator validator = new ProjectCodeValidator();

        boolean valid = validator.isValid("QA-1");

        assertFalse(valid);
    }

    @Test
    void codeStartingWithDigitShouldBeInvalid() {
        ProjectCodeValidator validator = new ProjectCodeValidator();

        boolean valid = validator.isValid("1QA");

        assertFalse(valid, "Код проекта не может начинаться с цифры");
    }

    // Модуль 2, выходная задача: позитивный тест с нулём ловит ошибку value > '0' в isDigit
    @Test
    void codeWithZeroShouldBeValid() {
        ProjectCodeValidator validator = new ProjectCodeValidator();

        boolean valid = validator.isValid("QA0");

        assertTrue(valid, "Цифра 0 допустима в коде проекта");
    }
}
