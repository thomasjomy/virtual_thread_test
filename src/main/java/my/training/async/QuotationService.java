package my.training.async;

import my.training.async.model.Quotation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class QuotationService {

    public List<CompletableFuture<Quotation>> getQuotations() {
        List<Supplier<Quotation>> quotationSuppliers = getQuotationSuppliers();

        List<CompletableFuture<Quotation>> quotationCFs = new ArrayList<>();
        for (Supplier<Quotation> quotationSupplier : quotationSuppliers) {
            CompletableFuture<Quotation> quotationCF = CompletableFuture.supplyAsync(quotationSupplier);
            quotationCFs.add(quotationCF);
        }

        return quotationCFs;
    }

    private List<Supplier<Quotation>> getQuotationSuppliers() {
        Random random = new Random();

        Supplier<Quotation> fetchQuotationA = () -> {
            try {
                Thread.sleep(random.nextInt(80, 120));
                return new Quotation("Server A", random.nextInt(40, 60));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        Supplier<Quotation> fetchQuotationB = () -> {
            try {
                Thread.sleep(random.nextInt(80, 120));
                return new Quotation("Server B", random.nextInt(30, 70));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        };

        Supplier<Quotation> fetchQuotationC = () -> {
            try {
                Thread.sleep(random.nextInt(80, 120));
                return new Quotation("Server C", random.nextInt(40, 80));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        };

        return List.of(fetchQuotationA, fetchQuotationB, fetchQuotationC);
    }
}
