import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Used in a new thread to loop continuosly when playing SequenceTrack
 */
public class Looper implements Runnable {

    private AtomicBoolean keepRunning;
    private SequenceTrack st;
    private double tempoFactor;
    /**
     * @param st the sequence of notes that is to be played
     * @param tempoFactor allows for a delay at the end of the track
     *                    otherwise, the beat would be thrown off
     */
    public Looper(SequenceTrack st, double tempoFactor) {
        keepRunning = new AtomicBoolean(true);
        this.st = st;
        this.tempoFactor = tempoFactor / 1000.0; // convert microseconds to milliseconds
    }

    public void stop() {
        keepRunning.set(false);
    }

    @Override
    public void run() {
        while (keepRunning.get()) {
            WavePlay.play(st, st.getLength());
            try {
                Thread.sleep((long)tempoFactor);
            }
            catch (Exception e) {
                System.out.println("error located in Looper: " + e);
            }
        }
    }

}