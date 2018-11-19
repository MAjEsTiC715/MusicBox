import java.util.LinkedList;

public class SequenceTrack extends Signal implements Tracked {
    private long durationOfTrack;
    private Instrument ins; //Instrument note
    private LinkedList<Note> track = new LinkedList<>();


    public SequenceTrack(Instrument i) {
        this.ins = i;
    }

    @Override
    public float getSample(long us) {
        Note[] allNotes = getAllAt(us);
        for (Note sampleNote : allNotes) {
            sampleNote.configure(ins);
            return sampleNote.getSample(ins, us);
        }
        return 0;
    }

    @Override
    public void add(long p, long d, int hz) {
        Note newNote = new Note(p, d, hz);
        track.add(newNote);
        if ((d + p) > durationOfTrack) {
            this.durationOfTrack = d + p;
        }
    }

    @Override
    public Note[] getAllAt(long us) {
        Note[] sampleArray = track.toArray(new Note[track.size()]);
        Note[] emptyArray = new Note[0];
        int i = 0;
        for (Note sample : track) {
            if(sample.isInside(us) == true) {
                sampleArray[i] = sample;
                i++;
            }
        }
        if (sampleArray[0] == null) {
            return emptyArray;
        }
        return sampleArray;
    }

    @Override
    public long getLength() {
        return durationOfTrack;
    }

    @Override
    public void remove(Note n) {
        track.removeFirstOccurrence(n);
    }

    @Override
    public void removeAllAt(long us) {
        Note[] noteArray = track.toArray(new Note[track.size()]);
        for (Note sample : noteArray) {
            if(sample.isInside(us) == false) {
                remove(sample);
            }
        }
    }

    @Override
    public Instrument getInstrument() {
        return new Razorback();
    }
}
