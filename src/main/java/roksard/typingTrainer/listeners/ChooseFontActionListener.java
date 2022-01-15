package roksard.typingTrainer.listeners;

import lombok.AllArgsConstructor;
import paint.JFontChooser;
import roksard.typingTrainer.pojo.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;

@AllArgsConstructor
public class ChooseFontActionListener implements ActionListener {
    private JFrame frame;
    private JTextArea epText;
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
