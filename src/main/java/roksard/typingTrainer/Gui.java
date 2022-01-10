package roksard.typingTrainer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import roksard.json_serializer.JsonSerializer;
import roksard.typingTrainer.listeners.EpTextFocusListener;
import roksard.typingTrainer.listeners.EpTextKeyListener;
import roksard.typingTrainer.listeners.FileLoadActionListener;
import roksard.typingTrainer.listeners.MainWindowListener;
import roksard.typingTrainer.pojo.Config;

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
    static Logger LOGGER = LogManager.getLogger(Gui.class);

    public static void main(String[] args) {
        LOGGER.debug("Initialisation start");
        frame = new JFrame();
        frame.setTitle(TITLE);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                JOptionPane.showMessageDialog(frame, "Error: " + e.toString() + ": " + e.getMessage());
                LOGGER.error("Error: ", e);
                System.exit(-1);
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
        epText.addFocusListener(new EpTextFocusListener(epText));


        JScrollPane jScrollPane = new JScrollPane(epText, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contentPane.add(jScrollPane, BorderLayout.CENTER);

        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("File");
        MenuItem menuItem = new MenuItem("Load");
        FileLoadActionListener fileLoadActionListener = new FileLoadActionListener(frame, epText, config);
        menuItem.addActionListener(fileLoadActionListener);
        menu.add(menuItem);
        menuBar.add(menu);
        frame.setMenuBar(menuBar);

        if (config.getWinX() != null && config.getWinY() != null && config.getWinW() != null && config.getWinH() != null) {
            frame.setLocation(config.getWinX(), config.getWinY());
            frame.setPreferredSize(new Dimension(config.getWinW(), config.getWinH()));
        } else {
            frame.setPreferredSize(new Dimension(700, 400));
            frame.setLocationRelativeTo(null);
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ConfigUpdater configUpdater = new ConfigUpdater(serializer, CONFIG_FILE, frame, epText, config);
        frame.addWindowListener(new MainWindowListener(configUpdater));
        frame.pack();

        frame.setVisible(true);
        frame.repaint();

        if (config.getFileName() != null) {
            File file = new File(config.getFileName());
            if (file.exists()) {
                fileLoadActionListener.loadFile(file, config.getFilePos());
            }
        }

        LOGGER.debug("Initialisation succesful");
    }
}
