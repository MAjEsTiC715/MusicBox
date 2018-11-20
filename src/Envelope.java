/**
 * Controls the volume over a short period of time
 * Envelope wraps around the values of Modulators getSample()
 * */
public class Envelope extends Signal {
    private long attackTime;
    private long decayTime;
    private float sustainLevel;
    private long releaseTime;
    private long sustainPeriod;
    private Signal s;
    /**
     * Constructs Instrument setting the given parameters.
     * @param s the Signal (Waveform) that is specific to that of a Note
     * @param a the Attack time (microseconds)
     * @param d the Decay time (microseconds)
     * @param l the Sustain Level (a y-point between 0.0 and 1.0)
     * @param r the Release time (microseconds)
     */
    public Envelope(Signal s, long a, long d, float l, long r) {
        this.s = s;
        this.attackTime = a;
        this.decayTime = d;
        if (l > 1 | l < 0) {
            this.sustainLevel = 0;
            System.out.println("Sustain Level set to 0 due to it being over 1.0 or negative");
        }
        this.sustainLevel = l;
        this.releaseTime = r;
    }

    public long getAttackTime() { return attackTime; }

    public long getDecayTime() { return decayTime; }

    public float getSustainLevel() { return sustainLevel; }

    public long getReleaseTime() { return releaseTime; }

    public float getSustainPeriod() { return sustainPeriod; }

    /** Wraps Envelope values around Signal s values */
    @Override
    public float getSample(long us) {
        float point = (float)createEnvelope(us) * s.getSample(us);
        return point;
    }

    /** Calculates the Sustain Period based on duration */
    public void setDuration(long duration) {
        duration -= getAttackTime();
        duration -= getDecayTime();
        duration -= getReleaseTime();
        if (duration < 0) {
            String err = String.format("Note too short; Needs to exceed: %.2f Seconds", (float)((getAttackTime()+getDecayTime()+getReleaseTime()) / 1000000.00f));
            throw new IllegalArgumentException(err);
        }
        this.sustainPeriod = duration;
    }

    /** Calculates each point in the envelope based on what us it at that time
     *  Sets all MICROSECOND values to SECONDS for easier calculations */
    private double createEnvelope(long us) {
        double y = 0;
        double x = us / 1000000.0;
        double a = getAttackTime() / 1000000.0;
        double d = getDecayTime() / 1000000.0;
        float sl = getSustainLevel();
        double r = getReleaseTime() / 1000000.0;
        double sp = getSustainPeriod() / 1000000.0;

        if (us <= getAttackTime()) {
            y = (1.0 / a) * x;  // Activate Attack (Linear Line)
        }
        if (us > getAttackTime() && us <= (getDecayTime() + getAttackTime())) {  // Activate Decay (Linear Line)
            y = (-(1.0 - sl) / d) * (x - a) + 1.0;
        }
        if (us > (getAttackTime() + getDecayTime()) && us <= (getDecayTime() + getAttackTime() + getSustainPeriod())) {  // Activate Sustain Period when Sustain Level is met (Constant)
            y = sl;
        }
        if (us > (getDecayTime() + getAttackTime() + getSustainPeriod()) && us <= (getDecayTime() + getAttackTime() + getSustainPeriod() + getReleaseTime())) {  // Activate release time (Linear Line)
            y = (-sl / r) * (x - a - d - sp) + sl;
        }
        return y;
    }
}
