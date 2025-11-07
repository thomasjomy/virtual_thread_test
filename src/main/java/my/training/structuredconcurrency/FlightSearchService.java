package my.training.structuredconcurrency;

import my.training.async.model.Flight;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.stream.Stream;

public class FlightSearchService {

    private final FlightInformationService flightInformationService = new FlightInformationService();

    public FlightQueryResult searchFlight(FlightQuery flightQuery) {
        var start = Instant.now();

        var flight1 = flightInformationService.readFromAlphaAirlines(flightQuery);
        var flight2 = flightInformationService.readFromGlobalAirlines(flightQuery);
        var flight3 = flightInformationService.readFromPlanetAirlines(flightQuery);

        Flight bestFlight = Stream.of(flight1, flight2, flight3)
                .min(Comparator.comparing(Flight::getPrice))
                .get();

        var end = Instant.now();

        return new FlightQueryResult(bestFlight, Duration.between(start, end));

    }

    private Flight readFromAlphaAirlines(FlightQuery flightQuery) {
        return flightInformationService.readFromAlphaAirlines(flightQuery);
    }

    private Flight readFromGlobalAirlines(FlightQuery flightQuery) {
        return flightInformationService.readFromGlobalAirlines(flightQuery);
    }

    private Flight readFromPlanetAirlines(FlightQuery flightQuery) {
        return flightInformationService.readFromPlanetAirlines(flightQuery);
    }

}
