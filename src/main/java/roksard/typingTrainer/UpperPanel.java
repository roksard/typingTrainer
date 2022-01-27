package roksard.typingTrainer;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roksard.typingTrainer.listeners.BtResetActionListener;
import roksard.typingTrainer.listeners.BtStartActionListener;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
@Getter
public class UpperPanel extends JPanel {
    private static final roksard.graphicsAwt.Graphics GRAPHICS = new roksard.graphicsAwt.Graphics();
    private Color statusIndicatorColor = Color.getHSBColor(0.33f, 1, 0.5f);
    private JLabel lbCharCount;
    private JLabel lbErrorCount;
    private JLabel lbTime;
    private JLabel lbSpeed;
    private JButton btStart;
    private JButton btReset;
    @Autowired
    private Session session;
    @Autowired
    BtStartActionListener btStartActionListener;
    @Autowired
    BtResetActionListener btResetActionListener;

    @PostConstruct
    private void init() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(50, 50));
        setLayout(new FlowLayout(FlowLayout.LEFT));

        lbCharCount = new JLabel();
        add(lbCharCount);
        lbErrorCount = new JLabel();
        add(lbErrorCount);

        lbTime = new JLabel();
        add(lbTime);

        lbSpeed = new JLabel();
        add(lbSpeed);

        btStart = new JButton("Start");
        btStart.setFocusable(false);
        btStart.addActionListener(btStartActionListener);
        add(btStart);

        btReset = new JButton("Reset");
        btReset.setFocusable(false);
        btReset.addActionListener(btResetActionListener);
        add(btReset);

        resetAllLabels();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        GRAPHICS.drawRect(g, 5, this.getHeight() - 10, this.getWidth() - 10, 5, this.getStatusIndicatorColor());
    }

    public Color getStatusIndicatorColor() {
        return statusIndicatorColor;
    }

    public void setStatusIndicatorColor(Color statusIndicatorColor) {
        this.statusIndicatorColor = statusIndicatorColor;
    }

    public void setLbCharErrorCount(Long typedCount, Long errorCount) {
        float errPercent;
        if (typedCount == 0) {
            errPercent = 0;
        } else {
            errPercent = errorCount / (float)(typedCount + errorCount) * 100;
        }
        this.lbCharCount.setText("Typed: " + typedCount);
        this.lbErrorCount.setText("Errors: " + errorCount + " (" + String.format("%.2f", errPercent) + "%)");
    }

    public void setLbTime(String time) {
        this.lbTime.setText("Time: " + time);
    }

    public void updateLbCountErrCount() {
        setLbCharErrorCount(session.getCurrentStats().getCount(), session.getCurrentStats().getErrCount());
    }

    public void updateLbTypingSpeed() { //letters per min
        lbSpeed.setText("Speed: " + String.format("%.0f / Average: %.0f letters/min)", session.calcMomentaryTypingSpeed(), session.calcAverageTypingSpeed()));
    }

    public void updateLbTime() {
        setLbTime(formatTimeMs(session.calcCurrentRunningTime()));
    }

    public String formatTimeMs(long timeMs) {
        int seconds = (int)Math.round(timeMs / 1000.0);
        int minutes = seconds / 60;
        int hours = minutes / 60;
        minutes = minutes % 60;
        seconds = seconds % 60;
        StringBuilder result = new StringBuilder();
        result.insert(0, String.format("%02d", seconds));
        result.insert(0, String.format("%02d:", minutes));
        if (hours > 0) {
            result.insert(0, String.format("%02d:", hours));
        }
        return result.toString();
    }

    public void resetAllLabels() {
        updateLbCountErrCount();
        updateLbTypingSpeed();
        setLbTime(formatTimeMs(0));
    }

}
