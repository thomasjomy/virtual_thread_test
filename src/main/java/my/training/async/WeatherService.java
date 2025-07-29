package my.training.async;

import my.training.async.model.Weather;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class WeatherService {

    public List<CompletableFuture<Weather>> getWeatherForecast() {
        List<Supplier<Weather>> weatherForecastTasks = getWeatherForecastSuppliers();

        List<CompletableFuture<Weather>> weatherCFs = new ArrayList<>();
        for (Supplier<Weather> weatherForecastTask : weatherForecastTasks) {
            CompletableFuture<Weather> weatherCF = CompletableFuture.supplyAsync(weatherForecastTask);
            weatherCFs.add(weatherCF);
        }

        return weatherCFs;
    }


    private List<Supplier<Weather>> getWeatherForecastSuppliers() {
        Random random = new Random();

        Supplier<Weather> fetchWeatherA = () -> {
            try {
                Thread.sleep(random.nextInt(80, 120));
                return new Weather("Server A", "Sunny");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        Supplier<Weather> fetchWeatherB = () -> {
            try {
                Thread.sleep(random.nextInt(80, 120));
                return new Weather("Server B", "Mostly sunny");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        };

        Supplier<Weather> fetchWeatherC = () -> {
            try {
                Thread.sleep(random.nextInt(80, 120));
                return new Weather("Server C", "Cloudy");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        };

        return List.of(fetchWeatherA, fetchWeatherB, fetchWeatherC);

    }
}
