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
        String text = epText.getText();
        char userInput = e.getKeyChar();
        int caretPosition = epText.getCaretPosition();
        if (caretPosition < text.length()) {
            Color statusIndicatorColor = jpanel.getStatusIndicatorColor();
            char requiredChar = text.charAt(caretPosition);
            boolean isCRLF = requiredChar == '\r' && caretPosition+1 < text.length() && text.charAt(caretPosition+1) == '\n';
            //with a [space] user can skip through unprintable and invisible characters
            if (userInput == requiredChar || userInput == ' ' || userInput == '\n' && isCRLF) {
                //correct letter typed
                jpanel.setStatusIndicatorColor(DARK_GREEN);
                if (jpanel.getSession().isStarted()) {
                    currentStats.setCount(currentStats.getCount() + 1);
                }
                if (isCRLF) {
                    epText.moveCaretPosition(caretPosition + 2);; //extra skip for "\r\n" new line characters combo
                } else {
                    epText.moveCaretPosition(caretPosition + 1);
                }
            } else {
                //incorrect letter typed
                jpanel.setStatusIndicatorColor(RED);
                if (jpanel.getSession().isStarted()) {
                    currentStats.setErrCount(currentStats.getErrCount() + 1);
                }
            }
            if (!statusIndicatorColor.equals(jpanel.getStatusIndicatorColor())) {
                jpanel.repaint();
            }

            epText.setSelectionStart(epText.getCaretPosition());
            epText.setSelectionEnd(epText.getCaretPosition());
            jpanel.updateLbCountErrCount();
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
