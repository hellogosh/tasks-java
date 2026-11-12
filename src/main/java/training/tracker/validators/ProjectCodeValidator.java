package training.tracker.validators;

public class ProjectCodeValidator {

    public boolean isValid(String code) {
        if (code == null) {
            return false;
        }

        int length = code.length();

        if (length < 2 || length > 10) {
            return false;
        }

        char firstChar = code.charAt(0);

        if (!isUpperLatinLetter(firstChar)) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            char currentChar = code.charAt(i);

            if (!isUpperLatinLetter(currentChar) && !isDigit(currentChar)) {
                return false;
            }
        }

        return true;
    }

    private boolean isUpperLatinLetter(char value) {
        return value >= 'A' && value <= 'Z';
    }

    private boolean isDigit(char value) {
        return value >= '0' && value <= '9';
    }
}
