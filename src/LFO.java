/**
 * LFO is a Low Frequency Oscillator,
 * this signal will take two source waves and a divisor (number of periods that must be completed for one period of oscillation)
 * to blend between the two signals for a variety of sounds.
 * In other terms, you can make your signals go WUB WUB
 */
public class LFO extends Signal {
    private static final Triangle triangle = new Triangle(); // Used for oscillation
    private Signal signalA; // First signal
    private Signal signalB; // Second signal
    private int divisor; // Divisor (number of source waveform periods to complete)
    /**
     * Creates a new LFO that blends two signals together back and forth.
     * @param s1 first source
     * @param s2 second source
     * @param d divisor (number of periods to complete for one oscillation)
     */
    public LFO(Signal s1,Signal s2,int d) {
        signalA = s1;
        signalB = s2;
        divisor = d;
    }
    @Override
    public float getSample(long us) {
        float pw = (triangle.getSample(us/divisor)+1.0f)/2.0f; // 0.0 - 1.0
        float s1 = signalA.getSample(us);
        float s2 = signalB.getSample(us);
        return s1*(1.0f-pw)+s2*pw; // Interpolate from signal 1 to signal 2
    }
}
