package roksard.typingTrainer.listeners;

import roksard.typingTrainer.Config;

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

public class FileLoadActionListener implements ActionListener {
    private JFrame frame;
    private JTextArea epText;
    private Config config;

    public FileLoadActionListener(JFrame frame, JTextArea epText, Config config) {
        this.frame = frame;
        this.epText = epText;
        this.config = config;
    }

    public void loadFile(File file, Integer position) {
        loadFile(file);
        if (position != null) {
            epText.setCaretPosition(position);
        }
    }

    public void loadFile(File file) {
        StringBuilder text = new StringBuilder();
        try (
            FileChannel fileChannel = FileChannel.open(Paths.get(file.getAbsolutePath()));
        ) {
            ByteBuffer bb = ByteBuffer.allocate(1024*500);
            int read = 0;
            while ((read = fileChannel.read(bb)) > 0) {
                bb.limit(read);
                bb.rewind();
                CharBuffer charBuffer = StandardCharsets.UTF_8.decode(bb);
                text.append(charBuffer.toString());
                bb.clear();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        epText.setText(text.toString());
        epText.setCaretPosition(0);
        epText.getCaret().setVisible(true);
        config.setFileName(file.getAbsolutePath());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FileDialog fileDialog = new FileDialog(frame, "Load file");
        fileDialog.setVisible(true);
        Arrays.stream(fileDialog.getFiles())
                .findFirst()
                .ifPresent(this::loadFile);
    }
}
