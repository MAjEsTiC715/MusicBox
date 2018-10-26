/**
 * Returns points that form a Complex Sine Wave
 * */
public class Complex extends Signal {

    @Override
    public float getSample(long us) {
        us = us % 1000000;
        double u = Math.toRadians((float)us / 2777.777778); // Close equivilent to pi/2
        double point = Math.sin(u) + Math.sin(2*u) + Math.sin(3*u);
        return (float)point;
    }
}
