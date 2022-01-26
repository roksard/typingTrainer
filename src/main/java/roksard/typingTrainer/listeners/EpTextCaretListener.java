package roksard.typingTrainer.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

@Component
public class EpTextCaretListener implements CaretListener {
    @Autowired
    JTextArea epText;
    @Autowired
    FileLoadActionListener fileLoadActionListener;

    Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void caretUpdate(CaretEvent e) {
        double relativePos = epText.getCaretPosition() / (double)epText.getText().length();
        if (relativePos < 0.1 || relativePos > 0.9 ) {
            fileLoadActionListener.loadFile(fileLoadActionListener.getCurrentFile(), fileLoadActionListener.calcFilePosByCurrentPosAndCaretPos());
        }
    }
}
