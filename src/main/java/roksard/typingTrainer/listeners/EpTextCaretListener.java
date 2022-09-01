package roksard.typingTrainer.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import java.awt.*;

@Component
public class EpTextCaretListener implements CaretListener {
    @Autowired
    JTextArea epText;
    @Autowired
    JScrollPane epTextScrollPane;
    @Autowired
    FileLoadActionListener fileLoadActionListener;

    Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void caretUpdate(CaretEvent e) {
        try {
            double caretYPos = epText.modelToView(epText.getCaretPosition()).getY();
            Rectangle visibleRect = epText.getVisibleRect();
            logger.debug("font height {}", epText.getFontMetrics(epText.getFont()).getHeight());
            if ((caretYPos + (epText.getFontMetrics(epText.getFont()).getHeight()*2))
                    > (visibleRect.getY() + visibleRect.getHeight())
            ) {
                JScrollBar scrollBar = epTextScrollPane.getVerticalScrollBar();
                scrollBar.setValue(scrollBar.getValue() + (int)Math.round(visibleRect.getHeight()));
            }
        } catch (BadLocationException badLocationException) {
            logger.error("Could not convert caret pos into XY view coordinate", badLocationException);
        }

        double relativePos = epText.getCaretPosition() / (double)epText.getText().length();
        if (relativePos < 0.1 || relativePos > 0.9 ) {
            fileLoadActionListener.loadFile(fileLoadActionListener.getCurrentFile(), fileLoadActionListener.calcFilePosByCurrentPosAndCaretPos());
        }
    }
}
