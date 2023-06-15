package task.structural.dependency.player;

import lombok.AllArgsConstructor;

/**
 * Класс для проигрывания несжатых звуковых данных
 */
@AllArgsConstructor
public class RawDataPlayer {
    private final byte[] rawData;

    public void play() {
        System.out.println("  Ля-ля-ля... Тыц-тыц...");
    }
}
