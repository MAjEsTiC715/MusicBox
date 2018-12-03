import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

/**
 * "Cyber Beat Box"
 * @author Kathy Sierra & Bert Bates
 */
public class BeatBox {

    private static final long TIME_PER_SCALE_NOTE = 100000;
    static private final String newline = "\n";
    private double tempoFactor = 500000;
    private SequenceTrack st;
    private Looper looper;
    private Instrument ins = new Razorback();

    JButton start;
    JButton saveButton;
    JPanel mainPanel; // Main panel
    ArrayList<JCheckBox> checkboxList; // All of the check boxes
    JFrame theFrame; // Container frame
    JLabel BPMLabel; // label to hold BPM
    JFileChooser fc;
    JTextArea log;

    // Note names
    String[] noteNames = {"D6", "C6",
            "B6","A6", "G5", "F5",
            "E5", "D5", "C5", "B5", "A5",
            "G4", "F4", "E4", "D4",
            "C4"};

    // Instrument names
    String[] instrumentNames = {"PWM", "Razorback"};

    // MIDI codes
    int[] pitch = {84,83,81,79,77,76,74,72,71,70,69,67,65,64,62,60};

    // Keeps track of notes applied for the track
    int[] notesApplied = new int[16];

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

        //Create the log, because the action listeners
        //need to refer to it.
        log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);

        fc = new JFileChooser(); // create a file chooser
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // Make all of the check boxes
        checkboxList = new ArrayList<>();
        Box buttonBox = new Box(BoxLayout.Y_AXIS);

        //Make combo box for instruments
        JComboBox instrumentList = new JComboBox(instrumentNames);
        instrumentList.setSelectedIndex(1);
        instrumentList.addActionListener(new MyInstrumentListener());
        buttonBox.add(instrumentList);

        // Start button
        start = new JButton("Start");
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

        // Clear
        JButton clearBoxes = new JButton("Clear Checkboxes");
        clearBoxes.addActionListener(new MyClearListener());
        buttonBox.add(clearBoxes);

        // Save
        saveButton = new JButton("Save Track");
        saveButton.addActionListener(new MySaveListener());
        buttonBox.add(saveButton);

        // BPM counter
        Box counterBox = new Box(BoxLayout.X_AXIS);
        BPMLabel = new JLabel("BPM: ...");
        counterBox.add(BPMLabel);

        // The list of instrument labels
        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for (int i = 0; i < 16; i++) {
            nameBox.add(new Label(noteNames[i]));
        }

        // The labels and the command buttons go here and set color
        background.add(BorderLayout.EAST, buttonBox);
        background.add(BorderLayout.WEST, nameBox);
        background.add(BorderLayout.NORTH, BPMLabel);
        background.add(logScrollPane, BorderLayout.CENTER);
        background.setBackground(Color.cyan);

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

        // Build Hashmap of notes to frequencies
        Note.buildNoteMap();

        theFrame.setBounds(50,50,300,300); // Sets a preferred size for the frame
        theFrame.pack(); // Makes the frame EXACTLY as big as it should be and no bigger based on what you put in it
        theFrame.setVisible(true); // This actually "opens" the main window
    }

    private void buildTrackAndStart() {
        int[] pitchList = null;
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

    } // close buildTrackAndStart method

    public class MyStartListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            buildTrackAndStart();
            if (a.getSource() == start) {
                if (looper == null) {
                    looper = new Looper(st, tempoFactor);
                    Thread t = new Thread(looper);  //Open new thread
                    t.start();
                }
            }
        }
    } // close inner class

    public class MyStopListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            looper.stop();
            looper = null;
        }
    } // close inner class

    public class MyUpTempoListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            tempoFactor = tempoFactor * .97;
            BPMLabel.setText("BPM: "+ Double.toString((60.0/(tempoFactor/1000000.0)))); // sets BPM
        }
    } // close inner class

    public class MyDownTempoListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            tempoFactor = tempoFactor * 1.03;
            BPMLabel.setText("BPM: "+ Double.toString((60.0/(tempoFactor/1000000.0)))); // sets BPM
        }
    } // close inner class


    public class MyInstrumentListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            JComboBox cb = (JComboBox)a.getSource();
            String InstrumentName = (String)cb.getSelectedItem();
            updateLabel(InstrumentName);
        }
    } // close inner class

    public class MyClearListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            clear();
        }
    } // close inner class

    public class MySaveListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            if (a.getSource() == saveButton) {
                int returnVal = fc.showSaveDialog(fc);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = createFile();
                    //This is where a real application would save the file.
                    log.append("Saving: " + file.getName() + "." + newline);
                } else {
                    log.append("Save command cancelled by user." + newline);
                }
                log.setCaretPosition(log.getDocument().getLength());
            }
        }
    } // close inner class

    private void makeTracks(int[] list) {

        for (int i = 0; i < 16; i++) {
            long key = list[i];

            if (key != 0) {
                int hz = Note.midiToPitch((int)(key));
                st.add(i * (long)tempoFactor, TIME_PER_SCALE_NOTE, hz);
                notesApplied[i] = i;
            }
        }
    }
    /**
     * creates new instrument
     * @param name label name for ComboBox
     */
    protected void updateLabel(String name) {
        if (name == "PWM") {
            this.ins = new PWM();
        }
        if (name == "Razorback") {
            this.ins = new Razorback();
        }
    }

    protected void clear() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++ ) {
                JCheckBox jc = checkboxList.get(j + (16*i));
                if ( jc.isSelected()) {
                    jc.setSelected(false);
                }
            } // close inner loop
        } // close outer
    }

    protected File createFile() {
        File fileout = new File("C:\\Users\\noahg\\Desktop\\all COLLEGE\\August Semester 2018\\CS 259\\test.txt");
        String instrumentName = ins.getName();
        int numOfNotes = st.track.size();
        int[] tempNotes = new int[numOfNotes];
        int noteCount = 0;

        for (int midiNote : notesApplied) {
            int i = 0;
            if (midiNote != 0) {
                tempNotes[i] = midiNote;
                i++;
            }
        }

        try {
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileout), "UTF8"));
            out.append(instrumentName + numOfNotes).append("\r\n");
            for (Note sample: st.track) {
                long position = sample.getPosition();
                String frequency = Note.notToPitch(tempNotes[noteCount]);
                long duration = TIME_PER_SCALE_NOTE + position;
                out.append(position + frequency + duration).append("\r\n");
                noteCount++;
            }
            out.flush();
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return fileout;
    }
} // close class
