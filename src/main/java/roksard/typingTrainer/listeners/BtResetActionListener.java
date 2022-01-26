package roksard.typingTrainer.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roksard.typingTrainer.UpperPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class BtResetActionListener implements ActionListener {
    @Autowired
    private UpperPanel upperPanel;

    @Override
    public void actionPerformed(ActionEvent e) {
        upperPanel.getSession().resetCurrentStats();
    }
}
