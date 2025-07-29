package my.training.async;

import my.training.async.model.Quotation;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class AsynchronousTask {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        run();
    }

    private static void run() throws ExecutionException, InterruptedException {
        Random random = new Random();

        Callable<Quotation> fetchQuotationA = () -> {
            Thread.sleep(random.nextInt(80, 120));
            return new Quotation("Server A", random.nextInt(40, 60));
        };

        Callable<Quotation> fetchQuotationB = () -> {
            Thread.sleep(random.nextInt(80, 120));
            return new Quotation("Server B", random.nextInt(30, 70));
        };

        Callable<Quotation> fetchQuotationC = () -> {
            Thread.sleep(random.nextInt(80, 120));
            return new Quotation("Server C", random.nextInt(40, 80));
        };

        List<Callable<Quotation>> quotationTasks = List.of(fetchQuotationA,fetchQuotationB, fetchQuotationC);

        var executorService = Executors.newFixedThreadPool(4);
        Instant begin = Instant.now();

        List<Future<Quotation>> results = new ArrayList<>();

        for (Callable<Quotation> quotationTask : quotationTasks) {
            Future<Quotation> result = executorService.submit(quotationTask);
            results.add(result);
        }

        List<Quotation> quotations = new ArrayList<>();
        for (Future<Quotation> result : results) {
            Quotation quotation = result.get();
            quotations.add(quotation);
        }

        Quotation bestQuotation = quotations.stream()
                .min(Comparator.comparing(Quotation::amount))
                .orElseThrow();

        Instant end = Instant.now();

        Duration duration = Duration.between(begin, end);

        System.out.println("Best Quotation [SYNC]  = " +  bestQuotation + " (" + duration.toMillis() + "ms)");

        executorService.shutdown();

    }

    private static Quotation fetchQuotation(Callable<Quotation> task){
        try {
            return task.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
