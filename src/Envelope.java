
public class Envelope extends Signal {
    private long attackTime;
    private long decayTime;
    private float sustainLevel;
    private long releaseTime;
    private long sustainPeriod;
    private Signal s;

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

    public long getAttackTime() {
        return attackTime;
    }

    public long getDecayTime() {
        return decayTime;
    }

    public float getSustainLevel() {
        return sustainLevel;
    }

    public long getReleaseTime() {
        return releaseTime;
    }

    public float getSustainPeriod() {
        return sustainPeriod;
    }

    @Override
    public float getSample(long us) {
        float point = (float)createEnvelope(us) * s.getSample(us);
        return point;
    }

    public void setDuration(long duration) throws IllegalArgumentException {
        duration -= getAttackTime();
        duration -= getDecayTime();
        duration -= getReleaseTime();
        if (duration < 0) {
            throw new IllegalArgumentException("Duration is Negative");
        }
        this.sustainPeriod = duration;
    }

    private double createEnvelope(long us) {
        double y = 0;
        double x = us / 1000000.0;
        double a = getAttackTime() / 1000000.0;
        double d = getDecayTime() / 1000000.0;
        float sl = getSustainLevel();
        double r = getReleaseTime() / 1000000.0;
        double sp = getSustainPeriod() / 1000000.0;

        if (us <= getAttackTime()) {
            y = (1.0 / a) * x;  // Activate Attack
        }
        if (us > getAttackTime() && us <= (getDecayTime() + getAttackTime())) {  // Activate Decay
            y = (-(1.0 - sl) / d) * (x - a) + 1.0;
        }
        if (us > (getAttackTime() + getDecayTime()) && us <= (getDecayTime() + getAttackTime() + getSustainPeriod())) {  // Activate Sustain Period when Sustain Level is met
            y = sl;
        }
        if (us > (getDecayTime() + getAttackTime() + getSustainPeriod()) && us <= (getDecayTime() + getAttackTime() + getSustainPeriod() + getReleaseTime())) {  // Activate release time
            y = (-sl / r) * (x - a - d - sp) + sl;
        }
        return y;
    }
}
