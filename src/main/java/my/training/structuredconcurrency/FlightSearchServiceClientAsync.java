package my.training.structuredconcurrency;

import my.training.async.model.Flight;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.concurrent.StructuredTaskScope;
import java.util.stream.Stream;

public class FlightSearchServiceClientAsync {
    private final FlightInformationService flightInformationService = new FlightInformationService();

    public FlightQueryResult searchFlight(FlightQuery flightQuery) {
        var start = Instant.now();

        try (var scope = new StructuredTaskScope<Flight>()) {
            var flight1 = scope.fork(() -> flightInformationService.readFromAlphaAirlines(flightQuery));
            var flight2 = scope.fork(() -> flightInformationService.readFromGlobalAirlines(flightQuery));
            var flight3 = scope.fork(() -> flightInformationService.readFromPlanetAirlines(flightQuery));
            scope.join();

            Flight bestFlight = Stream.of(flight1, flight2, flight3)
                    .filter(t -> t.state() == StructuredTaskScope.Subtask.State.SUCCESS)
                    .map(StructuredTaskScope.Subtask::get)
                    .min(Comparator.comparing(Flight::getPrice))
                    .get();

            var end = Instant.now();

            return new FlightQueryResult(bestFlight, Duration.between(start, end));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
