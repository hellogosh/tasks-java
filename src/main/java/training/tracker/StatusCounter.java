package training.tracker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatusCounter {

    public Map<String, Integer> countByStatus(List<String> statuses) {
        Map<String, Integer> result = new HashMap<>();

        for (String status : statuses) {
            int currentCount = result.getOrDefault(status, 0);
            result.put(status, currentCount + 1);
        }

        return result;
    }
}
