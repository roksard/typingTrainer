package roksard.typingTrainer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Listeners {
    public ActionListener fileLoad = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("file Load ??");
        }
    };
}
