package roksard.typingTrainer;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import roksard.json_serializer.JsonSerializer;
import roksard.typingTrainer.listeners.FileLoadActionListener;
import roksard.typingTrainer.pojo.Config;
import roksard.typingTrainer.pojo.Statistic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ConfigUpdater {
    private final JsonSerializer<Config> serializer;
    private final String CONFIG_FILE;
    private final JFrame frame;
    private final JTextArea epText;
    private final Config config;
    private final Session session;
    private final FileLoadActionListener fileLoadActionListener;
    private Logger logger = LogManager.getLogger(this.getClass());


    public void updateConfigAndSave() {
        config.setWinX((int)frame.getLocation().getX());
        config.setWinY((int)frame.getLocation().getY());
        config.setWinW(frame.getWidth());
        config.setWinH(frame.getHeight());
        config.setFilePos(fileLoadActionListener.calcFilePos());
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
        serializer.save(CONFIG_FILE, config);
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
