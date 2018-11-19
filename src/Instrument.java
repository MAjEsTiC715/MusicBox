
public class Instrument extends Signal implements Musical {
    private String name;
    private Signal s;
    private Signal i; // Instrument note
    private long attack;
    private long decay;
    private long release;
    private float sustainLevel;

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

    public long getAttack() { return attack; }

    public long getDecay() { return decay; }

    public float getSustainLevel() { return sustainLevel; }

    public long getRelease() { return release; }
}
