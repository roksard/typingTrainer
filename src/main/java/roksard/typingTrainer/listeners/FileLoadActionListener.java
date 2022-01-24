package roksard.typingTrainer.listeners;

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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FileLoadActionListener implements ActionListener {
    private JFrame frame;
    private JTextArea epText;
    private Config config;
    private ExecutorService executorService;

    public FileLoadActionListener(JFrame frame, JTextArea epText, Config config, ExecutorService executorService) {
        this.frame = frame;
        this.epText = epText;
        this.config = config;
        this.executorService = executorService;
    }

    public void loadFile(File file, Integer position) {
        loadFile(file);
        if (position != null) {
            epText.setCaretPosition(position);
        }
    }

    public void loadFile(File file) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                StringBuilder text = new StringBuilder();
                try (
                        FileChannel fileChannel = FileChannel.open(Paths.get(file.getAbsolutePath()));
                ) {
                    ByteBuffer bb = ByteBuffer.allocate(1024 * 500);
                    int read = 0;
                    while ((read = fileChannel.read(bb)) > 0) {
                        bb.limit(read);
                        bb.rewind();
                        CharBuffer charBuffer = StandardCharsets.UTF_8.decode(bb);
                        text.append(charBuffer.toString());
                        bb.clear();
                        if (Thread.interrupted()) {
                            return;
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (text.length() > 500_000) {
                    JOptionPane.showMessageDialog(frame, "Error: File size is too big, please try to split it, max file < 500kb" );
                    return;
                }
                epText.setText(text.toString());
                epText.setCaretPosition(0);
                epText.getCaret().setVisible(true);
                config.setFileName(file.getAbsolutePath());
            }
        });
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
