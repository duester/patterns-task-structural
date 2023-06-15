package task.structural.dependency.midi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Отдельная нота
 */
@AllArgsConstructor
@Getter
@ToString
public class Note {
    /**
     * отсечка времени начала звучания ноты (мс)
     */
    private final long startTime;
    /**
     * частота звука (Гц)
     */
    private final double pitch;
    /**
     * продолжительность звучания (мс)
     */
    private final long duration;
}
