package training.tracker.tests;

import org.junit.jupiter.api.Test;
import training.tracker.TaskService;
import training.tracker.builders.TaskBuilder;
import training.tracker.models.Task;
import training.tracker.models.TaskStatus;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskServiceTest {

    @Test
    void shouldReturnTasksWithRequiredStatus() {
        TaskService service = new TaskService();
        Task openTask = new TaskBuilder().withKey("QA-1").withStatus(TaskStatus.OPEN).build();
        Task doneTask = new TaskBuilder().withKey("QA-2").withStatus(TaskStatus.DONE).build();
        Task anotherOpenTask = new TaskBuilder().withKey("QA-3").withStatus(TaskStatus.OPEN).build();

        List<Task> result = service.tasksWithStatus(
                List.of(openTask, doneTask, anotherOpenTask),
                TaskStatus.OPEN
        );

        assertEquals(List.of(openTask, anotherOpenTask), result);
    }

    @Test
    void shouldReturnEmptyListWhenNoTasksMatchStatus() {
        TaskService service = new TaskService();

        List<Task> result = service.tasksWithStatus(
                List.of(new TaskBuilder().withKey("QA-2").withStatus(TaskStatus.DONE).build()),
                TaskStatus.OPEN
        );

        assertTrue(result.isEmpty());
    }

    // smoke опирается на проверку состава: неверный список той же длины тест не пройдёт
    @Test
    void shouldReturnOnlyOpenTasks() {
        TaskService service = new TaskService();

        List<Task> result = service.tasksWithStatus(
                List.of(
                        new TaskBuilder().withKey("QA-1").withStatus(TaskStatus.OPEN).build(),
                        new TaskBuilder().withKey("QA-2").withStatus(TaskStatus.DONE).build(),
                        new TaskBuilder().withKey("QA-3").withStatus(TaskStatus.OPEN).build()
                ),
                TaskStatus.OPEN
        );

        assertThat(result)
                .extracting(Task::key)
                .containsExactly("QA-1", "QA-3");
    }

    @Test
    void sourceListShouldNotChange() {
        TaskService service = new TaskService();
        List<Task> tasks = new ArrayList<>(List.of(
                new TaskBuilder().withKey("QA-1").withStatus(TaskStatus.OPEN).build(),
                new TaskBuilder().withKey("QA-2").withStatus(TaskStatus.DONE).build()
        ));
        List<Task> expectedTasks = List.copyOf(tasks);

        service.tasksWithStatus(tasks, TaskStatus.OPEN);

        assertEquals(expectedTasks, tasks);
    }

    // Модуль 5, выходная задача: фильтр сохраняет исходный порядок задач
    @Test
    void openTasksShouldKeepSourceOrder() {
        TaskService service = new TaskService();
        Task firstOpen = new TaskBuilder().withKey("QA-1").withStatus(TaskStatus.OPEN).build();
        Task doneTask = new TaskBuilder().withKey("QA-2").withStatus(TaskStatus.DONE).build();
        Task secondOpen = new TaskBuilder().withKey("QA-3").withStatus(TaskStatus.OPEN).build();

        List<Task> result = service.tasksWithStatus(List.of(firstOpen, doneTask, secondOpen), TaskStatus.OPEN);

        assertEquals(List.of(firstOpen, secondOpen), result);
    }
}
