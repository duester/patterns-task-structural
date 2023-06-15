package task.structural.exercise;

import org.junit.jupiter.api.Test;
import task.structural.dependency.fourier.FourierTransformer;
import task.structural.dependency.midi.MIDIFile;
import task.structural.dependency.midi.Note;
import task.structural.dependency.midi.Sequencer;
import task.structural.dependency.midi.Track;
import task.structural.dependency.mp3.MP3File;
import task.structural.dependency.mp3.Tag;
import task.structural.dependency.wav.WavFile;
import task.structural.exercise.audiolib.AudioLib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AudioLibTest {

    @Test
    void getRemainingTime_MP3() {
        MP3File file = createMP3();
        // длина несжатых данных = 6
        // => длина сжатых данных = 3
        // => общая продолжительность = 3 / 1 = 3 с
        file.setCurrentTime(2.0);
        double remainingTime = new AudioLib().getRemainingTime(file);
        assertEquals(1.0, remainingTime);
    }

    @Test
    void getRemainingTime_Wav() {
        WavFile file = createWav();
        // длина несжатых данных = 6
        // => общая продолжительность = 6 / 1 = 6 с
        file.setCurrentTime(2.0);
        double remainingTime = new AudioLib().getRemainingTime(file);
        assertEquals(4.0, remainingTime);
    }

    @Test
    void getRemainingTime_MIDI() {
        MIDIFile file = createMIDI();
        // общая продолжительность = 2000 + 500 = 2500 мс = 2,5 с
        file.setCurrentTimeMS(2000);
        double remainingTime = new AudioLib().getRemainingTime(file);
        assertEquals(0.5, remainingTime);
    }

    @Test
    void playInLoop_MP3() {
        MP3File file = createMP3();
        new AudioLib().playInLoop(file, 2);
        assertEquals(2, file.getTimesPlayed());
    }

    @Test
    void playInLoop_Wav() {
        WavFile file = createWav();
        new AudioLib().playInLoop(file, 2);
        assertEquals(2, file.getTimesPlayed());
    }

    @Test
    void playInLoop_MIDI() {
        MIDIFile file = createMIDI();
        Sequencer sequencer = new Sequencer();
        new AudioLib().playInLoop(file, sequencer, 2);
        assertEquals(2, file.getTimesPlayed());
    }

    @Test
    void analyzeFrequencies_MP3() {
        MP3File file = createMP3();
        FourierTransformer transformer = createFourierTransformer();
        AudioLib.FrequencyAnalysisStatistics stat = new AudioLib().analyzeFrequencies(file, transformer);
        assertEquals(100.0, stat.minimal());
        assertEquals(800.0, stat.maximal());
        assertEquals(440.0, stat.rarest());
        assertEquals(800.0, stat.mostFrequent());
    }

    @Test
    void analyzeFrequencies_Wav() {
        WavFile file = createWav();
        FourierTransformer transformer = createFourierTransformer();
        AudioLib.FrequencyAnalysisStatistics stat = new AudioLib().analyzeFrequencies(file, transformer);
        assertEquals(100.0, stat.minimal());
        assertEquals(800.0, stat.maximal());
        assertEquals(440.0, stat.rarest());
        assertEquals(800.0, stat.mostFrequent());
    }

    @Test
    void analyzeFrequencies_MIDI() {
        MIDIFile file = createMIDI();
        AudioLib.FrequencyAnalysisStatistics stat = new AudioLib().analyzeFrequencies(file);
        assertEquals(100.0, stat.minimal());
        assertEquals(440.0, stat.maximal());
        assertEquals(440.0, stat.rarest());
        assertEquals(100.0, stat.mostFrequent());
    }

    @Test
    void getTitle_MP3() {
        MP3File file = createMP3();
        String title = new AudioLib().getTitle(file);
        assertEquals("Test", title);
    }

    @Test
    void getTitle_Wav() {
        WavFile file = createWav();
        String title = new AudioLib().getTitle(file);
        assertEquals("Test", title);
    }

    @Test
    void getTitle_MIDI() {
        MIDIFile file = createMIDI();
        String title = new AudioLib().getTitle(file);
        assertEquals("Test", title);
    }

    // ------

    private MP3File createMP3() {
        byte[] data = new byte[]{0, 0, 0, 0, 0, 0};
        List<Tag> tags = List.of(
                new Tag("title", "Test")
        );
        return new MP3File(data, 1, tags);
    }

    private WavFile createWav() {
        byte[] data = new byte[]{0, 0, 0, 0, 0, 0};
        Map<String, String> tags = new HashMap<>() {{
            put("title", "Test");
        }};
        return new WavFile(data, 1, tags);
    }

    private MIDIFile createMIDI() {
        List<Track> tracks = List.of(
                new Track(List.of(
                        new Note(500, 100.0, 500),
                        new Note(1000, 440.0, 1000),
                        new Note(2000, 100.0, 500)
                ))
        );
        return new MIDIFile(tracks, "Test", "Test");
    }

    private FourierTransformer createFourierTransformer() {
        return new FourierTransformer() {
            @Override
            public Map<Double, Integer> getFrequencies(byte[] data) {
                return new HashMap<>() {{
                    put(100.0, 2);
                    put(440.0, 1);
                    put(800.0, 3);
                }};
            }
        };
    }
}
