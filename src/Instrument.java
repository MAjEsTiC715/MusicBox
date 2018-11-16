
public class Instrument extends Signal implements Musical {
    private String name;
    private Signal t;
    private long attack;
    private long decay;
    private long release;
    private float sustainLevel;
    private SequenceTrack st;

    public Instrument (String n, Signal t, long a, long d, float slv, long r) {
        this.name = n;
        this.t = t;
        this.attack = a;
        this.decay = d;
        this.sustainLevel = slv;
        this.release = r;
    }
    @Override
    public float getSample(long us) {
        return 0;
    }

    @Override
    public void setNote(long d, int hz) {
        st.add(0, d, hz);
    }

    @Override
    public Instrument getInstrument() {
        return null;
    }

    public String getName() { return name; }

    public long getAttack() { return attack; }

    public long getDecay() { return decay; }

    public float getSustainLevel() { return sustainLevel; }

    public long getRelease() { return release; }
}
