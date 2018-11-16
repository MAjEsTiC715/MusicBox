import java.util.LinkedList;

public class SequenceTrack extends Signal implements Tracked {
    private LinkedList<Note> sequenceTrack = new LinkedList<>();

    @Override
    public float getSample(long us) {
        return 0;
    }

    @Override
    public void add(long p, long d, int hz) {
        Note newNote = new Note(p, d, hz);
        sequenceTrack.add(newNote);
    }

    @Override
    public Note[] getAllAt(long us) {
        return new Note[0];
    }

    @Override
    public long getLength() {
        return 0;
    }

    @Override
    public void remove(Note n) {

    }

    @Override
    public void removeAllAt(long us) {

    }

    @Override
    public Instrument getInstrument() {
        return null;
    }
}
