package task.structural.dependency.midi;

/**
 * MIDI-файлы не хранят непосредственно звуковую информацию, а содержат набор нот/команд,
 * для проигрывания которых нужно специальное устройство - секвенсер
 */
public class Sequencer {

    public byte[] transformToBytes(MIDIFile file) {
        System.out.println("Преобразую midi в байты...");
        return new byte[]{0, 0, 0, 0, 0};
    }
}
