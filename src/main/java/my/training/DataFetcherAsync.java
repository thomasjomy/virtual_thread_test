package my.training;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Function;
import java.util.function.Supplier;

public class DataFetcherAsync {
    public static void main(String[] args) {
        URI uri = URI.create("http://mydata.com/data");

        Supplier<HttpRequest> request = () -> {
            return HttpRequest.newBuilder(uri).GET().build();
        };

        Function<HttpRequest, HttpResponse<String>> sender = request1 -> {
            try (HttpClient client = HttpClient.newBuilder().build()) {
                return client.send(request1, HttpResponse.BodyHandlers.ofString());
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        };




    }
}
