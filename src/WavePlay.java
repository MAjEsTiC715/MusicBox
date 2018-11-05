import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
/**
 * Opens a SourceDataLine and acts as a source to its mixer
 * It then writes audio bytes to the data line using WaveIO.WriteFully()
 * And handles the
 * Buffering of the bytes and delivers them to the mixer
 * */
public final class WavePlay {

    public static void play (Signal s, long duration) {
        try {
            SourceDataLine line = AudioSystem.getSourceDataLine(WaveIO.FORMAT);
            line.open();
            byte[] data = WaveIO.toByteArray(s, duration);
            line.start();
            WaveIO.writeFully(line, data);
            line.close();
        }
        catch (Exception LineAvailableException) {
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f, "Error, Can't open audio file: " + LineAvailableException);
        }
    }
}

