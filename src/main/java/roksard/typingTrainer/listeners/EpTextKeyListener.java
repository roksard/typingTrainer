package roksard.typingTrainer.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import roksard.typingTrainer.UpperPanel;
import roksard.typingTrainer.pojo.Statistic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EpTextKeyListener implements KeyListener {
    private JTextArea epText;
    private UpperPanel upperPanel;
    private Color DARK_GREEN = Color.getHSBColor(0.33f, 1, 0.5f);
    private Color RED = Color.RED;
    private Logger logger = LogManager.getLogger(this.getClass());

    public EpTextKeyListener(JTextArea epText, UpperPanel upperPanel) {
        this.epText = epText;
        this.upperPanel = upperPanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        Statistic currentStats = upperPanel.getSession().getCurrentStats();
        String text = epText.getText();
        char userInput = e.getKeyChar();
        int caretPosition = epText.getCaretPosition();
        if (caretPosition < text.length()) {
            Color statusIndicatorColor = upperPanel.getStatusIndicatorColor();
            char requiredChar = text.charAt(caretPosition);
            boolean isCRLF = requiredChar == '\r' && caretPosition+1 < text.length() && text.charAt(caretPosition+1) == '\n';
            //with a [space] user can skip through unprintable and invisible characters
            if (userInput == requiredChar || userInput == ' ' || userInput == '\n' && isCRLF) {
                //correct letter typed
                upperPanel.setStatusIndicatorColor(DARK_GREEN);
                if (upperPanel.getSession().isStarted()) {
                    currentStats.setCount(currentStats.getCount() + 1);
                    upperPanel.getSession().getMomentarySpeedLettersTimeList().offerFirst(System.currentTimeMillis());
                }
                if (isCRLF) {
                    epText.moveCaretPosition(caretPosition + 2);; //extra skip for "\r\n" new line characters combo
                } else {
                    epText.moveCaretPosition(caretPosition + 1);
                }
            } else {
                //incorrect letter typed
                upperPanel.setStatusIndicatorColor(RED);
                if (upperPanel.getSession().isStarted()) {
                    currentStats.setErrCount(currentStats.getErrCount() + 1);
                }
            }
            if (!statusIndicatorColor.equals(upperPanel.getStatusIndicatorColor())) {
                upperPanel.repaint();
            }

            epText.setSelectionStart(epText.getCaretPosition());
            epText.setSelectionEnd(epText.getCaretPosition());
            upperPanel.updateLbCountErrCount();
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
