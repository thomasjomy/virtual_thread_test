package my.training.structuredconcurrency;

import my.training.async.model.Flight;

import java.time.temporal.ChronoUnit;
import java.util.Random;

public class FlightInformationService {

    private final Random random = new Random();

    public Flight readFromAlphaAirlines(FlightQuery flightQuery) {
        ThreadUtil.sleepFor(random.nextInt(80, 100), ChronoUnit.MILLIS);
        int price = random.nextInt(70, 120);
        int duration = random.nextInt(70, 120);
        return new Flight(flightQuery.getFrom(), flightQuery.getTo(), duration, price, "Alpha Air Lines");
    }

    public Flight readFromGlobalAirlines(FlightQuery flightQuery) {
        ThreadUtil.sleepFor(random.nextInt(90, 110), ChronoUnit.MILLIS);
        int price = random.nextInt(60, 90);
        int duration = random.nextInt(60, 90);
        return new Flight(flightQuery.getFrom(), flightQuery.getTo(), duration, price, "Global Air Lines");
    }

    public Flight readFromPlanetAirlines(FlightQuery flightQuery) {
        ThreadUtil.sleepFor(random.nextInt(70, 120), ChronoUnit.MILLIS);
        int price = random.nextInt(70, 90);
        int duration = random.nextInt(60, 90);
        return new Flight(flightQuery.getFrom(), flightQuery.getTo(), duration, price, "Planet Air Lines");
    }
}