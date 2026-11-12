package training.tracker.tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import training.tracker.PermissionService;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PermissionServiceMethodSourceTest {

    @ParameterizedTest
    @MethodSource("editPermissionCases")
    void roleShouldHaveExpectedPermission(String role, boolean expectedAllowed) {
        PermissionService service = new PermissionService();

        boolean actualAllowed = service.canEditTask(role);

        assertEquals(expectedAllowed, actualAllowed);
    }

    static Stream<Arguments> editPermissionCases() {
        return Stream.of(
                Arguments.of("ADMIN", true),
                Arguments.of("MANAGER", true),
                Arguments.of("TESTER", true),
                Arguments.of("VIEWER", false),
                Arguments.of("UNKNOWN", false),
                Arguments.of(null, false)
        );
    }
}
