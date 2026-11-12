package training.tracker.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import training.tracker.PermissionService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PermissionServiceTest {

    private PermissionService service;

    // Модуль 3: общая подготовка — JUnit вызывает метод перед каждым тестом, каждый тест получает свежий сервис
    @BeforeEach
    void createService() {
        service = new PermissionService();
    }

    // Парный метод для уборки после каждого теста; здесь убирать нечего, поэтому только обнуляем поле
    @AfterEach
    void clearService() {
        service = null;
    }

    @ParameterizedTest
    @ValueSource(strings = {"ADMIN", "MANAGER", "TESTER"})
    void roleShouldEditTask(String role) {
        boolean allowed = service.canEditTask(role);

        assertTrue(allowed, role + " должен иметь право редактировать задачу");
    }

    @ParameterizedTest
    @CsvSource({
            "ADMIN, true",
            "MANAGER, true",
            "TESTER, true",
            "VIEWER, false",
            "UNKNOWN, false"
    })
    void roleShouldHaveExpectedEditPermission(String role, boolean expectedAllowed) {
        boolean actualAllowed = service.canEditTask(role);

        assertEquals(expectedAllowed, actualAllowed);
    }

    // Модуль 3, выходная задача: значение с пробелом в начале — в одинарных кавычках
    @ParameterizedTest
    @CsvSource({
            "admin, false",
            "' ADMIN', false",
            "TESTER, true",
            "VIEWER, false"
    })
    void roleShouldMatchNewTable(String role, boolean expected) {
        boolean actual = service.canEditTask(role);

        assertEquals(expected, actual, "Роль [" + role + "]");
    }

    // Модуль 4, разминка: порядок параметров — как столбцы таблицы, пустая строка — ''
    @ParameterizedTest
    @CsvSource({
            "ADMIN, true",
            "VIEWER, false",
            "'', false"
    })
    void editPermissionShouldMatchTable(String role, boolean expectedAllowed) {
        assertEquals(expectedAllowed, service.canEditTask(role));
    }
}
