package roksard.typingTrainer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ComponentScan(basePackages = "roksard.*")
@Configuration("roksard.typingTrainer.SpringConfiguration")
public class Main {
    static Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext("roksard.*").start();
    }
}
