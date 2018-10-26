import java.util.Random;
/**
 * Generates random points within the limit of -1 to 1 on the y-axis
 * */
public class RandomWave extends Signal {
    Random rand = new Random();
    float point;
    @Override
    public float getSample(long us) {
        us = us % 1000000;
        if (us % 1000 == 0) {
            point = -1.0f + rand.nextFloat() * (1.0f - -1.0f);;
        }
        return point;
    }
}