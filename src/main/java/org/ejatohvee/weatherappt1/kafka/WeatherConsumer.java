package org.ejatohvee.weatherappt1.kafka;

import lombok.RequiredArgsConstructor;
import org.ejatohvee.weatherappt1.models.City;
import org.ejatohvee.weatherappt1.models.Weather;
import org.ejatohvee.weatherappt1.repositories.CityRepository;
import org.ejatohvee.weatherappt1.repositories.WeatherRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeatherConsumer {
    private final WeatherRepository weatherRepository;
    private final CityRepository cityRepository;

    @KafkaListener(topics = "weather-topic", groupId = "weather-group", containerFactory = "kafkaListenerContainerFactory")
    public void listen(Weather weather) {
        City city = cityRepository.getOrCreate(weather.getCity().getName());
        weather.setCity(city);
        weatherRepository.save(city, weather.getDate(), weather);

        System.out.println("[Consumer] Received and saved: " + weather);
    }
}