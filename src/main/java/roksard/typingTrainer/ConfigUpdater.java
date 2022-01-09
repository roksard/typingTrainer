package roksard.typingTrainer;

import lombok.AllArgsConstructor;
import roksard.json_serializer.JsonSerializer;

import javax.swing.*;

@AllArgsConstructor
public class ConfigUpdater {
    private JsonSerializer<Config> serializer;
    private String CONFIG_FILE;
    private JFrame frame;
    private JTextArea epText;
    private Config config;


    public void updateConfigAndSave() {
        config.setWinX((int)frame.getLocation().getX());
        config.setWinY((int)frame.getLocation().getY());
        config.setWinW(frame.getWidth());
        config.setWinH(frame.getHeight());
        config.setFilePos(epText.getCaretPosition());
        serializer.save(CONFIG_FILE, config);
    }
}
