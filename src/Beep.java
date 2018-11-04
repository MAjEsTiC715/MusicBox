import javax.swing.JOptionPane;
import javax.swing.JFrame;
public class Beep {
    private static Signal s;

    public static void main(String[] args) {
        String waveName = "sawtooth";//args[0];
        int pitch = 440;//Integer.parseInt(args[1]);
        float duration = 15;//Float.parseFloat(args[2]);
        duration *= 1000000.0f;

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

        Envelope e = new Envelope(m, 3000000, 3000000, .4f, 3000000);
        try {
            e.setDuration((long) duration);
        }
        catch (IllegalArgumentException ex) {
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f, "Error Occurred within setDuration: " + ex);
        }

        try {
            WavePlay.play(e, (long)duration);
        }
        catch (Exception LineAvailableException) {
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f, "Error Occurred with Source Data Line");
        }
    }
}