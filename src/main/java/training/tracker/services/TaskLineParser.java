package training.tracker.services;

import training.tracker.models.Priority;
import training.tracker.models.Task;
import training.tracker.models.TaskStatus;

public class TaskLineParser {

    public Task parse(String line) {
        // -1: не отбрасывать пустые поля в конце, иначе "QA-1;Title;OPEN;HIGH;" прошла бы как 4 поля
        String[] parts = line.split(";", -1);

        if (parts.length != 4) {
            throw new IllegalArgumentException(
                    "Task line must have 4 fields, but got " + parts.length + ": " + line);
        }

        return new Task(
                parts[0],
                parts[1],
                TaskStatus.valueOf(parts[2]),
                Priority.valueOf(parts[3])
        );
    }
}
