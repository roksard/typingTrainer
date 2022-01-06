package roksard.graphicsAwt;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Graphics {
    public void drawCircle(java.awt.Graphics g, double x, double y, double size, Color color) {
        double s = 1; //some scale for coorinates
        Graphics2D g2d = (Graphics2D) g;
        Ellipse2D.Double circle = new Ellipse2D.Double(x * s, y * s, size, size);
        g2d.setColor(color);
        g2d.fill(circle);
    }
}
