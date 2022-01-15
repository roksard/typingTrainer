package roksard.typingTrainer.listeners;

import lombok.AllArgsConstructor;
import roksard.typingTrainer.UpperPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@AllArgsConstructor
public class BtResetActionListener implements ActionListener {
    private UpperPanel upperPanel;

    @Override
    public void actionPerformed(ActionEvent e) {
        upperPanel.getSession().resetCurrentStats();
    }
}
