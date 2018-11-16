/**
 * This is a pulse width modulating square, that is,
 * the pulse width changes over time over subsonic frequencies
 */
public class PWMSquare extends Square {
    private static final Triangle triangle = new Triangle(); // Used for the modulation
    private int divisor; // Number of periods the original wave must complete for one full pulse width modulation cycle
    /**
     * Creates a new pulse width modulated square wave.
     * @param d the number of periods needed for one full pulse width modulation period
     */
    public PWMSquare(int d) {
        super(0.5f);
        divisor = d;
    }
    /**
     * Creates a new pulse width modulated square wave with 256 being a good value for divisor.
     */
    public PWMSquare() {
        this(256);
    }
    @Override
    public float getSample(long us) {
        float pw = 0.5f+(triangle.getSample(us/divisor)+1.0f)/4.0f;
        setPulseWidth(pw);
        return super.getSample(us);
    }
}

