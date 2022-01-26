package roksard.typingTrainer.listeners;

import lombok.AllArgsConstructor;
import roksard.typingTrainer.ConfigUpdater;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

@RequiredArgsConstructor
public class MainWindowListener implements WindowListener {
    private final ConfigUpdater configUpdater;
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
