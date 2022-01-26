package roksard.typingTrainer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Main {
    public static long startTime;
    public static void main(String[] args) {
        startTime = System.currentTimeMillis();
        Gui gui = new Gui();
        ExecutorService executorService = Executors.newFixedThreadPool(2, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setUncaughtExceptionHandler(gui.getExceptionHandler());
                return thread;
            }
        });
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                gui.start(executorService);
            }
        });
    }
}
