import java.util.Arrays;
import java.util.Random;
/**
 * Generates random points within the limit of -1 to 1 on the y-axis
 * */
public class RandomWave extends Signal {
    Random rand = new Random();
    private float[] point = new float[3000];
    private int element;

    public RandomWave() {
        randomGenerator();
    }

    private void setElement(int e) {
        this.element = e;
    }

    private int getElement() {
        return element;
    }
    @Override
    public float getSample(long us) {
        us = us % 1000000;
        if (us == 0) {
            setElement(0);
        }
        if (us > 0) {
            int e = getElement() + 1;
            setElement(e);
        }
        return point[getElement()];
    }
    /** Create float array that holds 1000 unique points that will be used for every period*/
    private void randomGenerator() {
        for (int i = 0; i < 1000; i++) {
            float y;
            y = -1.0f + rand.nextFloat() * (1.0f - -1.0f);
            point[i] = y;
        }
    }
}