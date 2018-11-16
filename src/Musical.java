/**
 * Things that play notes need to implement this so they can be configured to output musical notes.
 */
public interface Musical {
    /**
     * Changes the current musical note properties of the object.
     * @param d duration (in microseconds)
     * @param hz pitch (in hertz / times a second)
     */
    public abstract void setNote(long d,int hz);
    /**
     * Gets the instrument that this object will be playing the note with.
     * @return the instrument being used
     */
    public abstract Instrument getInstrument();
}
