/**
 * A periodic signal of any sort with a range between -1.0 and 1.0 and a period of 1 second
 * Recall that 1 second is 1,000,000 microseconds 
 */
public abstract class Signal {
	public static final int MICROSECONDS_IN_A_SECOND = 1000000; // 1 second is 1,000,000 microseconds
	public static final float HIGH = 1.0f; // Signal highest
	public static final float LOW = -1.0f; // Signal lowest
	/**
	 * Samples the signal at time us where us is in microseconds
	 * All signals have a period of 1 second
	 * @param us time (in microseconds) to sample the signal
	 * @return the sample at that location in time
	 */
	public abstract float getSample(long us);
}