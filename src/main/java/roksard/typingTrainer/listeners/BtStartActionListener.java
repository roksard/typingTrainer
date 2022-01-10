package roksard.typingTrainer.listeners;

import lombok.AllArgsConstructor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@AllArgsConstructor
public class BtStartActionListener implements ActionListener {
    private JButton btStart;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (btStart.getText().equals("Start")) {
            btStart.setText("Stop");
        } else {
            btStart.setText("Start");
        }
    }
}
