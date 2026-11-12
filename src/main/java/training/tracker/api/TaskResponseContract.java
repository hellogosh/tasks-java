package training.tracker.api;

import java.util.Set;

public class TaskResponseContract {
    public static final Set<String> ALLOWED_STATUSES = Set.of(
            "OPEN",
            "IN_PROGRESS",
            "DONE",
            "CANCELLED"
    );

    public static final Set<String> ALLOWED_PRIORITIES = Set.of(
            "CRITICAL",
            "HIGH",
            "MEDIUM",
            "LOW"
    );

    public boolean isValid(TaskResponse response) {
        if(response == null) {
            return false;
        }
        return hasText(response.key())
                && hasText(response.title())
                && hasText(response.status())
                && ALLOWED_STATUSES.contains(response.status())
                && hasText(response.priority())
                && ALLOWED_PRIORITIES.contains(response.priority())
                && hasText(response.assigneeLogin());
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();

    }
}
