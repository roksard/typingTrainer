package roksard.typingTrainer;

import roksard.typingTrainer.listeners.EpTextKeyListener;
import roksard.typingTrainer.listeners.FileLoadActionListener;
import roksard.typingTrainer.listeners.MainWindowListener;

import javax.swing.*;
import java.awt.*;

public class Gui {
    final static String CONFIG_FILE = "settings.p";
//    final static JsonSerializer<Config> serializer = new JsonSerializer<>(Config.class);
//    final static Config config = serializer.load(CONFIG_FILE, Config.DEFAULT);
    static JFrame frame;
    static final String TITLE = "fileSearch by content";
    static final roksard.graphicsAwt.Graphics GRAPHICS = new roksard.graphicsAwt.Graphics();

    public static void main(String[] args) {
        frame = new JFrame();
        frame.setTitle(TITLE);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                JOptionPane.showMessageDialog(frame, "Error: " + e.toString() + ": " + e.getMessage());
            }
        });
        frame.addWindowListener(new MainWindowListener());
        MainJPanel jpanel = new MainJPanel();

        JEditorPane epText = new JEditorPane();
        epText.setText("Halliehaloe");
        epText.setBounds(5, 15, 390, 50);
        epText.setBorder(BorderFactory.createEtchedBorder());
        epText.setEditable(false);
        epText.getCaret().setSelectionVisible(true);
        epText.getCaret().setVisible(true);
        epText.getCaret().setDot(3);
        epText.addKeyListener(new EpTextKeyListener(epText, jpanel));
        jpanel.add(epText);

        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("File");
        MenuItem menuItem = new MenuItem("Load");
        menuItem.addActionListener(new FileLoadActionListener());
        menu.add(menuItem);
        menuBar.add(menu);
        frame.setMenuBar(menuBar);

        jpanel.setLayout(null);
        frame.add(jpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.repaint();
    }
}
