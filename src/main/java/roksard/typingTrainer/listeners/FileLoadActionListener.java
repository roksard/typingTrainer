package roksard.typingTrainer.listeners;

import java.awt.FileDialog;
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

import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import roksard.typingTrainer.Main;
import roksard.typingTrainer.pojo.Config;

@Component
public class FileLoadActionListener implements ActionListener {
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public void setEpText(JTextArea epText) {
		this.epText = epText;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

	public void setSeekPos(long seekPos) {
		this.seekPos = seekPos;
	}

	public void setCurrentFile(File currentFile) {
		this.currentFile = currentFile;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public void setLoadFile(Future<?> loadFile) {
		this.loadFile = loadFile;
	}

	public JFrame getFrame() {
		return frame;
	}

	public JTextArea getEpText() {
		return epText;
	}

	public Config getConfig() {
		return config;
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public long getSeekPos() {
		return seekPos;
	}

	public int getChunkSize() {
		return chunkSize;
	}

	public int getPosInChunk() {
		return posInChunk;
	}

	public File getCurrentFile() {
		return currentFile;
	}

	public Logger getLogger() {
		return logger;
	}

	public Future<?> getLoadFile() {
		return loadFile;
	}

	@Autowired
	private JFrame frame;
	@Autowired
	private JTextArea epText;
	@Autowired
	private Config config;
	@Autowired
	private ExecutorService executorService;

	private long seekPos;
	final int chunkSize = 50000;
	final int posInChunk = chunkSize * 50 / 100; // at 50% of chunk
	private File currentFile;
	Logger logger = LogManager.getLogger(this.getClass());
	private Future<?> loadFile;

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
						return; // trying to open same file at same pos
					}
					if (currentFile == file && getSeekPos() + chunkSize >= file.length() && seekPos >= getSeekPos()) {
						logger.debug("trying to open file at EOF twice");
						return; // trying to open same file at same pos (eof)
					}
					CharBuffer charBuffer;
					try (FileChannel fileChannel = FileChannel.open(Paths.get(file.getAbsolutePath()));) {
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
					logger.debug("Startup time: {}", System.currentTimeMillis() - Main.startTime);
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
		return getSeekPos()
				+ epText.getText().substring(0, epText.getCaretPosition()).getBytes(StandardCharsets.UTF_8).length;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		FileDialog fileDialog = new FileDialog(frame, "Load file");
		fileDialog.setVisible(true);
		Arrays.stream(fileDialog.getFiles()).findFirst().ifPresent(file -> loadFile(file, 0));
	}
}
