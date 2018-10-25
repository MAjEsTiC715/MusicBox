import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

/**
 * This class contains methods that let you write signals out to .wav files
 */
public class WaveIO {
	public static final int SAMPLE_RATE = 44100; // Sampling rate, 44100 is a common CD quality standard
	public static final int BIT_DEPTH = 16; // Bits per sample
	public static final int CHANNELS = 1; // Mono
	public static final float MASTERING_VOLUME = 0.02f; // Volume to output sound at (outputting at max is too loud!)
	private WaveIO() {} // Why did I do this?
	/**
	 * Outputs the given signal as an array of 16-bit signed sample bytes
	 * @param s the input signal
	 * @param us duration of signal
	 * @return the raw bytes
	 */
	public static byte[] toByteArray(Signal s,long us) {
		// Record the input signal
		float duration = (float)us;
		duration /= ((float)Signal.MICROSECONDS_IN_A_SECOND);
		duration *= ((float)SAMPLE_RATE);
		float[] remaster = new float[(int)duration];
		for(int i = 0;i < remaster.length;i++)
		{
			float p = ((float)i)/((float)remaster.length);
			p *= ((float)us);
			long sus = (long)p;
			remaster[i] = s.getSample(sus);
		}
		// Then we need to convert to signed 16 bit values
		// Probably one of the only times we need to use shorts in JAVA
		short[] data = new short[remaster.length];
		for(int i = 0;i < remaster.length;i++)
		{
			float sample = remaster[i]*MASTERING_VOLUME;
			if(sample < -1.0f)
				sample = -1.0f;
			if(sample > 1.0f)
				sample = 1.0f;
			sample *= ((float)Short.MAX_VALUE);
			data[i] = (short)sample;
		}
		// Then we need to convert to bytes
		byte[] buffer = new byte[remaster.length*2];
		for(int i = 0;i < remaster.length;i++)
		{
			byte a = (byte)data[i];
			byte b = (byte)(data[i]>>8);
			buffer[i*2] = a;
			buffer[i*2+1] = b;
		}
		return buffer;
	}
	/**
	 * Outputs the given signal to a .wav file
	 * @param s the signal to write to WAV file
	 * @param us the duration of time to write for (in microseconds)
	 * @param filename the filename to write to
	 */
	public static void write(Signal s,long us,String filename) throws IOException {
		// Curious are you?
		// First we want to convert from the master sampling rate of 1,000,000/s to SAMPLE_RATE
		// This method is pretty low quality, there's better ways to resize samples but this will do for our assignments
		byte[] buffer = toByteArray(s,us);
		// Now output as WAV
		ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
		File f = new File(filename);
		AudioFormat af = new AudioFormat((float)SAMPLE_RATE,BIT_DEPTH,CHANNELS,true,false);
		AudioInputStream ais = new AudioInputStream(bais,af,buffer.length);
		AudioSystem.write(ais,AudioFileFormat.Type.WAVE,f);
		ais.close();
	}
}