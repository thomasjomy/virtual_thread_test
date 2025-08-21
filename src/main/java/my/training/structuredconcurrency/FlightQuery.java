package my.training.structuredconcurrency;

public class FlightQuery {
    private final String from;
    private final String to;


    public FlightQuery(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "FlightQuery{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}
