/**
 * Instrument is used to create a specific type of Note given an Envelopes parameters (t,a,slv,r)
 * Instruments that play notes need to implement Musical so they can be configured to output musical notes.
 * extends Signal which provides getSample (this is used to get the point of the constructed Envelope note (Signal i)
 */
public class Instrument extends Signal implements Musical {
    private String name;
    private Signal s;
    private Signal i; // Instrument note with Envelope applied
    private long attack;
    private long decay;
    private long release;
    private float sustainLevel;
    /**
     * Constructs Instrument setting the given parameters.
     * @param n the name of the Instrument being used
     * @param t the Signal (Waveform) that is specific to that of Instrument
     * @param a the Attack time (microseconds)
     * @param d the Decay time (microseconds)
     * @param slv the Sustain Level (a y-point between 0.0 and 1.0)
     * @param r the Release time (microseconds)
     */
    public Instrument (String n, Signal t, long a, long d, float slv, long r) {
        this.name = n;
        this.s = t;
        this.attack = a;
        this.decay = d;
        this.sustainLevel = slv;
        this.release = r;
    }
    @Override
    public float getSample(long us) {
        float samplePoint = i.getSample(us);
        return samplePoint;
    }

    @Override
    public void setNote(long d, int hz) {
        Modulator m = new Modulator(s);
        m.setPeriod(hz);
        Envelope e = new Envelope(m, getAttack(), getDecay(), getSustainLevel(), getRelease());
        e.setDuration(d);
        this.i = e;
    }

    @Override
    public Instrument getInstrument() {
        return this;
    }

    public String getName() { return name; }

    public long getAttack() { return attack; }

    public long getDecay() { return decay; }

    public float getSustainLevel() { return sustainLevel; }

    public long getRelease() { return release; }
}
