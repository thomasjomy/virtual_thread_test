package my.training;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

public class VirtualThreadExecutor {
    public static void main(String[] args) {
        var set = ConcurrentHashMap.<String>newKeySet();

        Runnable task = () -> set.add(Thread.currentThread().toString());

        int N_TASKS = 200;

        try (var es1 = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int index = 0; index < N_TASKS; index++) {
                es1.submit(task);
            }
        }

        System.out.println("# threads.used " + set.size());
    }
}
