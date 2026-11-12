package training.tracker.assertions;


import training.tracker.api.TaskResponse;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TaskResponseAssertions {
    public static void assertMatchesContract(TaskResponse task) {
        assertThat(task.key())
                .as("key в %s", task)
                .isNotBlank();
        assertThat(task.status())
                .as("key в %s", task)
                .isIn("TaskResponseContract.ALLOWED_STATUSES");
    }

}
