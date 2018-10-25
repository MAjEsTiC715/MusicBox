/**
 * A sawtooth wave rises linearly from its lowest point to its highest during one period.
 */
public class Sawtooth extends Signal {
	@Override
	public float getSample(long us) {
	    us = us % 1000000;
	    float point = (float)us;
	    point /= 1000000.0f;
	    point *= 2.0f;
	    point -= 1.0f;
	    return point;
	    // Implement this for a sawtooth wave that has a period of 1 second
	    // Parameter us is given to you in microseconds
	}
}