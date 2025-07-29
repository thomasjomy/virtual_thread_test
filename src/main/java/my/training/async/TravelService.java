package my.training.async;

import my.training.async.model.Quotation;
import my.training.async.model.TravelDetails;
import my.training.async.model.Weather;

import java.nio.file.Watchable;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TravelService {
    private final QuotationService quotationService = new QuotationService();
    private final WeatherService weatherService = new WeatherService();

    public static void main(String[] args) {
        TravelService travelService = new TravelService();
        TravelDetails travelDetails = travelService.getTravelDetails().join();
        System.out.println("Travel details : " + travelDetails);
    }


    public CompletableFuture<TravelDetails> getTravelDetails() {
        List<CompletableFuture<Quotation>> quotationCompletableFutures = quotationService.getQuotations();
        List<CompletableFuture<Weather>> weatherCompletableFutures = weatherService.getWeatherForecast();

        CompletableFuture<Void> allOf = CompletableFuture.allOf(quotationCompletableFutures.toArray(CompletableFuture[]::new));

        CompletableFuture<Weather> anyWeatherCF = CompletableFuture.anyOf(weatherCompletableFutures.toArray(CompletableFuture[]::new))
                .thenApply(o -> (Weather) o);

        CompletableFuture<Quotation> bestQuotationCF = allOf.thenApply(v ->
                quotationCompletableFutures.stream()
                        .map(CompletableFuture::join)
                        .min(Comparator.comparing(Quotation::amount))
                        .orElseThrow()
        );

        return bestQuotationCF.thenCombine(anyWeatherCF, TravelDetails::new);
    }
}
