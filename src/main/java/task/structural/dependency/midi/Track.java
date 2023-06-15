package task.structural.dependency.midi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * Отдельная дорожка музыкального инструмента
 */
@AllArgsConstructor
@Getter
@ToString
public class Track {
    /**
     * ноты, составляющие мелодию музыкальной дорожки
     */
    private final List<Note> notes;

    void addNote(Note note) {
        notes.add(note);
    }

    /**
     * продолжительность звучания дорожки
     */
    public long getLength() {
        Note lastNote = notes.get(notes.size() - 1);
        return lastNote.getStartTime() + lastNote.getDuration();
    }
}
