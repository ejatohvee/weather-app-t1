package org.ejatohvee.weatherappt1.services;

import lombok.RequiredArgsConstructor;
import org.ejatohvee.weatherappt1.models.City;
import org.ejatohvee.weatherappt1.models.Weather;
import org.ejatohvee.weatherappt1.models.enums.WeatherState;
import org.ejatohvee.weatherappt1.repositories.CityRepository;
import org.ejatohvee.weatherappt1.repositories.WeatherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherRepository weatherRepository;
    private final CityRepository cityRepository;

    public void processIncomingWeather(Weather weather) {
        City city = cityRepository.getOrCreate(weather.getCity().getName());
        weather.setCity(city);
        weatherRepository.save(city, weather.getDate(), weather);
    }

    public Weather createRandomWeather() {
        List<String> cities = List.of("Moscow", "London", "Paris", "Berlin");
        Random random = new Random();

        String cityName = cities.get(random.nextInt(cities.size()));
        City city = cityRepository.getOrCreate(cityName);

        float temperature = random.nextFloat() * 35;
        WeatherState state = WeatherState.values()[random.nextInt(WeatherState.values().length)];

        return new Weather(temperature, state, city, LocalDate.now());
    }
}