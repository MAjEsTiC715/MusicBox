import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * CS259 Inheritance Assignment
 */
public class Inherit {
	public static final int MICROSECONDS_IN_A_SECOND = 1000000; // 1 second is 1,000,000 microseconds
	/**
	 * ASSIGNMENT:
	 * 
	 * 1. Finish the implementations of Square.java and Sawtooth.java
	 * 
	 * 2. Create a new object called Triangle that implements a triangle waveform and extends Signal
	 *    A triangle waveform increases linearly from 0.0f to its highest point in the first half of its period,
	 *    then decreases linearly to its lowest point in the second half of the period before increasing linearly back to 0.0f
	 *    
	 * 3. Add the Triangle waveform to the code below so that it is output with the 4 existing waveforms
	 *    Examine the output file plot.csv in a text editor to verify the implementation is correct
	 *    If it helps you, you can download plot.csv to your local machine and plot it in MATLAB as well
	 *    
	 * 4. Create a new class Modulator that accepts ANY Signal as a parameter in its constructor
	 *    Modulator will ALSO extend Signal
	 *    Modulator needs a method setPeriod(int hz) which will make its getSample method output the waveform at varying frequencies,
	 *    not just a period of 1 second (1 HZ)
	 *    
	 * 5. Add a Modulator to the code below that outputs a Sawtooth wave at 200HZ
	 *    You should now have the output of six signals in plot.csv
	 * 
	 * @param args (Ignored)
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		// Creates some waveforms
		Square s1 = new Square(0.5f);
		Square s2 = new Square(0.25f);
		Square s3 = new Square(0.125f);
		Sawtooth sw = new Sawtooth();
		Triangle tg = new Triangle();
		
		// Plot
		PrintStream ps = new PrintStream(new FileOutputStream(new File("plot.csv")));
		for(int i = 0;i < MICROSECONDS_IN_A_SECOND;i += 1000) {
			
			// Sample
			float s1x = s1.getSample(i);
			float s2x = s2.getSample(i);
			float s3x = s3.getSample(i);
			float swx = sw.getSample(i);
			float tgx = tg.getSample(i);
			
			ps.println(i+","+s1x+","+s2x+","+s3x+","+swx+","+tgx+";");
		}
		
		// Done
		ps.close();
	}
}
