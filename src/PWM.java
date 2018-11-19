/**
 * Pulse-width modulated square instrument.
 * @author Torin Adamson
 */
public class PWM extends Instrument {
	/**
	 * Creates a new pulse width modulated instrument.
	 */
	public PWM() {
		super("pwm",new PWMSquare(),10000,10000,0.7f,10000);
	}
}