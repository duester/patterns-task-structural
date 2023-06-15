package task.structural.dependency.mp3;

import lombok.Getter;
import lombok.Setter;
import task.structural.dependency.player.RawDataPlayer;

import java.util.Arrays;
import java.util.List;

/**
 * Класс по работе с файлами mp3
 */
@Getter
public class MP3File {
    /**
     * запакованные данные
     */
    private final byte[] encodedData;

    /**
     * частота дискретизации (байт/сек)
     */
    private final int bitrate;

    /**
     * текстовые метки
     */
    private final List<Tag> tags;

    /**
     * текущая отсечка времени (сек)
     */
    @Setter
    private double currentTime = 0;

    /**
     * количество воспроизведений композиции
     */
    private int timesPlayed = 0;

    public MP3File(byte[] rawData, int bitrate, List<Tag> tags) {
        this.encodedData = encodeData(rawData);
        this.bitrate = bitrate;
        this.tags = tags;
    }

    public MP3File(String fileName) {
        System.out.println("Загружаю файл mp3: " + fileName + "...");

        this.encodedData = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        this.bitrate = 10;
        this.tags = List.of(
                new Tag("title", "Yesterday"),
                new Tag("interpreter", "The Beatles")
        );
    }

    /**
     * упаковка несжатых данных
     */
    private byte[] encodeData(byte[] rawData) {
        System.out.println("Запаковываю данные...");
        return Arrays.copyOf(rawData, rawData.length / 2);
    }

    /**
     * распаковка сжатых данных
     */
    public byte[] decodeData() {
        System.out.println("Распаковываю данные...");
        return Arrays.copyOf(encodedData, encodedData.length * 2);
    }

    /**
     * общая продолжительность композиции (сек)
     */
    public double getTotalTime() {
        return encodedData.length * 1.0 / bitrate;
    }

    public void playMP3() {
        System.out.println("Текущая отсечка: " + currentTime + " с.");
        System.out.println("Проигрываю файл mp3...");

        byte[] rawData = decodeData();
        new RawDataPlayer(rawData).play();

        currentTime = getTotalTime();
        timesPlayed++;
        System.out.println("Проигрывание завершено.");
    }
}
