package roksard.typingTrainer.listeners;

import roksard.typingTrainer.MainJPanel;
import roksard.typingTrainer.pojo.Statistic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EpTextKeyListener implements KeyListener {
    private JTextArea epText;
    private MainJPanel jpanel;
    private Color DARK_GREEN = Color.getHSBColor(0.33f, 1, 0.5f);
    private Color RED = Color.RED;

    public EpTextKeyListener(JTextArea epText, MainJPanel jpanel) {
        this.epText = epText;
        this.jpanel = jpanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        Statistic currentStats = jpanel.getSession().getCurrentStats();
        int caretPosition = epText.getCaretPosition();
        if (caretPosition < epText.getText().length()) {
            Color statusIndicatorColor = jpanel.getStatusIndicatorColor();
            if (e.getKeyChar() == epText.getText().charAt(caretPosition)) {
                jpanel.setStatusIndicatorColor(DARK_GREEN);
            } else {
                jpanel.setStatusIndicatorColor(RED);
                if (jpanel.getSession().isStarted()) {
                    currentStats.setErrCount(currentStats.getErrCount() + 1);
                }
            }
            if (!statusIndicatorColor.equals(jpanel.getStatusIndicatorColor())) {
                jpanel.repaint();
            }

            if (jpanel.getSession().isStarted()) {
                currentStats.setCount(currentStats.getCount() + 1);
            }
            epText.moveCaretPosition(caretPosition + 1);
            epText.setSelectionStart(epText.getCaretPosition());
            epText.setSelectionEnd(epText.getCaretPosition());
            jpanel.updateLbCount();
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
