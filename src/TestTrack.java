/**
 * Tests implementation of sequence
 */
public class TestTrack {
    public static final long TIME_PER_SCALE_NOTE = 450000; // When playing all the notes on the piano, this is the duration for each note
    /**
     * Scale the piano really fast and then play a song
     * @param args (ignored)
     */
    public static void main(String[] args) {

        // Test instrument
        //Razorback ins = new Razorback();
        Signal s = new Square(0.5f);
        Instrument ins = new Instrument("square",s,10000,50000,0.5f,10000);

        // Create piano scale track and play it
        SequenceTrack st = new SequenceTrack(ins);
        for(long i = 0;i < 13;i++) {
            int hz = Note.midiToPitch((int)(i+24+12*3)); // Notes starting from C4 to C5
            st.add(i*TIME_PER_SCALE_NOTE,TIME_PER_SCALE_NOTE,hz);
        }
        WavePlay.play(st,st.getLength());

        // Remove notes to create a natural minor scale on C
        // There will be short pauses if we remove notes
        for(long i = 0;i < 13;i++) {
            if(i == 1 || i == 4 || i == 6 || i == 9 || i == 11)
                st.removeAllAt(i*TIME_PER_SCALE_NOTE);
        }
        WavePlay.play(st,st.getLength());

        // Bunch of chords
        // That's music speak for 3 or more notes playing at the same time
        SequenceTrack st2 = new SequenceTrack(ins);
        int octave = 24+12*3+5;
        long ct = 0;
        st2.add(ct,TIME_PER_SCALE_NOTE,Note.midiToPitch(octave));
        st2.add(ct,TIME_PER_SCALE_NOTE,Note.midiToPitch(octave+4));
        st2.add(ct,TIME_PER_SCALE_NOTE,Note.midiToPitch(octave+7));
        ct += TIME_PER_SCALE_NOTE;
        st2.add(ct,TIME_PER_SCALE_NOTE,Note.midiToPitch(octave+9));
        st2.add(ct,TIME_PER_SCALE_NOTE,Note.midiToPitch(octave+12));
        st2.add(ct,TIME_PER_SCALE_NOTE,Note.midiToPitch(octave+16));
        ct += TIME_PER_SCALE_NOTE;
        st2.add(ct,TIME_PER_SCALE_NOTE,Note.midiToPitch(octave+7));
        st2.add(ct,TIME_PER_SCALE_NOTE,Note.midiToPitch(octave+11));
        st2.add(ct,TIME_PER_SCALE_NOTE,Note.midiToPitch(octave+14));
        ct += TIME_PER_SCALE_NOTE;
        st2.add(ct,TIME_PER_SCALE_NOTE,Note.midiToPitch(octave+5));
        st2.add(ct,TIME_PER_SCALE_NOTE,Note.midiToPitch(octave+9));
        st2.add(ct,TIME_PER_SCALE_NOTE,Note.midiToPitch(octave+12));
        ct += TIME_PER_SCALE_NOTE;
        st2.add(ct,TIME_PER_SCALE_NOTE,Note.midiToPitch(octave));
        st2.add(ct,TIME_PER_SCALE_NOTE,Note.midiToPitch(octave+5));
        st2.add(ct,TIME_PER_SCALE_NOTE,Note.midiToPitch(octave+7));
        ct += TIME_PER_SCALE_NOTE;
        st2.add(ct,TIME_PER_SCALE_NOTE,Note.midiToPitch(octave));
        st2.add(ct,TIME_PER_SCALE_NOTE,Note.midiToPitch(octave+2));
        st2.add(ct,TIME_PER_SCALE_NOTE,Note.midiToPitch(octave+5));
        ct += TIME_PER_SCALE_NOTE;
        st2.add(ct,TIME_PER_SCALE_NOTE*2,Note.midiToPitch(octave+2));
        st2.add(ct,TIME_PER_SCALE_NOTE*2,Note.midiToPitch(octave+7));
        st2.add(ct,TIME_PER_SCALE_NOTE*2,Note.midiToPitch(octave+11));
        WavePlay.play(st2,st2.getLength());
    }
}

