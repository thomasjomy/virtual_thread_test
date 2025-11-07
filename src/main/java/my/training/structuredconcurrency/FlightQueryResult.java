package my.training.structuredconcurrency;

import my.training.async.model.Flight;

import java.time.Duration;

public class FlightQueryResult {
    private final Flight flight;
    private final Duration elapsedTime;


    public FlightQueryResult(Flight flight, Duration elapsedTime) {
        this.flight = flight;
        this.elapsedTime = elapsedTime;
    }

    public Flight getFlight() {
        return flight;
    }

    public Duration getElapsedTime() {
        return elapsedTime;
    }

    @Override
    public String toString() {
        return "FlightQueryResult{" +
                "flight=" + flight +
                ", elapsedTime=" + elapsedTime +
                '}';
    }
}
