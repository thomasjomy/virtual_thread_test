package my.training;

import java.time.Duration;
import java.time.Instant;

public class BookTravel {

    private static ScopedValue licenseKey = ScopedValue.newInstance();


    public static void main(String[] args) {
        String from = "New York";
        String via = "Atlanta";
        String to = "San Francisco";

        TravelQuery travelQuery = new TravelQuery(from, to);
        TravelQuery travelQuery1 = new TravelQuery(from, via);
        TravelQuery travelQuery2 = new TravelQuery(via, to);

        var begin = Instant.now();

        ScopedValue.where(licenseKey, "KEY_1")
                .run(() -> {
                            Page page = readPage(travelQuery, travelQuery1, travelQuery2);
                            System.out.println("Page = " + page);
                        }
                );

        var end = Instant.now();

        System.out.println("Done in " + Duration.between(begin, end).toMillis() + " ms");

    }

    private static Page readPage(TravelQuery travelQuery, TravelQuery travelQuery1, TravelQuery travelQuery2) {
        return null;
    }
}
