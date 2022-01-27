package roksard.typingTrainer.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roksard.typingTrainer.ConfigUpdater;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

@Component
public class MainWindowListener implements WindowListener {
    @Autowired
    private ConfigUpdater configUpdater;

    private Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        logger.debug("closing main window");
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
