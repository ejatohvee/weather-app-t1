package org.ejatohvee.weatherappt1;

import org.ejatohvee.weatherappt1.models.City;
import org.ejatohvee.weatherappt1.models.Weather;
import org.ejatohvee.weatherappt1.models.enums.WeatherState;
import org.ejatohvee.weatherappt1.repositories.CityRepository;
import org.ejatohvee.weatherappt1.repositories.WeatherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@EmbeddedKafka(partitions = 1, topics = { "weather-topic" })
class WeatherAppT1ApplicationTests {

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private CityRepository cityRepository;

    @Test
    void saveAndRetrieveWeather() {
        City city = cityRepository.getOrCreate("Tokyo");
        LocalDate date = LocalDate.of(2025, 7, 14);
        Weather weather = new Weather(30.5f, WeatherState.SUNNY, city, date);

        weatherRepository.save(city, date, weather);
        Optional<Weather> found = weatherRepository.findByCityAndDate(city, date);

        assertTrue(found.isPresent());
        assertEquals(30.5f, found.get().getTemperature());
        assertEquals(WeatherState.SUNNY, found.get().getState());
    }

    @Test
    void cityRepositoryCreatesOnce() {
        City city1 = cityRepository.getOrCreate("New York");
        City city2 = cityRepository.getOrCreate("New York");

        assertSame(city1, city2);
    }
}
