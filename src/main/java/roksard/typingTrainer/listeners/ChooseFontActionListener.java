package roksard.typingTrainer.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import paint.JFontChooser;
import roksard.typingTrainer.pojo.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class ChooseFontActionListener implements ActionListener {
    @Autowired
    private JFrame frame;
    @Autowired
    private JTextArea epText;
    @Autowired
    private Config config;

    @Override
    public void actionPerformed(ActionEvent e) {
        JFontChooser jf = new JFontChooser();
        jf.setSelectedFont(epText.getFont());
        int result = jf.showDialog(frame);
        if (result == JFontChooser.OK_OPTION) {
            Font font = jf.getSelectedFont();
            String fontName = font.getName();
            int fontStyle = font.getStyle();
            int fontSize = font.getSize();
            Font font2 = new Font(fontName, fontStyle, fontSize);
            System.out.println(font.equals(font2));
            setFont(jf.getSelectedFont());
        }
    }

    public void setFont(Font font) {
        epText.setFont(font);
    }
}
