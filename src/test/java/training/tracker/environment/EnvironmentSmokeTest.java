package training.tracker.environment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Проверка окружения перед курсом, а не учебный тест.
 * Если ./gradlew test показывает "1 test completed" и тест зелёный — JUnit, Gradle и JDK готовы.
 */
class EnvironmentSmokeTest {

    @Test
    void junitShouldRunOnJava25() {
        int javaVersion = Runtime.version().feature();

        assertTrue(javaVersion >= 25,
                "Тесты запущены на Java " + javaVersion + ", а курсу нужна Java 25. "
                        + "Проверьте JDK проекта и Gradle JVM в настройках IDE");
    }
}
