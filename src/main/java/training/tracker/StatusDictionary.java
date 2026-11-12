package training.tracker;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StatusDictionary {

    public Set<String> uniqueStatuses(List<String> statuses) {
        return new HashSet<>(statuses);
    }
}
