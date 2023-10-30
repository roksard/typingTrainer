package roksard.typingTrainer;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import roksard.json_serializer.JsonSerializer;
import roksard.typingTrainer.listeners.FileLoadActionListener;
import roksard.typingTrainer.pojo.Config;
import roksard.typingTrainer.pojo.Statistic;

@Component
public class ConfigUpdater {
	@Autowired
	private JsonSerializer<Config> serializer;
	@Autowired
	private String configFile;
	@Autowired
	private JFrame frame;
	@Autowired
	private JTextArea epText;
	@Autowired
	private Config config;
	@Autowired
	private Session session;
	@Autowired
	private FileLoadActionListener fileLoadActionListener;

	private Logger logger = LogManager.getLogger(this.getClass());

	public void updateConfigAndSave() {
		config.setWinX((int) frame.getLocation().getX());
		config.setWinY((int) frame.getLocation().getY());
		config.setWinW(frame.getWidth());
		config.setWinH(frame.getHeight());
		config.setFilePos(fileLoadActionListener.calcFilePosByCurrentPosAndCaretPos());
		logger.debug("save file pos: {}", config.getFilePos());
		session.recalcTimeMs();
		List<Statistic> statisticList = new ArrayList<>(session.getStatisticList());
		if (session.getCurrentStats().getTimeMs() > 0) {
			statisticList.add(0, session.getCurrentStats());
		}
		config.setStatistic(statisticList);
		config.setFontName(epText.getFont().getFontName());
		config.setFontStyle(epText.getFont().getStyle());
		config.setFontSize(epText.getFont().getSize());
		serializer.save(configFile, config);
	}

	public Font getFont() {
		Font font = null;
		try {
			font = new Font(config.getFontName(), config.getFontStyle(), config.getFontSize());
		} catch (Throwable e) {
		}
		return font;
	}
}
