package my.training.invoice;

import java.util.List;


public class Invoice {
    private final Issuer issuer;
    private final Customer customer;
    private final List<Item> items;


    public Invoice(Issuer issuer, Customer customer, List<Item> items) {
        this.issuer = issuer;
        this.customer = customer;
        this.items = items;
    }

    public Issuer getIssuer() {
        return issuer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Item> getItems() {
        return items;
    }
}
