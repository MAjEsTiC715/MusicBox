/**
 * A square wave is at its lowest outside of its wave duty cycle,
 * when in the wave duty cycle, it is at its highest.
 * This wave duty cycle will last for a proportion of the period defined by pulse width.
 *
 * If a square wave has pulse width set to 50%,
 * then half of the period is spent LOW and the other half spent HIGH
 */
public class Square extends Signal {
    private float pulseWidth = 0.5f; // 50% wave duty by default
    /**
     * Creates a square wave with a pulse width 50% that of the period
     */
    public float Square(float pw) {
        return pw;
    }
    /**
     * Creates a square wave with custom pulse width as a percentage of the period
     * @param pw desired pulse width
     */
    public Square(float pw) {
        pulseWidth = pw;
    }
    public void setPulseWidth(float pw) {
        pulseWidth = pw;
    }
    @Override
    public float getSample(long us) {
        float percentage = 1000000.0f - (1000000.0f * pulseWidth);
        us = us%1000000;
        if (us >= percentage){
            return 1.0f;
        }
        else {
            return -1.0f;
        }
        // Implement this for a square wave that has a period of 1 second
        // Parameter us is given to you in microseconds
    }

}