package roksard.typingTrainer;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import roksard.json_serializer.JsonSerializer;
import roksard.typingTrainer.listeners.*;
import roksard.typingTrainer.pojo.Config;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.concurrent.ExecutorService;

@Component
@Getter
public class Gui {
    final String TITLE = "typing Trainer";
    final roksard.graphicsAwt.Graphics GRAPHICS = new roksard.graphicsAwt.Graphics();
    Logger logger = LogManager.getLogger(Main.class);

    @Autowired
    JFrame frame;
    @Autowired
    Session session;
    @Autowired
    AnnotationConfigApplicationContext appContext;
    @Autowired
    JsonSerializer<Config> serializer;
    @Autowired
    Config config;
    @Autowired
    String configFile;
    @Autowired
    JTextArea epText;
    @Autowired
    JScrollPane epTextScrollPane;
    @Autowired
    ConfigUpdater configUpdater;
    @Autowired
    FileLoadActionListener fileLoadActionListener;
    @Autowired
    MainWindowListener mainWindowListener;
    @Autowired
    EpTextFocusListener epTextFocusListener;
    @Autowired
    EpTextKeyListener epTextKeyListener;
    @Autowired
    ChooseFontActionListener chooseFontActionListener;
    @Autowired
    ExecutorService executorService;
    @Autowired
    EpTextCaretListener epTextCaretListener;
    @Autowired
    UpperPanel upperPanel;

    @PostConstruct
    public void start() {

        logger.debug("Initialisation start");
        session.setStatisticList(config.getStatistic());

        frame.setTitle(TITLE);

        Container contentPane = frame.getContentPane();
        contentPane.add(upperPanel, BorderLayout.PAGE_START);

        epText.setText("This program allows you to read a book and simultaneously type it on a keyboard.\n" +
                "Use Menu File -> Load to load a file. This can be some book or any other text, that you would like to practice your typing on. \n" +
                "You can start typing right now, but if you want to see statistics, first press Start button. This will create a new session and will start counting your current statistic. You can pause session by pressing Stop, or you can create a new session by pressing Reset.");
        epText.setLineWrap(true);
        epText.setWrapStyleWord(true);
        epText.setEditable(false);
        epText.getCaret().setVisible(true);
        epText.addKeyListener(epTextKeyListener);
        epText.addFocusListener(epTextFocusListener);
        epText.addCaretListener(epTextCaretListener);

        contentPane.add(epTextScrollPane, BorderLayout.CENTER);

        MenuBar menuBar = new MenuBar();
        Menu mFile = new Menu("File");
        MenuItem miLoad = new MenuItem("Load");
        miLoad.addActionListener(fileLoadActionListener);
        mFile.add(miLoad);
        menuBar.add(mFile);

        Menu mSettings = new Menu("Settings");
        MenuItem miChooseFont = new MenuItem("Choose font");
        miChooseFont.addActionListener(chooseFontActionListener);
        mSettings.add(miChooseFont);
        menuBar.add(mSettings);
        frame.setMenuBar(menuBar);

        if (config.getWinX() != null && config.getWinY() != null && config.getWinW() != null && config.getWinH() != null) {
            frame.setLocation(config.getWinX(), config.getWinY());
            frame.setPreferredSize(new Dimension(config.getWinW(), config.getWinH()));
        } else {
            frame.setPreferredSize(new Dimension(700, 400));
            frame.setLocationRelativeTo(null);
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(mainWindowListener);
        frame.pack();

        frame.setVisible(true);
        frame.repaint();

        if (config.getFileName() != null) {
            File file = new File(config.getFileName());
            if (file.exists()) {
                logger.debug("load file at pos: {} ", config.getFilePos());
                fileLoadActionListener.loadFile(file, config.getFilePos());
            }
        }
        Font font = configUpdater.getFont();
        if (font != null) {
            chooseFontActionListener.setFont(font);
        }

        logger.debug("Initialisation succesful");
    }
}
