package my.training.airtravel;

import java.util.Map;

public class AirportService {
    public String getAirportName(String iata) {
        delay(2000);
        var mockData = Map.of("AUS", "Austin", "IAH", "Houston", "DFW", "Dallas", "SAT", "San Antonio");

        var name = mockData.get(iata);

        if (name == null) {
            throw new RuntimeException("Invalid Airport code " + iata);
        }

        delay(2000);
        return name;
    }

    private void delay(long millis){
        try{
            Thread.sleep(millis);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
