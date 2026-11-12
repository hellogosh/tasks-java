package training.tracker.validators;

// Модуль 3: своя функция участника — оценка задачи в часах от 1 до 160 включительно
public class EstimateValidator {

    public boolean isValid(int hours) {
        return hours > 0 && hours <= 160;
    }
}
