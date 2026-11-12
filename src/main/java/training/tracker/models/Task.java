package training.tracker.models;

public record Task(
        String key,
        String title,
        TaskStatus status,
        Priority priority
) {

    // Модуль 5: метод с условием; в модуле 6 сравнение переведено на enum
    public boolean isOpen() {
        return status == TaskStatus.OPEN;
    }

    // Модуль 5: не меняет задачу, а возвращает новую с другим статусом
    public Task withStatus(TaskStatus newStatus) {
        return new Task(key, title, newStatus, priority);
    }
}
