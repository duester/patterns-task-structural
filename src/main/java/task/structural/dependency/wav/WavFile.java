package task.structural.dependency.wav;

import lombok.Getter;
import lombok.Setter;
import task.structural.dependency.player.RawDataPlayer;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс по работе с файлами wav
 */
@Getter
public class WavFile {
    /**
     * данные
     */
    private final byte[] rawData;

    /**
     * частота дискретизации (байт/сек)
     */
    private final int bitrate;

    /**
     * текстовые метки
     */
    private final Map<String, String> tags;

    /**
     * текущая отсечка времени (сек)
     */
    @Setter
    private double currentTime = 0;

    /**
     * количество воспроизведений композиции
     */
    private int timesPlayed = 0;

    public WavFile(byte[] rawData, int bitrate, Map<String, String> tags) {
        this.rawData = rawData;
        this.bitrate = bitrate;
        this.tags = tags;
    }

    public WavFile(String fileName) {
        System.out.println("Загружаю файл wav: " + fileName + "...");

        this.rawData = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        this.bitrate = 10;
        this.tags = new HashMap<>() {{
            put("title", "Yesterday");
            put("interpreter", "The Beatles");
        }};
    }

    /**
     * общая продолжительность композиции (сек)
     */
    public double getTotalTime() {
        return rawData.length * 1.0 / bitrate;
    }

    public void playWav() {
        System.out.println("Текущая отсечка: " + currentTime + " с.");
        System.out.println("Проигрываю файл wav...");

        new RawDataPlayer(rawData).play();

        currentTime = getTotalTime();
        timesPlayed++;
        System.out.println("Проигрывание завершено.");
    }
}
