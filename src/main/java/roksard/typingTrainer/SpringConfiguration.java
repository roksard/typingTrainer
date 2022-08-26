package roksard.typingTrainer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import roksard.json_serializer.JsonSerializer;
import roksard.typingTrainer.pojo.Config;

import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Configuration
public class SpringConfiguration {
    private final JsonSerializer<Config> serializer1 = new JsonSerializer<>(Config.class);
    private final JFrame frame1 = new JFrame();
    Logger logger = LogManager.getLogger(this.getClass());

    @Bean
    public JsonSerializer<Config> serializer() {
        return serializer1;
    }

    @Bean
    public Config config() {
        return serializer1.load(configFile(), Config.DEFAULT);
    }

    @Bean
    public String configFile() {
        return "settings.p";
    }

    @Bean
    public JFrame frame() {
        return frame1;
    }

    @Bean
    JTextArea epText() {
        return new JTextArea();
    }

    @Bean
    JScrollPane epTextScrollPane(@Autowired JTextArea epText) {
        return new JScrollPane(epText, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    @Bean
    ExecutorService executorService() {
        return Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        JOptionPane.showMessageDialog(frame1, "Error: " + e.toString() + ": " + e.getMessage());
                        logger.error("Error: ", e);
                        System.exit(-1);
                    }
                });
                return thread;
            }
        });
    }
}
