package roksard.typingTrainer;

import javax.swing.*;
import java.awt.*;

public class MainJPanel extends JPanel {
    private static final roksard.graphicsAwt.Graphics GRAPHICS = new roksard.graphicsAwt.Graphics();
    private Color statusIndicatorColor = Color.getHSBColor(0.33f, 1, 0.5f);

    //init
    {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(50, 15));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        GRAPHICS.drawRect(g, 5, 5, this.getWidth() - 10, 5, this.getStatusIndicatorColor());
    }

    public Color getStatusIndicatorColor() {
        return statusIndicatorColor;
    }

    public void setStatusIndicatorColor(Color statusIndicatorColor) {
        this.statusIndicatorColor = statusIndicatorColor;
    }
}
