package my.training.structuredconcurrency;

public class FlightSearchServiceClient {

    public static void main(String[] args) {
        runSequentialQuery();
        runParallelQuery();
        runParallelQueryCustom();
    }

    private static void runSequentialQuery() {
        FlightSearchService flightSearchService = new FlightSearchService();
        String from = "New York";
        String to = "San Francisco";
        FlightQuery flightQuery = new FlightQuery(from, to);
        FlightQueryResult result = flightSearchService.searchFlight(flightQuery);
        System.out.println("Best flight " + result.getFlight() + " in " + result.getElapsedTime().toMillis());
    }

    private static void runParallelQuery() {
        FlightSearchServiceClientAsync flightSearchService = new FlightSearchServiceClientAsync();
        String from = "New York";
        String to = "San Francisco";
        FlightQuery flightQuery = new FlightQuery(from, to);
        FlightQueryResult result = flightSearchService.searchFlight(flightQuery);
        System.out.println("Best flight " + result.getFlight() + " in " + result.getElapsedTime().toMillis());
    }

    private static void runParallelQueryCustom(){
        FlightSearchServiceClientCustomAsync flightSearchService = new FlightSearchServiceClientCustomAsync();
        String from = "New York";
        String to = "San Francisco";
        FlightQuery flightQuery = new FlightQuery(from, to);
        FlightQueryResult result = flightSearchService.searchFlight(flightQuery);
        System.out.println("Best flight " + result.getFlight() + " in " + result.getElapsedTime().toMillis());
    }

}
