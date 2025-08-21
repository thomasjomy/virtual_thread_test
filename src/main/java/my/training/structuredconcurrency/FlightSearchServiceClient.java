package my.training.structuredconcurrency;

import my.training.async.model.Flight;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.concurrent.StructuredTaskScope;
import java.util.stream.Stream;

public class FlightSearchServiceClient {

    public static void main(String[] args) {
        runSequentialQuery();
        runParallelQuery();
    }

    private static void runParallelQuery() {
        FlightSearchService flightSearchService = new FlightSearchService();

        String from = "New York";
        String to = "San Francisco";

        FlightQuery flightQuery = new FlightQuery(from, to);

        var start = Instant.now();
        try (var scope = new StructuredTaskScope<Flight>()) {
            var flight1 = scope.fork(() -> flightSearchService.readFromAlphaAirlines(flightQuery));
            var flight2 = scope.fork(() -> flightSearchService.readFromGlobalAirlines(flightQuery));
            var flight3 = scope.fork(() -> flightSearchService.readFromPlanetAirlines(flightQuery));
            scope.join();

            Flight bestFlight = Stream.of(flight1, flight2, flight3)
                    .filter(t -> t.state() == StructuredTaskScope.Subtask.State.SUCCESS)
                    .map(StructuredTaskScope.Subtask::get)
                    .min(Comparator.comparing(Flight::getPrice))
                    .get();

            var end = Instant.now();
            System.out.println("Best flight " + bestFlight + " in " + Duration.between(start, end).toMillis());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void runSequentialQuery() {
        FlightSearchService flightSearchService = new FlightSearchService();

        String from = "New York";
        String to = "San Francisco";

        FlightQuery flightQuery = new FlightQuery(from, to);

        var start = Instant.now();
        var flight1 = flightSearchService.readFromAlphaAirlines(flightQuery);
        var flight2 = flightSearchService.readFromGlobalAirlines(flightQuery);
        var flight3 = flightSearchService.readFromPlanetAirlines(flightQuery);

        System.out.println("Flight 1 : " + flight1);
        System.out.println("Flight 2 : " + flight2);
        System.out.println("Flight 3 : " + flight3);

        Flight bestFlight = Stream.of(flight1, flight2, flight3)
                .min(Comparator.comparing(Flight::getPrice))
                .get();
        var end = Instant.now();

        System.out.println("Best flight " + bestFlight + " in " + Duration.between(start, end).toMillis());
    }

}
