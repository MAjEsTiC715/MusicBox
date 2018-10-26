import java.util.Random;
/**
 * Generates random points within the limit of -1 to 1 on the y-axis
 * */
public class RandomWave extends Signal {
    Random rand = new Random();
    private float[] point = new float[1000];
    private int element;
    
    public RandomWave() {
    	randomGenerator();
    }
    @Override
    public float getSample(long us) {
        us = us % 1000000;
        if (us == 0) {
        	element = 0;
        }
        if (us != 0) {
        	element++;
        }
        return point[element];
    }
    
    public void randomGenerator() {
    	for (int i = 0; i < 1000; i++) {
    		float y;
    		y = -1.0f + rand.nextFloat() * (1.0f - -1.0f);
    		point[i] = y;
    	}
    }
}