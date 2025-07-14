package org.ejatohvee.weatherappt1.repositories;

import org.ejatohvee.weatherappt1.models.City;
import org.ejatohvee.weatherappt1.models.Weather;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class WeatherRepository {
    private Map<City, Map<LocalDateTime, Weather>> data = new ConcurrentHashMap<>();

    public Optional<Weather> findWeatherByCityAndDate(City city, LocalDateTime date) {
        return Optional.ofNullable(data.get(city).get(date));
    }

    public void save(City city, LocalDateTime time, Weather weather) {
        data.computeIfAbsent(city, c -> new ConcurrentHashMap<>()).put(time, weather);
    }

    public Map<City, Map<LocalDateTime, Weather>> findAll() {
        return data;
    }
}
