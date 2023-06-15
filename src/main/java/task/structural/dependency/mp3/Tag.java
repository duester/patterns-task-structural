package task.structural.dependency.mp3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Метка с информацией о композиции
 */
@AllArgsConstructor
@Getter
@ToString
public class Tag {
    /**
     * код (например, "title")
     */
    private final String code;
    /**
     * значение (например, "Yesterday")
     */
    private final String value;
}
