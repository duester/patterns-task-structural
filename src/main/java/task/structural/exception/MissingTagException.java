package task.structural.exception;

public class MissingTagException extends RuntimeException {
    public MissingTagException(String tag) {
        super("Тег " + tag + " не найден");
    }
}
