package org.ejatohvee.weatherappt1.repositories;

import org.ejatohvee.weatherappt1.models.City;
import org.ejatohvee.weatherappt1.models.Weather;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class WeatherRepository {
    private final Map<City, Map<LocalDate, Weather>> data = new ConcurrentHashMap<>();

    public Optional<Weather> findByCityAndDate(City city, LocalDate date) {
        return Optional.ofNullable(data.get(city).get(date));
    }

    public void save(City city, LocalDate time, Weather weather) {
        data.computeIfAbsent(city, c -> new ConcurrentHashMap<>()).put(time, weather);
    }

    public Map<City, Map<LocalDate, Weather>> findAll() {
        return data;
    }
}
