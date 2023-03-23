package com.project.Weather.services;

import com.project.Weather.Weather;
import com.project.Weather.repos.WeatherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;

    @Autowired
    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @Transactional
    public void save(Weather weather) {
        weatherRepository.save(weather);
    }

    @Transactional
    public Weather findRecent() {
        return weatherRepository.findRecent();
    }

    @Transactional
    public String getAverage(String start, String end) {
        String[] split = weatherRepository.findAverage(start, end).split(",");
        return "<center><pre>Average:\n\nTemperature: " + split[0] + "\nWind: " + split[1] +
                "\nPressure: " + split[2] + "\nHumidity: " + split[3] + "</pre></center>";
    }
}
