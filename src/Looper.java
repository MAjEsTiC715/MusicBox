import java.util.concurrent.atomic.AtomicBoolean;

public class Looper implements Runnable {

    private AtomicBoolean keepRunning;
    private SequenceTrack st;

    public Looper(SequenceTrack st) {
        keepRunning = new AtomicBoolean(true);
        this.st = st;
    }

    public void stop() {
        keepRunning.set(false);
    }

    @Override
    public void run() {
        while (keepRunning.get()) {
            WavePlay.play(st, st.getLength());
        }
    }

}