package roksard.typingTrainer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "roksard.*")
public class Main {
    public static long startTime;
    static Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext("roksard.*").start();

    }
}
