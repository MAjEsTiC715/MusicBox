/**
 * Returns points that form a Sine Wave
 * */
public class Sine extends Signal {

    public float getSample(long us) {
        us = us % 1000000;
        double u = Math.toRadians((float)us / 2777.777778); // Closes equivilent to pi/2
        double point = Math.sin(u);
        return (float)point;
    }

}

