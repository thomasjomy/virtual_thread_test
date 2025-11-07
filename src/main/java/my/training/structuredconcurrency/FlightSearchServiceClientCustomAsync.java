package my.training.structuredconcurrency;

import my.training.async.model.Flight;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Comparator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.StructuredTaskScope;

public class FlightSearchServiceClientCustomAsync {
    private final FlightInformationService flightInformationService = new FlightInformationService();

    private static class FlightScope extends StructuredTaskScope<Flight> {

        private volatile Collection<Flight> flights = new ConcurrentLinkedQueue<>();
        private volatile Collection<Throwable> exceptions = new ConcurrentLinkedQueue<>();

        @Override
        protected void handleComplete(Subtask<? extends Flight> subtask) {

            switch (subtask.state()) {
                case UNAVAILABLE -> throw new IllegalStateException("Task should be done.");
                case SUCCESS -> this.flights.add(subtask.get());
                case FAILED -> this.exceptions.add(subtask.exception());

            }
        }

        public RuntimeException exceptions(){
            RuntimeException runtimeException = new RuntimeException();
            this.exceptions.forEach(runtimeException::addSuppressed);
            return runtimeException;
        }

        public Flight bestFlight() {
            return flights.stream()
                    .min(Comparator.comparing(Flight::getPrice))
                    .orElseThrow(this::exceptions);
        }
    }

    public FlightQueryResult searchFlight(FlightQuery flightQuery) {
        var start = Instant.now();

        try (var scope = new FlightScope()) {
            var flight1 = scope.fork(() -> flightInformationService.readFromAlphaAirlines(flightQuery));
            var flight2 = scope.fork(() -> flightInformationService.readFromGlobalAirlines(flightQuery));
            var flight3 = scope.fork(() -> flightInformationService.readFromPlanetAirlines(flightQuery));
            scope.join();

            var end = Instant.now();

            return new FlightQueryResult(scope.bestFlight(), Duration.between(start, end));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
