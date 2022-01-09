package roksard.typingTrainer.listeners;

import lombok.AllArgsConstructor;
import roksard.typingTrainer.ConfigUpdater;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

@AllArgsConstructor
public class MainWindowListener implements WindowListener {
    private final ConfigUpdater configUpdater;

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        configUpdater.updateConfigAndSave();
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
