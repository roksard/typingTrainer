package roksard.typingTrainer;

import lombok.Getter;
import roksard.typingTrainer.listeners.BtStartActionListener;

import javax.swing.*;
import java.awt.*;

@Getter
public class MainJPanel extends JPanel {
    private static final roksard.graphicsAwt.Graphics GRAPHICS = new roksard.graphicsAwt.Graphics();
    private Color statusIndicatorColor = Color.getHSBColor(0.33f, 1, 0.5f);
    private JLabel lbCharCount;
    private JLabel lbErrorCount;
    private JLabel lbTime;
    private JButton btStart;

    //init
    {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(50, 50));
        setLayout(new FlowLayout(FlowLayout.LEFT));
        lbCharCount = new JLabel();
        add(lbCharCount);
        lbErrorCount = new JLabel();
        add(lbErrorCount);
        setLbCharErrorCount(0, 0);
        lbTime = new JLabel();
        add(lbTime);
        setLbTime("00:00");
        btStart = new JButton("Start");
        btStart.addActionListener(new BtStartActionListener(btStart));
        add(btStart);
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

    public void setLbCharErrorCount(Integer typedCount, Integer errorCount) {
        float errPercent;
        if (typedCount == 0) {
            errPercent = 0;
        } else {
            errPercent = errorCount / (float)typedCount * 100;
        }
        this.lbCharCount.setText("Typed: " + typedCount);
        this.lbErrorCount.setText("Errors: " + errorCount + " (" + String.format("%.2f", errPercent) + "%)");
    }

    public void setLbTime(String time) {
        this.lbTime.setText("Time: " + time);
    }
}
