/**
 * A single note.
 */
public class Note {
    public static final int[] MIDI_21 = {28,29,31,33,35,37,39,41,44,46,49,52,55,58,62,65,69,73,78,
            82,87,93,98,104,110,117,123,131,139,147,156,165,175,185,
            196,208,220,233,247,262,277,294,311,330,349,370,392,415,
            440,466,494,523,554,587,622,659,698,740,784,831,880,932,
            988,1047,1109,1175,1245,1319,1397,1480,1568,1661,1760,1865,
            1976,2093,2217,2349,2489,2637,2794,2960,3136,3322,3520,3729,3951,4186};
    public static final int A = 440; // The note A above middle C, often used to tune things
    private long position; // Absolute position in time
    private long duration; // Length of time the note is to be held down
    private int pitch; // Frequency to play at
    /**
     * Converts from the MIDI key code to a pitch (in hertz / times a second).
     * @param mk MIDI code
     * @return the pitch
     */
    public static int midiToPitch(int mk) {
        mk -= 21;
        if(mk < 0)
            return 0; // Not defined but are valid MIDI codes
        if(mk >= 88)
            return 0; // Too high
        return MIDI_21[mk];
    }
    /**
     * Creates a new musical note.
     * @param p position in time (microseconds)
     * @param d duration (microseconds)
     * @param hz pitch (in hertz / times a second)
     */
    public Note(long p,long d,int hz) {
        position = p;
        duration = d;
        pitch = hz;
    }
    /**
     * Checks to see if this specific absolute point in time (in microseconds) is inside of this note's duration
     * @param us the absolute point of time (in microseconds)
     * @return if this point in time is inside the playing of this note
     */
    public boolean isInside(long us) {
        us = toLocalTime(us);
        return !(us < 0 || us >= duration);
    }
    /**
     * Given the musical object, configures the object to play this note.
     * @param mm the musical object to configure
     */
    public void configure(Musical mm) {
        mm.setNote(duration,pitch);
    }
    /**
     * Gets the absolute position in time this note starts at.
     * @return the position in time
     */
    public long getPosition() {
        return position;
    }
    /**
     * Converts from an absolute position in time to a time local to this note.
     * This gives you a time that you can then feed directly into an envelope/instrument to get the correct sample.
     * The returned time won't mean anything if its outside the life of this note, however.
     * @param us absolute point in time (microseconds)
     * @return local time with 0 microseconds being the start of the note
     */
    public long toLocalTime(long us) {
        return us-position;
    }
    /**
     * This gets a sample from the note, accepting absolute times instead of those local to source signal.
     * @param s source signal
     * @param us absolute position in time (microseconds)
     * @return the sample at that time
     */
    public float getSample(Signal s,long us) {
        us = toLocalTime(us);
        return s.getSample(us);
    }
}

