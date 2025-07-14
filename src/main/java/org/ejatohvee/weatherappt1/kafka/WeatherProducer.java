package org.ejatohvee.weatherappt1.kafka;

import lombok.RequiredArgsConstructor;
import org.ejatohvee.weatherappt1.models.Weather;
import org.ejatohvee.weatherappt1.services.WeatherService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class WeatherProducer {
    private final KafkaTemplate<String, Weather> kafkaTemplate;
    private final WeatherService weatherService;

    @Scheduled(fixedRate = 2000)
    public void sendRandomWeather() {
        Weather weather = weatherService.createRandomWeather();

        kafkaTemplate.send("weather-topic", weather.getCity().getName(), weather);
        System.out.println("[Producer] Sent: " + weather);
    }
}