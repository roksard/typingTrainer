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
public class BtResetActionListener implements ActionListener {
    private MainJPanel mainJPanel;

    @Override
    public void actionPerformed(ActionEvent e) {
        mainJPanel.getSession().resetCurrentStats();
    }
}
