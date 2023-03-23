package com.project.Weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Weather.services.WeatherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class WeatherApplication {

    public static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(WeatherApplication.class, args);
        while (true) {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://weatherapi-com.p.rapidapi.com/current.json?q=Minsk"))
                        .header("X-RapidAPI-Key", "9ddcd94db5mshf0a5f83069ee288p130c25jsn619e0577d055")
                        .header("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
                        .method("GET", HttpRequest.BodyPublishers.noBody())
                        .build();
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                JsonNode jsonNode = new ObjectMapper().readTree(response.body());
                Weather weather = new Weather();
                weather.setLastUpdated(jsonNode.get("current").get("last_updated").asText());
                weather.setTemp(jsonNode.get("current").get("temp_c").asDouble());
                weather.setWind(jsonNode.get("current").get("wind_mph").asDouble());
                weather.setPressure(jsonNode.get("current").get("pressure_mb").asDouble());
                weather.setHumidity(jsonNode.get("current").get("humidity").asInt());
                weather.setShortDescription(jsonNode.get("current").get("condition").get("text").asText());
                weather.setLocation(jsonNode.get("location").get("name").asText());
                context.getBean(WeatherService.class).save(weather);
                TimeUnit.MINUTES.sleep(10);
            } catch (IOException e) {
                System.out.println("IOException");
            } catch (InterruptedException e) {
                System.out.println("InterruptedException");
            }
        }
    }
}
