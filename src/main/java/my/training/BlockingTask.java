package my.training;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class BlockingTask {

    public static void main(String[] args) {
        Runnable task1 = () -> {
            System.out.println(Thread.currentThread());
            sleepFor(10, ChronoUnit.MICROS);
            System.out.println(Thread.currentThread());
        };

        Runnable task2 = () -> {
          sleepFor(10, ChronoUnit.MICROS);
        };

        int N_THREADS = 10;
        var threads = new ArrayList<Thread>();
        for (int index = 0; index < N_THREADS; index++){
            var thread = index == 0 ?
                    Thread.ofVirtual().unstarted(task1):
                    Thread.ofVirtual().unstarted(task2);
            threads.add(thread);
        }

        for(var thread : threads){
            thread.start();
        }

        for(var thread: threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void sleepFor(int amount, ChronoUnit chronoUnit){
        try{
            Thread.sleep(Duration.of(amount, chronoUnit));
        } catch(InterruptedException ie){
            throw new RuntimeException(ie);
        }
    }
}
