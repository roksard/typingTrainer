package roksard.typingTrainer;

import com.sun.corba.se.impl.presentation.rmi.ExceptionHandler;
import lombok.Getter;
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

@Getter
public class Gui {
    final String CONFIG_FILE = "settings.p";
    final JsonSerializer<Config> serializer = new JsonSerializer<>(Config.class);
    final Config config = serializer.load(CONFIG_FILE, Config.DEFAULT);
    JFrame frame;
    final String TITLE = "typing Trainer";
    final roksard.graphicsAwt.Graphics GRAPHICS = new roksard.graphicsAwt.Graphics();
    Logger LOGGER = LogManager.getLogger(Main.class);
    final Thread.UncaughtExceptionHandler exceptionHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            JOptionPane.showMessageDialog(frame, "Error: " + e.toString() + ": " + e.getMessage());
            LOGGER.error("Error: ", e);
            System.exit(-1);
        }
    };


    public void start() {
        LOGGER.debug("Initialisation start");
        Session session = new Session();
        session.setStatisticList(config.getStatistic());

        frame = new JFrame();
        frame.setTitle(TITLE);

        MainJPanel jpanel = new MainJPanel(session);
        Container contentPane = frame.getContentPane();
        contentPane.add(jpanel, BorderLayout.PAGE_START);

        JTextArea epText = new JTextArea("This program allows you to read a book and simultaneously type it on a keyboard.\n" +
                "Use Menu File -> Load to load a file. This can be some book or any other text, that you would like to practice your typing on. \n" +
                "You can start typing right now, but if you want to see statistics, first press Start button. This will create a new session and will start counting your current statistic. You can pause session by pressing Stop, or you can create a new session by pressing Reset. ");

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
        ConfigUpdater configUpdater = new ConfigUpdater(serializer, CONFIG_FILE, frame, epText, config, session);
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
