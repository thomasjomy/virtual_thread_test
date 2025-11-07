package my.training;

public class TravelQuery {

    private final String from;
    private final String to;

    public TravelQuery(String from , String to){
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
