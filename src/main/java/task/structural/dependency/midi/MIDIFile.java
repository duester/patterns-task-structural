package task.structural.dependency.midi;

import lombok.Getter;
import lombok.Setter;
import task.structural.dependency.player.RawDataPlayer;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * Класс по работе с файлами midi
 */
@Getter
public class MIDIFile {
    /**
     * дорожки музыкальных инструментов
     */
    private final List<Track> tracks;

    /**
     * название композиции
     */
    private final String title;

    /**
     * исполнитель
     */
    private final String interpreter;

    /**
     * текущая отсечка времени (мс)
     */
    @Setter
    private long currentTimeMS = 0;

    /**
     * количество воспроизведений композиции
     */
    private int timesPlayed = 0;

    public MIDIFile(List<Track> tracks, String title, String interpreter) {
        this.tracks = tracks;
        this.title = title;
        this.interpreter = interpreter;
    }

    public MIDIFile(String fileName) {
        System.out.println("Загружаю файл midi: " + fileName + "...");

        this.tracks = List.of(
                new Track(List.of(
                        new Note(0, 440.0, 500),
                        new Note(500, 660.0, 1000),
                        new Note(1700, 330.0, 500)
                )),
                new Track(List.of(
                        new Note(200, 880.0, 1000),
                        new Note(1200, 440.0, 500)
                ))
        );
        this.title = "Yesterday";
        this.interpreter = "The Beatles";
    }

    /**
     * общая длина композиции (мс)
     */
    public long getLength() {
        return tracks.stream().map(Track::getLength)
                .max(Comparator.comparing(Function.identity())).orElse(0L);
    }

    public void playMIDI(Sequencer sequencer) {
        System.out.println("Текущая отсечка: " + currentTimeMS + " мс.");
        System.out.println("Проигрываю файл midi...");

        byte[] rawData = sequencer.transformToBytes(this);
        new RawDataPlayer(rawData).play();

        currentTimeMS = getLength();
        timesPlayed++;
        System.out.println("Проигрывание завершено.");
    }
}
