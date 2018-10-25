
public class Modulator extends Signal {
	private int hz;
	private Signal s;
	
	public Modulator (Signal s) {
		this.s = s;
	}
	
	public void setPeriod(int hz) {
		this.hz = hz;
	}
	
	public float getSample(long us) {
		long x = us * hz;
		return s.getSample(x); 
	}

}
