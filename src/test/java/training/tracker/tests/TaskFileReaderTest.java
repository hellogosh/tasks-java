package training.tracker.tests;

import org.junit.jupiter.api.Test;
import training.tracker.TaskService;
import training.tracker.models.Task;
import training.tracker.models.TaskStatus;
import training.tracker.services.TaskFileReader;
import training.tracker.services.TaskLineParser;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskFileReaderTest {

    @Test
    void shouldReadTaskLinesFromResourceFile() throws Exception {
        TaskFileReader reader = new TaskFileReader();
        Path path = resourcePath("tasks.txt");

        List<String> lines = reader.readLines(path);

        assertEquals(3, lines.size());
        assertEquals("QA-1;Write tests;OPEN;HIGH", lines.getFirst());
    }

    @Test
    void openTasksFromFileShouldBeFound() throws Exception {
        TaskFileReader reader = new TaskFileReader();
        TaskLineParser parser = new TaskLineParser();
        List<Task> tasks = new ArrayList<>();

        for (String line : reader.readLines(resourcePath("tasks.txt"))) {
            tasks.add(parser.parse(line));
        }
        List<Task> openTasks = new TaskService().tasksWithStatus(tasks, TaskStatus.OPEN);

        assertEquals(List.of("QA-1", "QA-3"),
                openTasks.stream().map(task -> task.key()).toList());
    }

    private Path resourcePath(String fileName) throws URISyntaxException {
        URL url = getClass().getClassLoader().getResource(fileName);
        if (url == null) {
            throw new IllegalStateException("Test resource not found: " + fileName);
        }
        return Path.of(url.toURI());
    }
}
