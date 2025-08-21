package my.training.async.model;

public class Flight {
    private final String from;
    private final String to;
    private final int flightDuration;
    private final int price;
    private final String carrier;

    public Flight(String from, String to, int flightDuration, int price,  String carrier) {
        this.from = from;
        this.to = to;
        this.flightDuration = flightDuration;
        this.price = price;
        this.carrier = carrier;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getFlightDuration() {
        return flightDuration;
    }

    public int getPrice() {
        return price;
    }

    public String getCarrier() {
        return carrier;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", flightDuration=" + flightDuration +
                ", price=" + price +
                ", carrier='" + carrier + '\'' +
                '}';
    }
}
