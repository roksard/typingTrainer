package roksard.typingTrainer.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roksard.typingTrainer.Session;
import roksard.typingTrainer.UpperPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class BtStartActionListener implements ActionListener {
    @Autowired
    private UpperPanel upperPanel;

    @Override
    public void actionPerformed(ActionEvent e) {
        Session session = upperPanel.getSession();
        JButton btStart = upperPanel.getBtStart();
        session.setStarted(!session.isStarted());
        if (session.isStarted()) {
            btStart.setText("Stop");
            session.setStartedTime(Instant.now());
            session.resetMomentarySpeed();
            Timer timer = new Timer(true);
            session.setTimer(timer);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    session.removeOldLetterTimes();
                    upperPanel.updateLbTime();
                    upperPanel.updateLbTypingSpeed();
                }
            }, 500, 100);
        } else {
            session.getTimer().cancel();
            session.recalcTimeMs();
            session.setStartedTime(null);
            btStart.setText("Start");
            if (session.getCurrentStats().getTimeMs() == null) {
                session.getCurrentStats().setTimeMs(0L);
            }
        }
    }
}
