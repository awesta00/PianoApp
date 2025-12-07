import javax.swing.*;
import java.awt.Color;

public class GuiFunctions extends JPanel {
    private boolean suggestions = false;

    public void toggleSuggestions(JButton toggleButton) {
        if (suggestions == false) {
            suggestions = true;
            toggleButton.setBackground(new Color(10, 180, 10));
            toggleButton.setText("Suggestions = true");
        } else {
            suggestions = false;
            toggleButton.setBackground(new Color(200, 200, 200));
            toggleButton.setText("Suggestions = false");
        }
    }

    public boolean getSuggestion() {
        return suggestions;
    }


}
