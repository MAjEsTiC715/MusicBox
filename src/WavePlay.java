import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.LineUnavailableException;

public final class WavePlay {

    public static void play (Signal s, long duration) throws LineUnavailableException {
        SourceDataLine line = AudioSystem.getSourceDataLine(WaveIO.FORMAT);
        line.open();
        byte[] data = WaveIO.toByteArray(s, duration);
        line.start();
        WaveIO.writeFully(line, data);
        line.close();
    }
}

