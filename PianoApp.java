import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;

public class PianoApp extends JPanel {

    // Sound player component:
    // private final MidiSoundPlayer player = new MidiSoundPlayer(); 
    private final PianoDisplay keyboardObject = new PianoDisplay();

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
                    keyboardObject.getData(device); //, player  Add parameter to constructor
                   
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
        JFrame frame = new JFrame("PianoApp");
        JScrollPane scrollPane = new JScrollPane(keyboardObject,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.setSize(1325, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        connectToMidiDevice("USB-MIDI.10");

        // SoundPlayer component:
        // frame.addWindowListener(new java.awt.event.WindowAdapter() {
        //     @Override
        //     public void windowClosing(java.awt.event.WindowEvent e) {
        //          player.close();
        //     }
        // }); 
    }

    public static void main(String[] args) {
       SwingUtilities.invokeLater(() -> {
            new PianoApp().buildGui();
        });
    }
}
