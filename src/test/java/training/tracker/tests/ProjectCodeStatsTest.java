package training.tracker.tests;

import org.junit.jupiter.api.Test;
import training.tracker.ProjectCodeStats;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Модуль 2: тесты своей функции с циклом
class ProjectCodeStatsTest {

    @Test
    void codeWithoutDigitsShouldHaveZeroDigits() {
        ProjectCodeStats stats = new ProjectCodeStats();

        int digits = stats.countDigits("QA");

        assertEquals(0, digits);
    }

    @Test
    void codeWithFourDigitsShouldHaveFourDigits() {
        ProjectCodeStats stats = new ProjectCodeStats();

        int digits = stats.countDigits("QA2026");

        assertEquals(4, digits);
    }

    @Test
    void emptyCodeShouldHaveZeroDigits() {
        ProjectCodeStats stats = new ProjectCodeStats();

        int digits = stats.countDigits("");

        assertEquals(0, digits);
    }

    @Test
    void nullCodeShouldHaveZeroDigits() {
        ProjectCodeStats stats = new ProjectCodeStats();

        int digits = stats.countDigits(null);

        assertEquals(0, digits, "Для отсутствующего кода цифр нет");
    }
}
