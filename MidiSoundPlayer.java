import javax.sound.midi.*;

// THIS CLASS IS NOT CURRENTLY USED (and will be deleted)

public class MidiSoundPlayer {
    private Synthesizer synth;
    private MidiChannel channel;

    public MidiSoundPlayer() {
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            // Get a default channel (usually channel 0 = piano)
            channel = synth.getChannels()[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playNote(int midiNote, int velocity) {
        if (channel != null) {
            channel.noteOn(midiNote, velocity);
        }
    }

    public void stopNote(int midiNote) {
        if (channel != null) {
            channel.noteOff(midiNote);
        }
    }

    public void close() {
        if (synth != null && synth.isOpen()) synth.close();
    }
}
