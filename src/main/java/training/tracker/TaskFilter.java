package training.tracker;

import java.util.ArrayList;
import java.util.List;

public class TaskFilter {

    public List<String> byStatus(List<String> taskStatuses, String requiredStatus) {
        List<String> result = new ArrayList<>();

        for (String status : taskStatuses) {
            if (status.equals(requiredStatus)) {
                result.add(status);
            }
        }

        return result;
    }
}
