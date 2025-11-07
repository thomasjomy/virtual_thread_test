package my.training.invoice;

import java.util.List;
import java.util.concurrent.StructuredTaskScope;

public class InvoiceGenerator {

    public Invoice generate() {
        try(var scope = new StructuredTaskScope.ShutdownOnFailure()){
            StructuredTaskScope.Subtask<Issuer> issuer = scope.fork(this::findIssuer);
            StructuredTaskScope.Subtask<Customer> customer = scope.fork(this::findCustomer);
            StructuredTaskScope.Subtask<List<Item>> items = scope.fork(this::findItems);

            scope.join();

            return new Invoice(issuer.get(), customer.get(), items.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Issuer findIssuer(){
        return new Issuer();
    }

    private Customer findCustomer(){
        return new Customer();
    }

    private List<Item> findItems(){
        return List.of(new Item());
    }
}
