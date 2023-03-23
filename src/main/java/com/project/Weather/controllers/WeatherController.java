package com.project.Weather.controllers;

import com.project.Weather.Weather;
import com.project.Weather.WeatherApplication;
import com.project.Weather.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class WeatherController {

    private WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @ResponseBody
    @GetMapping("/current")
    public Weather current() {
        return WeatherApplication.context.getBean(WeatherService.class).findRecent();
    }

    @ResponseBody
    @PostMapping("/range")
    public String range(@RequestParam String start, @RequestParam String end) {
        return WeatherApplication.context.getBean(WeatherService.class).getAverage(start, end);
    }
}
