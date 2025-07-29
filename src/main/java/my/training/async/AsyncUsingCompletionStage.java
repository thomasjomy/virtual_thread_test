package my.training.async;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class AsyncUsingCompletionStage {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        run();
    }

    private static void run() throws ExecutionException, InterruptedException {
        Random random = new Random();

        Supplier<Quotation> fetchQuotationA = () -> {
            try {
                Thread.sleep(random.nextInt(80, 120));
                return new Quotation("Server A", random.nextInt(40, 60));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        Supplier<Quotation> fetchQuotationB = () -> {
            try {
                Thread.sleep(random.nextInt(80, 120));
                return new Quotation("Server B", random.nextInt(30, 70));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        };

        Supplier<Quotation> fetchQuotationC = () -> {
            try {
                Thread.sleep(random.nextInt(80, 120));
                return new Quotation("Server C", random.nextInt(40, 80));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        };

        var quotationTasks = List.of(fetchQuotationA,fetchQuotationB, fetchQuotationC);

        Instant begin = Instant.now();

        List<CompletableFuture<Quotation>> results = new ArrayList<>();

        for (Supplier<Quotation> quotationTask : quotationTasks) {
            CompletableFuture<Quotation> quotationCompletableFuture = CompletableFuture.supplyAsync(quotationTask);
            results.add(quotationCompletableFuture);
        }

        List<Quotation> quotations = new ArrayList<>();
        for (CompletableFuture<Quotation> result : results) {
            Quotation quotation = result.join();
            quotations.add(quotation);
        }

        Quotation bestQuotation = quotations.stream()
                .min(Comparator.comparing(Quotation::amount))
                .orElseThrow();

        Instant end = Instant.now();

        Duration duration = Duration.between(begin, end);

        System.out.println("Best Quotation [SYNC]  = " +  bestQuotation + " (" + duration.toMillis() + "ms)");

    }

    private static Quotation fetchQuotation(Callable<Quotation> task){
        try {
            return task.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
