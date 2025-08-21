package my.training.structuredconcurrency;

public class WeatherDataClient {

    public static void main(String[] args) {
        var weather = WeatherDataService.readWeather();
        System.out.println(weather);
    }
}
