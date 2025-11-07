package my.training.structuredconcurrency;

import java.time.temporal.ChronoUnit;

public class ThreadUtil {
    public static void sleepFor(int amount, ChronoUnit chronoUnit) {
        try {
            Thread.sleep(chronoUnit.getDuration().toMillis() * amount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}