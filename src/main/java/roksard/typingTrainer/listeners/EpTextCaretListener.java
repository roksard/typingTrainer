package roksard.typingTrainer.listeners;

import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.nio.charset.StandardCharsets;

@Setter
public class EpTextCaretListener implements CaretListener {
    JTextArea epText;
    FileLoadActionListener fileLoadActionListener;
    Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void caretUpdate(CaretEvent e) {
        double relativePos = epText.getCaretPosition() / (double)epText.getText().length();
        if (relativePos < 0.1 || relativePos > 0.9 ) {
            fileLoadActionListener.loadFile(fileLoadActionListener.getCurrentFile(), fileLoadActionListener.calcFilePos());
        }
    }
}
