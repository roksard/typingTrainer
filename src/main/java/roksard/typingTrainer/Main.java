package roksard.typingTrainer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Main {
    public static void main(String[] args) {
        Gui gui = new Gui();
        ExecutorService executorService = Executors.newSingleThreadExecutor(new ThreadFactory() {
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
                gui.start();
            }
        });
        executorService.shutdown();
    }
}
