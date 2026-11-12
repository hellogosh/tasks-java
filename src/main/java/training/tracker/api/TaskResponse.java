package training.tracker.api;

public record TaskResponse (
    String key,
    String title,
    String status,
    String priority,
    String assigneeLogin
) {

}
