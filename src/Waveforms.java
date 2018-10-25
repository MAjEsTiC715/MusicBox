import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * Assumes you've completed the assignment and sends me signal tests to grade
 */
public class Waveforms {

	// Computes the squared error between two sets of six samples each
	public static float findOneError(String s1,String s2) {
		float v1 = Float.parseFloat(s1);
		float v2 = Float.parseFloat(s2);
		float dv = v2-v1;
		return dv*dv;
	}
	public static float findError(String ln1,String ln2) {
		String[] ls1 = ln1.split("[,;]");
		String[] ls2 = ln2.split("[,;]");
		float e1 = findOneError(ls1[1],ls2[1]);
		float e2 = findOneError(ls1[2],ls2[2]);
		float e3 = findOneError(ls1[3],ls2[3]);
		float e4 = findOneError(ls1[4],ls2[4]);
		float e5 = findOneError(ls1[5],ls2[5]);
		float e6 = findOneError(ls1[6],ls2[6]);
		return e1+e2+e3+e4+e5+e6;
	}

	public static void main(String[] args) throws IOException {

		// Create signals
		Square s1 = new Square(0.5f);
		Square s2 = new Square(0.25f);
		Square s3 = new Square(0.125f);
		Sawtooth sw = new Sawtooth();
		Triangle t = new Triangle();
		Modulator m = new Modulator(sw);
		m.setPeriod(5);

		// Wave files
		Modulator m440s1 = new Modulator(s1);
		m440s1.setPeriod(440);
		WaveIO.write(m440s1,1000000,"square50.wav");

		Modulator m440s2 = new Modulator(s2);
		m440s2.setPeriod(440);
		WaveIO.write(m440s2,1000000,"square25.wav");

		Modulator m440s3 = new Modulator(s3);
		m440s3.setPeriod(440);
		WaveIO.write(m440s3,1000000,"square125.wav");

		Modulator m440sw = new Modulator(sw);
		m440sw.setPeriod(440);
		WaveIO.write(m440sw,1000000,"sawtooth.wav");

		Modulator m440t = new Modulator(t);
		m440t.setPeriod(440);
		WaveIO.write(m440t,1000000,"triangle.wav");

		// Plot
		PrintStream ps = new PrintStream(new FileOutputStream(new File("check.csv")));
		for(int i = 0;i < (Inherit.MICROSECONDS_IN_A_SECOND*3);i += 1000) {

			// Sample
			float s1x = s1.getSample(i);
			float s2x = s2.getSample(i);
			float s3x = s3.getSample(i);
			float swx = sw.getSample(i);
			float tx = t.getSample(i);
			float mx = m.getSample(i);

			// What time is this in seconds?
			float sec = (float)i;
			sec /= ((float)Inherit.MICROSECONDS_IN_A_SECOND);

			ps.println(sec+","+s1x+","+s2x+","+s3x+","+swx+","+tx+","+mx+";");
		}

		// Check error between
		int ct = 0;
		float err = 0.0f;
		BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(new File("check.csv"))));
		BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream(new File("target.csv"))));
		for(int i = 0;i < (Inherit.MICROSECONDS_IN_A_SECOND*3);i += 1000) {

			String ln1 = br1.readLine();
			String ln2 = br2.readLine();
			err += findError(ln1,ln2);
			ct += 6;
		}
		br1.close();
		br2.close();
		err /= ((float)ct);
		err = (float)Math.sqrt(err);

		System.out.print("Error: "+err+" - ");
		if(err <= 0.15f)
			System.out.println("Acceptable");
		else
			System.out.println("Unacceptable");

		// Finish
		ps.close();
	}
}
