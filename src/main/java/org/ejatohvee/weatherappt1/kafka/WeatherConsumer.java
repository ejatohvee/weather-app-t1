package org.ejatohvee.weatherappt1.kafka;

import lombok.RequiredArgsConstructor;
import org.ejatohvee.weatherappt1.models.Weather;
import org.ejatohvee.weatherappt1.services.WeatherService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeatherConsumer {
    private final WeatherService weatherService;

    @KafkaListener(topics = "weather-topic", groupId = "weather-group", containerFactory = "kafkaListenerContainerFactory")
    public void listen(Weather weather) {
        weatherService.processIncomingWeather(weather);
        System.out.println("[Consumer] Received and saved: " + weather);
    }
}