/**
 * The Triangle class starts at 0 and increases linearly
 * until it reaches a quarter of the period (y-axis = 1.0) 
 * Then it decreases for half the period (y-axis = -1.0)
 * Then increases and reaches 0 on y axis
 * */
public class Triangle extends Signal {
    @Override
    public float getSample(long us) {
        us = us % 1000000;
        float point = (float)us / 250000.f;
        if (us >= 250000) {
            point = (float)us / -250000.0f + 2.0f;
            if (us >= 750000) {
                point = (float)us / 250000.0f - 4.0f;
            }
        }
        return point;
    }
}