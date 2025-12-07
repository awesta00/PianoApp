import java.util.HashMap;

import javax.swing.JComboBox;

public class KeySuggestions {
    private String selected;
    private int keySig = 0;
    HashMap<Integer, String[]> keySigToScale = new HashMap<>();
    HashMap<String, int[]> noteToMidi = new HashMap<>();


    public KeySuggestions (JComboBox<String> keySigObject) {
        selected = (String) keySigObject.getSelectedItem();
        // System.out.println(selected);
        keySigToScale.put(0, new String[]{"C", "D", "E", "F", "G", "A", "B"});               // C major / A minor
        keySigToScale.put(1, new String[]{"D", "E", "F#", "G", "A", "B", "C#"});            // D major / B minor
        keySigToScale.put(2, new String[]{"G", "A", "B", "C", "D", "E", "F#"});             // G major / E minor
        keySigToScale.put(3, new String[]{"E", "F#", "G#", "A", "B", "C#", "D#"});          // E major / C# minor
        keySigToScale.put(4, new String[]{"A", "B", "C#", "D", "E", "F#", "G#"});           // A major / F# minor
        keySigToScale.put(5, new String[]{"F#", "G#", "A#", "B", "C#", "D#", "E#"});        // F# major / D# minor
        keySigToScale.put(6, new String[]{"B", "C#", "D#", "E", "F#", "G#", "A#"});         // B major / G# minor
        keySigToScale.put(7, new String[]{"C#", "D#", "E#", "F#", "G#", "A#", "B#"});       // C# major / A# minor
        keySigToScale.put(8, new String[]{"Bb", "C", "D", "Eb", "F", "G", "A"});            // Bb major / G minor
        keySigToScale.put(9, new String[]{"F", "G", "A", "Bb", "C", "D", "E"});             // F major / D minor
        keySigToScale.put(10, new String[]{"Ab", "Bb", "C", "Db", "Eb", "F", "G"});         // Ab major / F minor
        keySigToScale.put(11, new String[]{"Eb", "F", "G", "Ab", "Bb", "C", "D"});          // Eb major / C minor
        keySigToScale.put(12, new String[]{"Gb", "Ab", "Bb", "Cb", "Db", "Eb", "F"});       // Gb major / Eb minor
        keySigToScale.put(13, new String[]{"Db", "Eb", "F", "Gb", "Ab", "Bb", "C"});        // Db major / Bb minor
        keySigToScale.put(14, new String[]{"Cb", "Db", "Eb", "Fb", "Gb", "Ab", "Bb"});      // Cb major / Ab minor
        detectKeySig(selected);
    }
    // each note needs a list of numbers that represent it on the keyboard so the computer knows what it is

    // Goal: when a note is pressed, 3 nearby keys will be suggested depending on the key
    // Requirements: variable to store the key sig, lists of each note per key
    // hashmap storing arrays. Key = key sig, Value = array of notes in the array
    // can only suggest up to 3 notes above middle C (and 3 below) = 6 total
    public void detectKeySig(String selected) {
    switch (selected) {

        case "D major / B minor (F#, C#)":
            System.out.println("D major / B minor");
            keySig = 1;

            for (String note : keySigToScale.get(keySig)) {
                System.out.println(note);
            }
            break;

        case "G major / E minor (F#)":
            System.out.println("G major / E minor");
            keySig = 2;
            break;

        case "E major / C# minor (F#, C#, G#, D#)":
            System.out.println("E major / C# minor");
            keySig = 3;
            break;

        case "A major / F# minor (F#, C#, G#)":
            System.out.println("A major / F# minor");
            keySig = 4;
            break;

        case "F# major / D# minor (F#, C#, G#, D#, A#, E#)":
            System.out.println("F# major / D# minor");
            keySig = 5;
            break;

        case "B major / G# minor (F#, C#, G#, D#, A#)":
            System.out.println("B major / G# minor");
            keySig = 6;
            break;

        case "C# major / A# minor (F#, C#, G#, D#, A#, E#, B#)":
            System.out.println("C# major / A# minor");
            keySig = 7;
            break;

        case "B♭ major / G minor (B♭, E♭)":
            System.out.println("Bb major / G minor");
            keySig = 8;
            break;

        case "F major / D minor (B♭)":
            System.out.println("F major / D minor");
            keySig = 9;
            break;

        case "A♭ major / F minor (B♭, E♭, A♭, D♭)":
            System.out.println("Ab major / F minor");
            keySig = 10;
            break;

        case "E♭ major / C minor (B♭, E♭, A♭)":
            System.out.println("Eb major / C minor");
            keySig = 11;
            break;

        case "G♭ major / E♭ minor (B♭, E♭, A♭, D♭, G♭, C♭)":
            System.out.println("Gb major / Eb minor");
            keySig = 12;
            break;

        case "D♭ major / B♭ minor (B♭, E♭, A♭, D♭, G♭)":
            System.out.println("Db major / Bb minor");
            keySig = 13;
            break;

        case "C♭ major / A♭ minor (B♭, E♭, A♭, D♭, G♭, C♭, F♭)":
            System.out.println("Cb major / Ab minor");
            keySig = 14;
            break;

        default:
            // "C major / A minor (all white)":
            System.out.println("C major / A minor");
            keySig = 0;
    }
}

}
