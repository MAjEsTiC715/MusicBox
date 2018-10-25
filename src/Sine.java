/**
 * Returns points that form a Sine Wave
 * */
public class Sine extends Signal {
	
	public float getSample(long us) {
		us = us % 1000000;
		double u = Math.toRadians((float)us / 1000.0f);
		//double k = Math.PI * 2;
		double point = Math.sin(u);
		return (float)point;
	}

}
