package org.ejatohvee.weatherappt1.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.ejatohvee.weatherappt1.models.City;
import org.ejatohvee.weatherappt1.models.Weather;
import org.ejatohvee.weatherappt1.models.enums.WeatherState;
import org.ejatohvee.weatherappt1.repositories.CityRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class WeatherProducer {
    private final KafkaTemplate<String, Weather> kafkaTemplate;
    private final CityRepository cityRepository;

    private static final List<String> cities = List.of("Moscow", "London", "Paris", "Berlin");
    private static final Random random = new Random();

    @Scheduled(fixedRate = 2000)
    public void sendRandomWeather() {
        String cityName = cities.get(random.nextInt(cities.size()));
        City city = cityRepository.getOrCreate(cityName);

        float temperature = random.nextFloat(35);
        WeatherState state = WeatherState.values()[random.nextInt(WeatherState.values().length)];

        Weather weather = new Weather(temperature, state, city, LocalDateTime.now());

        kafkaTemplate.send("weather-topic", cityName, weather);
        System.out.println("[Producer] Sent: " + weather);
    }
}