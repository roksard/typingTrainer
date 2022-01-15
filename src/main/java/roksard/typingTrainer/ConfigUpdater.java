package roksard.typingTrainer;

import lombok.AllArgsConstructor;
import roksard.json_serializer.JsonSerializer;
import roksard.typingTrainer.pojo.Config;
import roksard.typingTrainer.pojo.Statistic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ConfigUpdater {
    private JsonSerializer<Config> serializer;
    private String CONFIG_FILE;
    private JFrame frame;
    private JTextArea epText;
    private Config config;
    private Session session;


    public void updateConfigAndSave() {
        config.setWinX((int)frame.getLocation().getX());
        config.setWinY((int)frame.getLocation().getY());
        config.setWinW(frame.getWidth());
        config.setWinH(frame.getHeight());
        config.setFilePos(epText.getCaretPosition());
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
