package training.tracker.tests;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Модуль 4, задание 1: строки и даты; даты заданы явно, без LocalDate.now()
class StringsAndDatesTest {

    @Test
    void trimShouldRemoveSpacesAroundKey() {
        assertEquals("QA-1", " QA-1 ".trim());
    }

    @Test
    void spacesOnlyStringShouldBeBlankButNotEmpty() {
        String spaces = "   ";

        assertAll(
                () -> assertTrue(spaces.isBlank(), "isBlank"),
                () -> assertFalse(spaces.isEmpty(), "isEmpty")
        );
    }

    @Test
    void keyShouldStartWithProjectPrefix() {
        String key = "QA-123";

        assertAll(
                () -> assertTrue(key.startsWith("QA-"), "префикс"),
                () -> assertEquals(6, key.length(), "длина")
        );
    }

    @Test
    void plusMonthShouldMoveEndOfJanuaryToEndOfFebruary() {
        LocalDate date = LocalDate.of(2026, 1, 31).plusMonths(1);

        assertEquals(LocalDate.of(2026, 2, 28), date);
    }

    @Test
    void dueDateShouldBeThreeDaysAfterCreation() {
        LocalDate created = LocalDate.of(2026, 10, 29);

        LocalDate due = created.plusDays(3);

        assertAll(
                () -> assertEquals(LocalDate.of(2026, 11, 1), due),
                () -> assertTrue(due.isAfter(created), "срок позже даты создания")
        );
    }
}
