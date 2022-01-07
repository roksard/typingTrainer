package roksard.typingTrainer.listeners;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;

public class FileLoadActionListener implements ActionListener {
    JFrame frame;
    JEditorPane epText;

    public FileLoadActionListener(JFrame frame, JEditorPane epText) {
        this.frame = frame;
        this.epText = epText;
    }

    void loadFile(File file) {
        StringBuilder text = new StringBuilder();
        try (
            FileChannel fileChannel = FileChannel.open(Paths.get(file.getAbsolutePath()));
        ) {
            ByteBuffer bb = ByteBuffer.allocate(1024);
            int read = 0;
            while ((read = fileChannel.read(bb)) > 0) {
                bb.limit(read);
                bb.rewind();
                CharBuffer charBuffer = StandardCharsets.UTF_8.decode(bb);
                text.append(charBuffer.toString());
                bb.clear();
            };
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        epText.setText(text.toString());
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
