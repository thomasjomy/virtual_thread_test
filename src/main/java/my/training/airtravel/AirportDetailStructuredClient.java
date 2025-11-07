package my.training.airtravel;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.StructuredTaskScope;

public class AirportDetailStructuredClient {
    public static void main(String[] args) {
        try {
            AirportService airportService = new AirportService();

            List<String> iataCodes = List.of("AUS", "TAS", "DFW", "IAH", "SAT");

            try(var scope = new StructuredTaskScope.ShutdownOnFailure()){
                List<StructuredTaskScope.Subtask<String>> tasks = new ArrayList<>();

                for (var iataCode : iataCodes){
                    tasks.add(scope.fork(() -> airportService.getAirportName(iataCode)));
                }

                scope.joinUntil(Instant.now().plusSeconds(10));

                for(var task : tasks){
                    System.out.println(task.get());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
