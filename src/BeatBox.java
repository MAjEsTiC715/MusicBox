import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * "Cyber Beat Box"
 * @author Kathy Sierra & Bert Bates
 */
public class BeatBox {

    private static float tempo = 450000;
    JPanel mainPanel; // Main panel
    ArrayList<JCheckBox> checkboxList; // All of the check boxes
    Sequencer sequencer; // MIDI sequencer
    Sequence sequence; // MIDI sequence
    Track track; // MIDI track
    SequenceTrack st;
    JFrame theFrame; // Container frame

    // Instrument names and MIDI codes
    String[] instrumentNames = {"C4", "D4",
            "E4","F4", "G4", "A4",
            "B4", "C5", "D5", "E5", "F5",
            "G5", "C6", "B5", "C6",
            "D6"};

    int[] pitch = {60,62,64,65,67,69,70,71,72,74,76,77,79,81,83,84};

    /**
     * Creates a beat box which presents the GUI
     * @param args (ignored)
     */
    public static void main (String[] args) {
        new BeatBox().buildGUI();
    }

    /**
     * Puts everything into place and shows it
     */
    public void buildGUI() {
        // Prepare the container frame
        theFrame = new JFrame("Cyber BeatBox");
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // Make all of the check boxes
        checkboxList = new ArrayList<>();
        Box buttonBox = new Box(BoxLayout.Y_AXIS);

        // Start button
        JButton start = new JButton("Start");
        start.addActionListener(new MyStartListener());
        buttonBox.add(start);

        // Stop button
        JButton stop = new JButton("Stop");
        stop.addActionListener(new MyStopListener());
        buttonBox.add(stop);

        // Faster
        JButton upTempo = new JButton("Tempo Up");
        upTempo.addActionListener(new MyUpTempoListener());
        buttonBox.add(upTempo);

        // Slower
        JButton downTempo = new JButton("Tempo Down");
        downTempo.addActionListener(new MyDownTempoListener());
        buttonBox.add(downTempo);

        // The list of instrument labels
        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for (int i = 0; i < 16; i++) {
            nameBox.add(new Label(instrumentNames[i]));
        }

        // The labels and the command buttons go here
        background.add(BorderLayout.EAST, buttonBox);
        background.add(BorderLayout.WEST, nameBox);

        theFrame.getContentPane().add(background);

        // This has all of the check boxes
        GridLayout grid = new GridLayout(16,16);
        grid.setVgap(1);
        grid.setHgap(2);
        mainPanel = new JPanel(grid);
        background.add(BorderLayout.CENTER, mainPanel);

        // This wipes out all of the check boxes so they start out off
        for (int i = 0; i < 256; i++) {
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkboxList.add(c);
            mainPanel.add(c);
        }



        theFrame.setBounds(50,50,300,300); // Sets a preferred size for the frame
        theFrame.pack(); // Makes the frame EXACTLY as big as it should be and no bigger based on what you put in it
        theFrame.setVisible(true); // This actually "opens" the main window
    }

    public void buildTrackAndStart() {
        int[] pitchList = null;
        Razorback ins = new Razorback();
        st = new SequenceTrack(ins);

        for (int i = 0; i < 16; i++) {
            pitchList = new int[16];
            int key = pitch[i];
            for (int j = 0; j < 16; j++ ) {
                JCheckBox jc = (JCheckBox) checkboxList.get(j + (16*i));
                if ( jc.isSelected()) {
                    pitchList[j] = key;
                } else {
                    pitchList[j] = 0;
                }
            } // close inner loop
            makeTracks(pitchList);
        } // close outer
        WavePlay.play(st,st.getLength());

    } // close buildTrackAndStart method


    public class MyStartListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            buildTrackAndStart();
        }
    } // close inner class

    public class MyStopListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            sequencer.stop();
        }
    } // close inner class

    public class MyUpTempoListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            float tempoFactor = tempo;
            tempo = (tempoFactor * .97f);
        }
    } // close inner class

    public class MyDownTempoListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            float tempoFactor = tempo;
            tempo = (tempoFactor * 1.03f);
        }
    } // close inner class
    public void makeTracks(int[] list) {

        for (int i = 0; i < 16; i++) {
            long key = list[i];

            if (key != 0) {
                int hz = Note.midiToPitch((int)(key));
                st.add(i * (long)tempo, (long)tempo, hz);
            }
        }
    }


    } // close class
