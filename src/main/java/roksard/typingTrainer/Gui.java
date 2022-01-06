package roksard.typingTrainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Gui {
    final static String CONFIG_FILE = "settings.p";
//    final static JsonSerializer<Config> serializer = new JsonSerializer<>(Config.class);
//    final static Config config = serializer.load(CONFIG_FILE, Config.DEFAULT);
    static JFrame frame;
    static final String TITLE = "fileSearch by content";
    static final roksard.graphicsAwt.Graphics GRAPHICS = new roksard.graphicsAwt.Graphics();
    static final Color DARK_GREEN = Color.getHSBColor(0.33f, 1, 0.5f);
    static final Color RED = Color.RED;

    public static void main(String[] args) {
        frame = new JFrame();
        frame.setTitle(TITLE);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                JOptionPane.showMessageDialog(frame, "Error: " + e.toString() + ": " + e.getMessage());
            }
        });
        frame.addWindowListener(getMainWindowListener());
        MainJPanel jpanel = new MainJPanel();

        JEditorPane epText = new JEditorPane();
        epText.setText("Halliehaloe");
        epText.setBounds(5, 15, 390, 50);
        epText.setBorder(BorderFactory.createEtchedBorder());
        epText.setEditable(false);
        epText.getCaret().setSelectionVisible(true);
        epText.getCaret().setVisible(true);
        epText.getCaret().setDot(3);

        epText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                int caretPosition = epText.getCaretPosition();
                if (caretPosition < epText.getText().length()) {
                    Color statusIndicatorColor = jpanel.getStatusIndicatorColor();
                    if (e.getKeyChar() == epText.getText().charAt(caretPosition)) {
                        jpanel.setStatusIndicatorColor(DARK_GREEN);
                    } else {
                        jpanel.setStatusIndicatorColor(RED);
                    }
                    if (!statusIndicatorColor.equals(jpanel.getStatusIndicatorColor()))
                        jpanel.repaint();

                    epText.moveCaretPosition(caretPosition + 1);
                    epText.setSelectionStart(epText.getCaretPosition());
                }

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        jpanel.add(epText);

        jpanel.setLayout(null);
        frame.add(jpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.repaint();
    }

    static WindowListener getMainWindowListener() {
        return new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
//                config.setX((int)frame.getLocation().getX());
//                config.setY((int)frame.getLocation().getY());
//                serializer.save(CONFIG_FILE, config);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        };
    }
}
