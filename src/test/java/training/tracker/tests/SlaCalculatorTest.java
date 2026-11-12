package training.tracker.tests;

import org.junit.jupiter.api.Test;
import training.tracker.SlaCalculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SlaCalculatorTest {

    @Test
    void criticalTaskShouldHaveTwoHoursResponseTime() {
        SlaCalculator calculator = new SlaCalculator();

        int actualHours = calculator.responseTimeHours("CRITICAL");

        assertEquals(
                2,
                actualHours,
                "CRITICAL задача должна иметь срок реакции 2 часа"
        );
    }

    @Test
    void highTaskShouldHaveEightHoursResponseTime() {
        SlaCalculator calculator = new SlaCalculator();

        int actualHours = calculator.responseTimeHours("HIGH");

        assertEquals(8, actualHours);
    }

    @Test
    void mediumTaskShouldHaveTwentyFourHoursResponseTime() {
        SlaCalculator calculator = new SlaCalculator();

        int actualHours = calculator.responseTimeHours("MEDIUM");

        assertEquals(24, actualHours);
    }

    @Test
    void lowTaskShouldHaveSeventyTwoHoursResponseTime() {
        SlaCalculator calculator = new SlaCalculator();

        int actualHours = calculator.responseTimeHours("LOW");

        assertEquals(72, actualHours);
    }

    @Test
    void unknownPriorityShouldReturnMinusOne() {
        SlaCalculator calculator = new SlaCalculator();

        int actualHours = calculator.responseTimeHours("BLOCKER");

        assertEquals(-1, actualHours);
    }

    @Test
    void lowercaseCriticalShouldReturnMinusOne() {
        SlaCalculator calculator = new SlaCalculator();

        int actualHours = calculator.responseTimeHours("critical");

        assertEquals(-1, actualHours);
    }
}
