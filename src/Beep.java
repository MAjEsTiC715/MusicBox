import javax.swing.JOptionPane;
import javax.swing.JFrame;
/**
 * Allows arguments passed from the command line (waveform name, pitch, and duration)
 * Creates pitch using modulator
 * Creates envelope using Envelope
 * Takes envelope and passes it through Waveplay.play()
 * */
public class Beep {
    private static Signal s;

    public static void main(String[] args) {
        String waveName = args[0];
        int pitch = Integer.parseInt(args[1]);
        float duration = Float.parseFloat(args[2]);
        duration *= 1000000.00f;

        if (waveName == "triangle") {
            s = new Triangle();
        }

        if (waveName == "square") {
            s = new Square(.5f);
        }

        if (waveName == "sawtooth") {
            s = new Sawtooth();
        }

        Modulator m = new Modulator(s);
        m.setPeriod(pitch);

        Envelope e = new Envelope(m, 6000000, 300000, .00f, 300000); // (Signal, attack, Decay, Sustain Level, Release)
        try {
            e.setDuration((long) duration);
        }
        catch (IllegalArgumentException ex) {
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f, ex);
        }

        WavePlay.play(e, (long)duration);
    }
}