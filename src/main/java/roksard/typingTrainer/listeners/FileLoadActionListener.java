package roksard.typingTrainer.listeners;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import roksard.typingTrainer.Main;
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
import java.util.concurrent.Future;

@Setter
@Getter
public class FileLoadActionListener implements ActionListener {
    private JFrame frame;
    private JTextArea epText;
    private Config config;
    private ExecutorService executorService;
    private long seekPos;
    final int chunkSize = 50000;
    final int posInChunk = chunkSize * 50 / 100; //at 50% of chunk
    private File currentFile;
    Logger logger = LogManager.getLogger(this.getClass());
    private Future<?> loadFile;

    public FileLoadActionListener(JFrame frame, JTextArea epText, Config config, ExecutorService executorService) {
        this.frame = frame;
        this.epText = epText;
        this.config = config;
        this.executorService = executorService;
    }

    public void loadFile(File file, long filePos) {
        synchronized (this) {
            if (loadFile != null && !loadFile.isDone()) {
                logger.debug("load file interrupted, because already running");
                return;
            }
            loadFile = executorService.submit(new Runnable() {
                @Override
                public void run() {
                    logger.debug(">>load file at pos: {} ", filePos);
                    long seekPos = Math.max(0, filePos - posInChunk);
                    if (currentFile == file && getSeekPos() == 0 && seekPos == 0) {
                        logger.debug("trying to open file at 0 pos twice");
                        return; //trying to open same file at same pos
                    }
                    if (currentFile == file && getSeekPos() + chunkSize >= file.length() && seekPos >= getSeekPos()) {
                        logger.debug("trying to open file at EOF twice");
                        return; //trying to open same file at same pos (eof)
                    }
                    CharBuffer charBuffer;
                    try (
                            FileChannel fileChannel = FileChannel.open(Paths.get(file.getAbsolutePath()));
                    ) {
                        fileChannel.position(seekPos);
                        ByteBuffer bb = ByteBuffer.allocate(chunkSize);
                        int read = fileChannel.read(bb);
                        bb.limit(read);
                        bb.rewind();
                        charBuffer = StandardCharsets.UTF_8.decode(bb);
                    } catch (Exception e) {
                        logger.error("Error: ", e);
                        throw new RuntimeException(e);
                    }
                    currentFile = file;
                    epText.setText(charBuffer.toString());
                    epText.setCaretPosition(calcCaretPosOffsetByBytes(epText.getText(), (int) (filePos - seekPos)));
                    epText.getCaret().setVisible(true);
                    config.setFileName(file.getAbsolutePath());
                    setSeekPos(seekPos);
                    System.out.println("Startup time: " + (System.currentTimeMillis() - Main.startTime));
                }
            });
        }
    }

    public int calcCaretPosOffsetByBytes(String text, int byteOffset) {
        for (int i = 0; i < text.length(); i++) {
            if (text.substring(0, i).getBytes(StandardCharsets.UTF_8).length >= byteOffset) {
                logger.debug("calcCaretPosOffsetByBytes result {}", i);
                return i;
            }
        }
        return 0;
    }

    public long calcFilePosByCurrentPosAndCaretPos() {
        return getSeekPos() + epText.getText().substring(0, epText.getCaretPosition()).getBytes(StandardCharsets.UTF_8).length;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FileDialog fileDialog = new FileDialog(frame, "Load file");
        fileDialog.setVisible(true);
        Arrays.stream(fileDialog.getFiles())
                .findFirst()
                .ifPresent(file -> loadFile(file, 0));
    }
}
