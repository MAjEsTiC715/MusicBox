/**
 * Data structures that are put onto tracks of time should implement these methods.
 */
public interface Tracked {
    /**
     * Adds the note to this track.
     * @param p the absolute position in time to add the new note (microseconds)
     * @param d the duration of the new note (microseconds)
     * @param hz the pitch of the new note (in hertz / times a second)
     */
    public abstract void add(long p,long d,int hz);
    /**
     * Gets all of the notes that would be playing at this specific absolute point in time (microseconds).
     * Notes added to a track could potentially overlap in time, so that is why this method could return any or no notes.
     * If there are no notes active at this point in time, this method MUST return an empty array (length 0) instead of null.
     * @param us the absolute point in time (microseconds)
     * @return an array of all the notes that are active during that time
     */
    public abstract Note[] getAllAt(long us);
    /**
     * Gets the total length of this entire track, in microseconds.
     * After this point in time, it is impossible for any notes to be active.
     * Adding new notes MIGHT increase the length of the track.
     * @return the total length of this track (in microseconds)
     */
    public abstract long getLength();
    /**
     * Removes the given instance of node from the tracked sequence.
     * @param n the note to remove
     */
    public abstract void remove(Note n);
    /**
     * Removes all the notes that are active during the given absolute point in time (in microseconds).
     * @param us the absolute point in time to remove all notes (in microseconds)
     */
    public abstract void removeAllAt(long us);
    /**
     * Gets the current instrument assigned to this track.
     * @return the current instrument
     */
    public abstract Instrument getInstrument();
}

