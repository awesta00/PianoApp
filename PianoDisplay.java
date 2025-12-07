import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Transmitter;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import java.awt.*;

public class PianoDisplay extends JPanel{
    
    private final Map<Integer, Boolean> keyPressed = new HashMap<>();
    private static final int FIRST_KEY = 21;    // A0 (where piano starts; # < 21 DNE on this piano)
    private static final int NUM_KEYS = 88;     // full piano
    private GuiFunctions gui;
    // private KeySuggestions keySuggest;


    public PianoDisplay(GuiFunctions gui) {
        setPreferredSize(new Dimension(NUM_KEYS * 25, 200)); // setting size of keys
        for (int i = 0; i < NUM_KEYS; i++) {
            keyPressed.put(FIRST_KEY + i, false);
        }
        this.gui = gui;
    }

    /*
     * Gets MIDI data from keyboard
     * The MIDISoundPlayer is not used due to noticeable audible latency
     * @param device is the keyboard.
     */
    public void getData(MidiDevice device) {
        try {
            Transmitter transmitter = device.getTransmitter();
                    transmitter.setReceiver(new Receiver() {
                        @Override
                        public void send(MidiMessage message, long timeStamp) {
                            if (message instanceof ShortMessage sm) {
                                int cmd = sm.getCommand(); // Note on/off (144 on, 128 off???)
                                int pitch = sm.getData1(); // MIDI note number (0-127); (21-108 inclusive are actual keys)
                                int velocity = sm.getData2(); // How hard the note was pressed (0-127)
                                // System.out.println(suggestion);

                                // Adds suggestions
                                if (cmd == ShortMessage.NOTE_ON && velocity > 0) {
                                    boolean suggestion = gui.getSuggestion();

                                    keyPressed.put(pitch, true);
                                    
                                    if (suggestion == true) {
                                        
                                        int suggested = pitch - 10;
                                        for (int i = pitch - 10; i <= pitch + 10; i++) {
                                            keyPressed.put(suggested, true);
                                            suggested++;
                                        }

                                        // keySuggest.detectKeySig();
                                    }

                                    repaint();
                                    // Removes suggestions
                                } else if (cmd == ShortMessage.NOTE_OFF ||
                                           (cmd == ShortMessage.NOTE_ON && velocity == 0)) {
                                    boolean suggestion = gui.getSuggestion();

                                    keyPressed.put(pitch, false);
                                    if (suggestion == true) {
                                        int suggested = pitch - 10;
                                            for (int i = pitch - 10; i <= pitch + 10; i++) {
                                            keyPressed.put(suggested, false);
                                            suggested++;
                                        }
                                    }
                                    repaint();
                                }
                            }
                        }

                        @Override
                        public void close() {}
                    });
                } catch (MidiUnavailableException  e) {
                    System.err.println("MIDI device unavailable");
                }
    }


    /*
     * Paints the graphic for the piano. Is updated everytime a key is pressed/released
     * @param g, part of override; do not remove.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int panelHeight = getHeight();
        int whiteKeyWidth = 25;
        int whiteKeyHeight = 200;
        int blackKeyWidth = 15;
        int blackKeyHeight = (int) (whiteKeyHeight * 0.6);
        int yOffset = panelHeight - whiteKeyHeight; // anchor piano to bottom


        // Create positions of white keys
        int x = 0;
        Map<Integer, Integer> keyX = new HashMap<>();
        for (int i = 0; i < NUM_KEYS; i++) {
            int midi = FIRST_KEY + i; // will represent the ID
            if (!isBlackKey(midi)) {
                keyX.put(midi, x);
                x += whiteKeyWidth;
            }
        }

        // Draw white keys
        for (int i = 0; i < NUM_KEYS; i++) {
            int midi = FIRST_KEY + i;
            if (!isBlackKey(midi)) {
                int wx = keyX.get(midi);
                g.setColor(keyPressed.getOrDefault(midi, false) ? Color.LIGHT_GRAY : Color.WHITE);
                g.fillRect(wx, yOffset, whiteKeyWidth, whiteKeyHeight);
                g.setColor(Color.BLACK);
                g.drawRect(wx, yOffset, whiteKeyWidth, whiteKeyHeight);
            }
        }

        // Draw black keys
        for (int i = 0; i < NUM_KEYS; i++) {
            int midi = FIRST_KEY + i;
            if (isBlackKey(midi)) {
                int wx = getWhiteKeyXBefore(midi, keyX);
                if (wx == -1) continue;
                int bx = wx + (whiteKeyWidth - blackKeyWidth / 2);
                g.setColor(keyPressed.getOrDefault(midi, false) ? new Color(128,175,175) : Color.BLACK);
                g.fillRect(bx, yOffset, blackKeyWidth, blackKeyHeight);
            }
        }
    }

    // math using mod to find black keys
    private boolean isBlackKey(int midiNote) {
        int pitchClass = midiNote % 12;
        return switch (pitchClass) {
            case 1, 3, 6, 8, 10 -> true;
            default -> false;
        };
    }

    private int getWhiteKeyXBefore(int midiNote, Map<Integer, Integer> keyX) {
        for (int i = midiNote - 1; i >= FIRST_KEY; i--) {
            if (!isBlackKey(i) && keyX.containsKey(i)) return keyX.get(i);
        }
        return -1;
    }

}
