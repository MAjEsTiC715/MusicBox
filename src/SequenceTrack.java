import java.util.ArrayList;
import java.util.LinkedList;
/**
 * SequenceTrack will wrap a LinkedList of Note Objects created using Object Instrument.
 * Implements Tracked Interface which provides methods: add, getAllAt, getLength, remove, removeAllAt, and getInstrument.
 * extends Signal which provides the method getSample;
 */
public class SequenceTrack extends Signal implements Tracked {
    private long durationOfTrack; // length of entire track
    private Instrument ins; //Instrument note
    private LinkedList<Note> track = new LinkedList<>();

    /**
     * Adds the note to this track.
     * @param i The instrument being used to construct the notes in track
     */
    public SequenceTrack(Instrument i) {
        this.ins = i;
    }

    @Override
    public float getSample(long us) {  // Linear time O(n)
        Note[] allNotes = getAllAt(us);
        float amp = 0.0f;
        for (Note sampleNote : allNotes) {
            sampleNote.configure(ins);
            amp += sampleNote.getSample(ins, us);
        }
        return amp;
    }

    @Override
    public void add(long p, long d, int hz) {  // Constant time O(1)
        Note newNote = new Note(p, d, hz);
        track.add(newNote);
        if ((d + p) > durationOfTrack) {
            this.durationOfTrack = d + p;
        }
    }

    @Override
    public Note[] getAllAt(long us) {  // Linear time O(n)
        ArrayList<Note> sampleArray = new ArrayList<>();
        Note[] allNotes = track.toArray(new Note[track.size()]);
        Note[] emptyArray = new Note[0];
        for (Note sample : allNotes) {
            if(sample.isInside(us) == true) {
                sampleArray.add(sample);
            }
        }
        if (sampleArray.equals(null)) {
            System.out.println("returned null");
            return emptyArray;
        }
        Note[] returnNotes = new Note[sampleArray.size()];
        for (int x = 0; x < sampleArray.size(); x++) {
            returnNotes[x] = sampleArray.get(x);
        }
        return returnNotes;
    }

    @Override
    public long getLength() {  // Constant time O(1)
        return durationOfTrack;
    }

    @Override
    public void remove(Note n) {  // Constant time O(1)
        track.removeFirstOccurrence(n);
    }

    @Override
    public void removeAllAt(long us) {  // Linear time O(n)
        Note[] noteArray = track.toArray(new Note[track.size()]);
        for (Note sample : noteArray) {
            if(sample.isInside(us) == true) {
                remove(sample);
            }
        }
    }

    @Override
    public Instrument getInstrument() {  // Constant time O(1)
        return ins;
    }
}
