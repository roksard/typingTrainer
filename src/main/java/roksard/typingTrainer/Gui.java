package roksard.typingTrainer;

import roksard.json_serializer.JsonSerializer;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Future;

public class Gui {
    final static String CONFIG_FILE = "settings.p";
//    final static JsonSerializer<Config> serializer = new JsonSerializer<>(Config.class);
//    final static Config config = serializer.load(CONFIG_FILE, Config.DEFAULT);
    static JFrame frame;
    final static String TITLE = "fileSearch by content";

    public static void main(String[] args) {
        frame = new JFrame();
        frame.setTitle(TITLE);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                JOptionPane.showMessageDialog(frame, "Error: " + e.toString() + ": " + e.getMessage());
            }
        });
//        frame.setLocation(config.getX(), config.getY());
        frame.addWindowListener(getMainWindowListener());
                JPanel jpanel = new JPanel() {
                    {
                        setBackground(Color.WHITE);
                        setPreferredSize(new Dimension(400, 115));
                    }
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                    }
                };


        JTextArea jtResult = new JTextArea();
        jtResult.setBounds(5, 5, 390, 50);
        jtResult.setBorder(BorderFactory.createEtchedBorder());

//        ActionListener searchActionListener = new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                jtResult.setText("...");
//                frame.setTitle("..." + " - " + TITLE);
//                jpanel.repaint();
//                Result result = new Result();
//                String subString = jtSubString.getText();
//                Future<Result> future = fileSearch.searchBySubstringAsync(jtDir.getText(), true, subString, result);
//                runSearchingTimer(frame, future, jtResult, subString);
//                config.setDirectory(jtDir.getText());
//                config.setSubString(jtSubString.getText());
//            }
//        };

//        jtSubString.addActionListener(searchActionListener);

        jpanel.add(jtResult);

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
