/**
 * Razorback signal instrument, blends between a sawtooth and a triangle wave using LFO.
 * @author Torin Adamson
 */
public class Razorback extends Instrument {
	/**
	 * Creates the razorback instrument.
	 */
	public Razorback() {
		super("razorback",new LFO(new Sawtooth(),new Triangle(),4),10000,10000,0.6f,10000);
	}
}