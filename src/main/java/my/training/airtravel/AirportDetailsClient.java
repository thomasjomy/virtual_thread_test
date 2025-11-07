package my.training.airtravel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class AirportDetailsClient {

    public static void main(String[] args) {
        try {
            AirportService airportService = new AirportService();

            List<String> iataCodes = List.of("AUS", "DFW", "IAH", "TAS", "SAT");

            var executorService = Executors.newFixedThreadPool(3);
            var futures = new ArrayList<Future<String>>();

            for (var iataCode : iataCodes) {
                futures.add(executorService.submit(() -> airportService.getAirportName(iataCode)));
            }

            for (var future : futures) {
                System.out.println(future.get());
            }

            executorService.shutdown();
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
