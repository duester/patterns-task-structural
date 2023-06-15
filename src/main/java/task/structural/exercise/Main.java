package task.structural.exercise;

import task.structural.dependency.midi.MIDIFile;
import task.structural.dependency.midi.Sequencer;
import task.structural.dependency.mp3.MP3File;
import task.structural.dependency.wav.WavFile;

public class Main {
    public static void main(String[] args) {
        MP3File mp3 = new MP3File("test.mp3");
        mp3.playMP3();
        System.out.println("");

        WavFile wav = new WavFile("test.wav");
        wav.playWav();
        System.out.println("");

        MIDIFile midi = new MIDIFile("test.midi");
        Sequencer sequencer = new Sequencer();
        midi.playMIDI(sequencer);
    }
}
