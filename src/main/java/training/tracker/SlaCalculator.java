package training.tracker;

public class SlaCalculator {

    public int responseTimeHours(String priority) {
        if ("CRITICAL".equals(priority)) {
            return 2;
        }

        if ("HIGH".equals(priority)) {
            return 8;
        }

        if ("MEDIUM".equals(priority)) {
            return 24;
        }

        if ("LOW".equals(priority)) {
            return 72;
        }

        return -1;
    }
}
