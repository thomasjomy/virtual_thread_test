package my.training.structuredconcurrency;

import my.training.async.model.Weather;

import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;

public class WeatherDataService {

    public static Random random = new Random();

    //--release 21 --enable-preview
    public static Weather readWeather() {
        try (StructuredTaskScope.ShutdownOnSuccess<Weather> scope = new StructuredTaskScope.ShutdownOnSuccess<>()) {
            StructuredTaskScope.Subtask<Weather> f1 = scope.fork(WeatherDataService::readFromInternationalWeatherForecast);
            StructuredTaskScope.Subtask<Weather> f2 = scope.fork(WeatherDataService::readFromGlobalWeatherForecast);
            StructuredTaskScope.Subtask<Weather> f3 = scope.fork(WeatherDataService::readFromPlanetEarthWeatherForecast);

            scope.join();

            System.out.println("F1 : " + f1.state());
            if(f1.state() == StructuredTaskScope.Subtask.State.SUCCESS) {
                System.out.println(" " +  f1.get());
            }

            System.out.println("F2 : " + f2.state());
            if(f2.state() == StructuredTaskScope.Subtask.State.SUCCESS) {
                System.out.println(" " +  f2.get());
            }

            System.out.println("F3 : " + f3.state());
            if(f3.state() == StructuredTaskScope.Subtask.State.SUCCESS) {
                System.out.println(" " +  f3.get());
            }

            return scope.result();
        }catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static Weather readFromInternationalWeatherForecast() {
        sleepFor(random.nextInt(70, 120), ChronoUnit.MILLIS);
        String weatherForecast = WeatherForecast.values()[random.nextInt(0, 3)].name();
        return new Weather(weatherForecast, "International Weather Forecast");
    }

    public static Weather readFromGlobalWeatherForecast() {
        sleepFor(random.nextInt(80, 120), ChronoUnit.MILLIS);
        String weatherForecast = WeatherForecast.values()[random.nextInt(0, 3)].name();
        return new Weather(weatherForecast, "Global Weather Forecast");
    }

    public static Weather readFromPlanetEarthWeatherForecast() {
        sleepFor(random.nextInt(80, 120), ChronoUnit.MILLIS);
        String weatherForecast = WeatherForecast.values()[random.nextInt(0, 3)].name();
        return new Weather(weatherForecast, "Global Weather Forecast");
    }

    public static void sleepFor(int amount, ChronoUnit chronoUnit) {
        try {
            Thread.sleep(chronoUnit.getDuration().toMillis() * amount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
