package roksard.typingTrainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EpTextKeyListener implements KeyListener {
    private JEditorPane epText;
    private MainJPanel jpanel;
    private Color DARK_GREEN = Color.getHSBColor(0.33f, 1, 0.5f);
    private Color RED = Color.RED;

    public EpTextKeyListener(JEditorPane epText, MainJPanel jpanel) {
        this.epText = epText;
        this.jpanel = jpanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int caretPosition = epText.getCaretPosition();
        if (caretPosition < epText.getText().length()) {
            Color statusIndicatorColor = jpanel.getStatusIndicatorColor();
            if (e.getKeyChar() == epText.getText().charAt(caretPosition)) {
                jpanel.setStatusIndicatorColor(DARK_GREEN);
            } else {
                jpanel.setStatusIndicatorColor(RED);
            }
            if (!statusIndicatorColor.equals(jpanel.getStatusIndicatorColor()))
                jpanel.repaint();

            epText.moveCaretPosition(caretPosition + 1);
            epText.setSelectionStart(epText.getCaretPosition());
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
