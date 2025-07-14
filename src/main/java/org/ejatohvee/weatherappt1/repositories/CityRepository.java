package org.ejatohvee.weatherappt1.repositories;

import org.ejatohvee.weatherappt1.models.City;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CityRepository {
    private final Map<String, City> cities = new ConcurrentHashMap<>();

    public City getOrCreate(String name) {
        return cities.computeIfAbsent(name, City::new);
    }

    public Collection<City> findAll() {
        return cities.values();
    }
}
