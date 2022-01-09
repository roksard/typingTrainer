package roksard.typingTrainer.listeners;

import roksard.json_serializer.JsonSerializer;
import roksard.typingTrainer.Config;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainWindowListener implements WindowListener {
    private JFrame frame;
    private Config config;
    private JsonSerializer<Config> serializer;
    private String CONFIG_FILE;

    public MainWindowListener(JFrame frame, Config config, JsonSerializer<Config> serializer, String CONFIG_FILE) {
        this.frame = frame;
        this.config = config;
        this.serializer = serializer;
        this.CONFIG_FILE = CONFIG_FILE;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        config.setWinX((int)frame.getLocation().getX());
        config.setWinY((int)frame.getLocation().getY());
        config.setWinW(frame.getWidth());
        config.setWinH(frame.getHeight());
        serializer.save(CONFIG_FILE, config);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
