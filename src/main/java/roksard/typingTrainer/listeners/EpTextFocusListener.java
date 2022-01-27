package roksard.typingTrainer.listeners;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

@Component
public class EpTextFocusListener implements FocusListener {
    @Autowired
    private JTextArea epText;

    @Override
    public void focusGained(FocusEvent e) {
        epText.getCaret().setVisible(true);
        epText.setSelectionStart(epText.getCaretPosition());
        epText.setSelectionEnd(epText.getCaretPosition());
    }

    @Override
    public void focusLost(FocusEvent e) {
        epText.getCaret().setVisible(true);
        epText.setSelectionStart(epText.getCaretPosition());
        epText.setSelectionEnd(epText.getCaretPosition());
    }
}
