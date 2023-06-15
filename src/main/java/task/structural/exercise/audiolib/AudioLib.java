package task.structural.exercise.audiolib;

import task.structural.dependency.fourier.FourierTransformer;
import task.structural.dependency.midi.MIDIFile;
import task.structural.dependency.midi.Note;
import task.structural.dependency.midi.Sequencer;
import task.structural.dependency.mp3.MP3File;
import task.structural.dependency.mp3.Tag;
import task.structural.dependency.wav.WavFile;
import task.structural.exception.MissingTagException;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Разрабатываемая универсальная библиотека с новыми методами
 * для работы с различными звуковыми форматами
 */
public class AudioLib {

    // 1. вычислить оставшееся время проигрывания (сек)

    public double getRemainingTime(MP3File file) {
        double currentTime = file.getCurrentTime();
        double totalTime = file.getTotalTime();
        return totalTime - currentTime;
    }

    public double getRemainingTime(WavFile file) {
        double currentTime = file.getCurrentTime();
        double totalTime = file.getTotalTime();
        return totalTime - currentTime;
    }

    public double getRemainingTime(MIDIFile file) {
        long currentTimeMS = file.getCurrentTimeMS();
        long totalTimeMS = file.getLength();
        return (totalTimeMS - currentTimeMS) / 1000.0;
    }

    // 2. проиграть композицию несколько раз подряд

    public void playInLoop(MP3File file, int times) {
        for (int i = 0; i < times; i++) {
            file.setCurrentTime(0.0);
            file.playMP3();
        }
    }

    public void playInLoop(WavFile file, int times) {
        for (int i = 0; i < times; i++) {
            file.setCurrentTime(0.0);
            file.playWav();
        }
    }

    public void playInLoop(MIDIFile file, Sequencer sequencer, int times) {
        for (int i = 0; i < times; i++) {
            file.setCurrentTimeMS(0);
            file.playMIDI(sequencer);
        }
    }

    // 3. статистика по частотному анализу

    public FrequencyAnalysisStatistics analyzeFrequencies(MP3File file, FourierTransformer transformer) {
        byte[] decodedData = file.decodeData();
        Map<Double, Integer> frequencies = transformer.getFrequencies(decodedData);
        return analyzeFrequencies(frequencies);
    }

    public FrequencyAnalysisStatistics analyzeFrequencies(WavFile file, FourierTransformer transformer) {
        byte[] decodedData = file.getRawData();
        Map<Double, Integer> frequencies = transformer.getFrequencies(decodedData);
        return analyzeFrequencies(frequencies);
    }

    public FrequencyAnalysisStatistics analyzeFrequencies(MIDIFile file) {
        Stream<Note> noteStream = file.getTracks().stream().flatMap(t -> t.getNotes().stream());
        Map<Double, Integer> frequencies = noteStream.collect(
                Collectors.toMap(Note::getPitch, b -> 1, (amount, i) -> amount + 1));
        return analyzeFrequencies(frequencies);
    }

    private FrequencyAnalysisStatistics analyzeFrequencies(Map<Double, Integer> frequencies) {
        if (frequencies.isEmpty()) {
            return null;
        }
        double minimal = frequencies.keySet().stream().min(Comparator.comparing(Function.identity())).get();
        double maximal = frequencies.keySet().stream().max(Comparator.comparing(Function.identity())).get();
        double rarest = frequencies.entrySet().stream().min(Map.Entry.comparingByValue()).get().getKey();
        double mostFrequent = frequencies.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
        return new FrequencyAnalysisStatistics(minimal, maximal, rarest, mostFrequent);
    }

    public record FrequencyAnalysisStatistics(double minimal, double maximal, double rarest, double mostFrequent) {
    }

    // 4. получить название композиции

    public String getTitle(MP3File file) {
        Tag titleTage = file.getTags().stream()
                .filter(tag -> tag.getCode().equals("title")).findFirst()
                .orElseThrow(() -> {
                    throw new MissingTagException("title");
                });
        return titleTage.getValue();
    }

    public String getTitle(WavFile file) {
        Map<String, String> tags = file.getTags();
        String title = tags.get("title");
        if (title == null) {
            throw new MissingTagException("title");
        }
        return title;
    }

    public String getTitle(MIDIFile file) {
        String title = file.getTitle();
        if (title == null) {
            throw new MissingTagException("title");
        }
        return title;
    }
}
