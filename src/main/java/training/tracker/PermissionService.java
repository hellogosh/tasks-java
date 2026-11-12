package training.tracker;

public class PermissionService {

    public boolean canEditTask(String role) {
        if (role == null) {
            return false;
        }

        return switch (role) {
            case "ADMIN", "MANAGER", "TESTER" -> true;
            case "VIEWER" -> false;
            default -> false;
        };
    }
}
