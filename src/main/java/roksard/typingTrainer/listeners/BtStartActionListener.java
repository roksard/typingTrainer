package roksard.typingTrainer.listeners;

import lombok.AllArgsConstructor;
import roksard.typingTrainer.MainJPanel;
import roksard.typingTrainer.Session;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

@AllArgsConstructor
public class BtStartActionListener implements ActionListener {
    private MainJPanel mainJPanel;

    @Override
    public void actionPerformed(ActionEvent e) {
        Session session = mainJPanel.getSession();
        JButton btStart = mainJPanel.getBtStart();
        session.setStarted(!session.isStarted());
        if (session.isStarted()) {
            btStart.setText("Stop");
            session.getCurrentStats().setStartTime(Instant.now());
            Timer timer = new Timer(true);
            session.setTimer(timer);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mainJPanel.setLbTime(session.formatTimeMs(session.calcCurrentTime()));
                    mainJPanel.updateLbTypingSpeed();
                }
            }, 500, 500);
        } else {
            session.getTimer().cancel();
            btStart.setText("Start");
            if (session.getCurrentStats().getTimeMs() == null) {
                session.getCurrentStats().setTimeMs(0L);
            }
            session.getCurrentStats().setTimeMs(session.calcCurrentTime());
        }
    }
}
