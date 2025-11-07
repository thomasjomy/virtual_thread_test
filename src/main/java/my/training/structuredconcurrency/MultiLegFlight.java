package my.training.structuredconcurrency;

import my.training.async.model.Flight;

import java.util.concurrent.StructuredTaskScope;

public record MultiLegFlight(String from, String via, String to, int price, String airline) {

    public static MultiLegFlight of(Flight flight1, Flight flight2) {
        return new MultiLegFlight(flight1.getFrom(), flight1.getTo(), flight2.getTo(), flight1.getPrice() + flight2.getPrice(), flight1.getCarrier());
    }
}

class MultiLegFlightService {
    private final FlightInformationService flightInformationService = new FlightInformationService();

    public MultiLegFlight readMultiLegFlight(FlightQuery flightQuery1, FlightQuery flightQuery2) {
        try (StructuredTaskScope<Flight> scope = new StructuredTaskScope<Flight>()) {
            //StructuredTaskScope.Subtask<Flight> f1 = scope.fork(flightInformationService::readFromAlphaAirlines);
            //StructuredTaskScope.Subtask<Flight> f2 = scope.fork(flightInformationService::readFromAlphaAirlines);

            //scope.join();

            //return MultiLegFlight.of(f1, f2);
            //} catch (InterruptedException e) {
            //    throw new RuntimeException(e);
            //}
            return null;

        }
    }
}
