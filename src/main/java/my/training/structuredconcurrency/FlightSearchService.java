package my.training.structuredconcurrency;

import my.training.async.model.Flight;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.Stream;

public class FlightSearchService {

    private final Random random = new Random();

    public Flight readFromAlphaAirlines(FlightQuery flightQuery) {
        sleepFor(random.nextInt(80, 100), ChronoUnit.MILLIS);
        int price = random.nextInt(70, 120);
        int duration = random.nextInt(70, 120);
        return new Flight(flightQuery.getFrom(), flightQuery.getTo(), duration, price, "Alpha Air Lines");
    }

    public Flight readFromGlobalAirlines(FlightQuery flightQuery) {
        sleepFor(random.nextInt(90, 110), ChronoUnit.MILLIS);
        int price = random.nextInt(60, 90);
        int duration = random.nextInt(60, 90);
        return new Flight(flightQuery.getFrom(), flightQuery.getTo(), duration, price, "Global Air Lines");
    }

    public Flight readFromPlanetAirlines(FlightQuery flightQuery) {
        sleepFor(random.nextInt(70, 120), ChronoUnit.MILLIS);
        int price = random.nextInt(70, 90);
        int duration = random.nextInt(60, 90);
        return new Flight(flightQuery.getFrom(), flightQuery.getTo(), duration, price, "Planet Air Lines");
    }


    public static void sleepFor(int amount, ChronoUnit chronoUnit) {
        try {
            Thread.sleep(chronoUnit.getDuration().toMillis() * amount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
