package training.tracker;

// Модуль 2: своя функция участника — цикл и условие
public class ProjectCodeStats {

    public int countDigits(String code) {
        if (code == null) {
            return 0;
        }

        int digits = 0;

        for (int i = 0; i < code.length(); i++) {
            char currentChar = code.charAt(i);

            if (currentChar >= '0' && currentChar <= '9') {
                digits++;
            }
        }

        return digits;
    }
}
