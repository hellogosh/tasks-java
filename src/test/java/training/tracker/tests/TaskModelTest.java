package training.tracker.tests;

import org.junit.jupiter.api.Test;
import training.tracker.models.Priority;
import training.tracker.models.Task;
import training.tracker.models.TaskStatus;
import training.tracker.models.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Модуль 5: методы модели, свой record и сравнение объектов
class TaskModelTest {

    @Test
    void openTaskShouldBeOpen() {
        assertTrue(task("QA-1", TaskStatus.OPEN).isOpen());
    }

    @Test
    void doneTaskShouldNotBeOpen() {
        assertFalse(task("QA-2", TaskStatus.DONE).isOpen());
    }

    @Test
    void withStatusShouldReturnNewTaskAndKeepOriginal() {
        Task original = task("QA-1", TaskStatus.OPEN);

        Task changed = original.withStatus(TaskStatus.DONE);

        assertAll(
                () -> assertEquals(new Task("QA-1", "Write tests", TaskStatus.DONE, Priority.HIGH), changed),
                () -> assertEquals(TaskStatus.OPEN, original.status(), "исходная задача не изменилась")
        );
    }

    @Test
    void shouldCountTwoOpenTasksOfThree() {
        List<Task> tasks = List.of(
                task("QA-1", TaskStatus.OPEN),
                task("QA-2", TaskStatus.DONE),
                task("QA-3", TaskStatus.OPEN));

        assertEquals(2, countOpen(tasks));
    }

    @Test
    void emptyListShouldHaveNoOpenTasks() {
        assertEquals(0, countOpen(List.of()));
    }

    // Record сравнивает по содержимому: тест «ловушки equals» из задания 0 зелёный
    @Test
    void tasksWithSameFieldsShouldBeEqual() {
        assertEquals(task("QA-1", TaskStatus.OPEN), task("QA-1", TaskStatus.OPEN));
    }

    @Test
    void usersWithSameFieldsShouldBeEqual() {
        assertEquals(new User("ivanov", "Ivan Ivanov"), new User("ivanov", "Ivan Ivanov"));
    }

    @Test
    void usersWithDifferentLoginShouldNotBeEqual() {
        assertNotEquals(new User("ivanov", "Ivan Ivanov"), new User("petrov", "Ivan Ivanov"));
    }

    private int countOpen(List<Task> tasks) {
        int count = 0;

        for (Task task : tasks) {
            if (task.isOpen()) {
                count++;
            }
        }

        return count;
    }

    private Task task(String key, TaskStatus status) {
        return new Task(key, "Write tests", status, Priority.HIGH);
    }
}
