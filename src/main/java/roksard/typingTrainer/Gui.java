package roksard.typingTrainer;

import roksard.json_serializer.JsonSerializer;
import roksard.typingTrainer.listeners.EpTextKeyListener;
import roksard.typingTrainer.listeners.FileLoadActionListener;
import roksard.typingTrainer.listeners.MainWindowListener;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Gui {
    final static String CONFIG_FILE = "settings.p";
    final static JsonSerializer<Config> serializer = new JsonSerializer<>(Config.class);
    final static Config config = serializer.load(CONFIG_FILE, Config.DEFAULT);
    static JFrame frame;
    static final String TITLE = "typing Trainer";
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

        MainJPanel jpanel = new MainJPanel();
        Container contentPane = frame.getContentPane();
        contentPane.add(jpanel, BorderLayout.PAGE_START);

        JTextArea epText = new JTextArea();
        epText.setText("Hello World!");

        epText.setLineWrap(true);
        epText.setEditable(false);
        epText.getCaret().setVisible(true);
        epText.addKeyListener(new EpTextKeyListener(epText, jpanel));


        JScrollPane jScrollPane = new JScrollPane(epText, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contentPane.add(jScrollPane, BorderLayout.CENTER);
//        jScrollPane.setPreferredSize(new Dimension(700, 400));

        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("File");
        MenuItem menuItem = new MenuItem("Load");
        FileLoadActionListener fileLoadActionListener = new FileLoadActionListener(frame, epText, config);
        menuItem.addActionListener(fileLoadActionListener);
        menu.add(menuItem);
        menuBar.add(menu);
        frame.setMenuBar(menuBar);

        frame.setLocation(config.getWinX(), config.getWinY());
        frame.setPreferredSize(new Dimension(config.getWinW(), config.getWinH()));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new MainWindowListener(frame, config, serializer, CONFIG_FILE));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.repaint();

        File file = new File(config.getFileName());
        if (file.exists()) {
            fileLoadActionListener.loadFile(file, config.getFilePos());
        }
    }
}
