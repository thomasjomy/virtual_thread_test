package my.training;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DataFetcher {

    public static void main(String[] args) throws IOException, InterruptedException {
        URI uri = URI.create("http://mydata.com/data");
        HttpResponse<String> response;

        try (HttpClient client = HttpClient.newBuilder().build()) {
            HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }

        if(response.statusCode() == 200){
            String body = response.body();
            System.out.println(body);
        }

        System.out.println(response);
    }

}
