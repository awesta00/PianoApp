import javax.sound.midi.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PianoApp extends JPanel {

    private PianoDisplay keyboardObject;  // no final, will initialize in buildGui()
    private KeySuggestions suggestionObject;

    public void connectToMidiDevice(String deviceName) {
        try {
            MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
            for (MidiDevice.Info info : infos) {
                if (info.getName().contains(deviceName)) {

                    MidiDevice device = MidiSystem.getMidiDevice(info);
                    if (device.getMaxTransmitters() == 0) {
                        System.out.println("LOADING or Device has no transmitters: " + info.getName());
                        continue;
                    }
                    device.open();
                    keyboardObject.getData(device);
                   
                    System.out.println("Connected to: " + info.getName());
                    return;
                }
            }
            System.out.println("MIDI device not found: " + deviceName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buildGui() {
        GuiFunctions gui = new GuiFunctions();
        keyboardObject =  new PianoDisplay(gui);

        JFrame frame = new JFrame("PianoApp");
        JScrollPane scrollPane = new JScrollPane(keyboardObject,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JComboBox<String> keySigBox = new JComboBox<>();

        JButton suggestionToggle = new JButton();
        suggestionToggle.setSize(200,50);
        suggestionToggle.setText("Suggestions = false");
        suggestionToggle.setVisible(true);
        suggestionToggle.setBackground(new Color (200, 200, 200));
        suggestionToggle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.toggleSuggestions(suggestionToggle);
            }
        });

        JButton confirmKey = new JButton();
        confirmKey.setSize(280,50);
        confirmKey.setText("Click me after changing key signature");
        confirmKey.setVisible(true);
        confirmKey.setBackground(new Color (200, 200, 200));
        confirmKey.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                suggestionObject = new KeySuggestions(keySigBox);
            }
        });


        keySigBox.setPreferredSize(new Dimension(200, 30));  // JComboBox uses preferredSize, not setSize
        keySigBox.setVisible(true);
        // keySigBox.setName("Key signature:");
        keySigBox.setBackground(new Color (200, 200, 200));
        keySigBox.addItem("C major / A minor (all white)");
        keySigBox.addItem("D major / B minor (F#, C#)");
        keySigBox.addItem("G major / E minor (F#)");
        keySigBox.addItem("E major / C# minor (F#, C#, G#, D#)");
        keySigBox.addItem("A major / F# minor (F#, C#, G#)");
        keySigBox.addItem("F# major / D# minor (F#, C#, G#, D#, A#, E#)");
        keySigBox.addItem("B major / G# minor (F#, C#, G#, D#, A#)");
        
        keySigBox.addItem("C# major / A# minor (F#, C#, G#, D#, A#, E#, B#)");
        keySigBox.addItem("B♭ major / G minor (B♭, E♭)");
        keySigBox.addItem("F major / D minor (B♭)");
        keySigBox.addItem("A♭ major / F minor (B♭, E♭, A♭, D♭)");
        keySigBox.addItem("E♭ major / C minor (B♭, E♭, A♭)");
        keySigBox.addItem("G♭ major / E♭ minor (B♭, E♭, A♭, D♭, G♭, C♭)");
        keySigBox.addItem("D♭ major / B♭ minor (B♭, E♭, A♭, D♭, G♭)");
        keySigBox.addItem("C♭ major / A♭ minor (B♭, E♭, A♭, D♭, G♭, C♭, F♭)");

        suggestionObject = new KeySuggestions(keySigBox);

        JPanel topbar = new JPanel();
        topbar.add(suggestionToggle);
        topbar.add(keySigBox);
        topbar.add(confirmKey);
        
        frame.add(topbar, BorderLayout.NORTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.setSize(1325, 500);
        // frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        connectToMidiDevice("USB-MIDI.10");
    }

    public static void main(String[] args) {
       SwingUtilities.invokeLater(() -> {
            new PianoApp().buildGui();
        });
    }
}
