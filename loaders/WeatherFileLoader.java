package org.ejatohvee.weatherappt1.loaders;

import lombok.RequiredArgsConstructor;
import org.ejatohvee.weatherappt1.models.City;
import org.ejatohvee.weatherappt1.models.Weather;
import org.ejatohvee.weatherappt1.models.enums.WeatherState;
import org.ejatohvee.weatherappt1.repositories.CityRepository;
import org.ejatohvee.weatherappt1.repositories.WeatherRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class WeatherFileLoader {
    private final WeatherRepository weatherRepository;
    private final CityRepository cityRepository;


    @EventListener(ApplicationReadyEvent.class)
    public void loadFromFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weather.txt"))))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length != 4) {
                    System.err.println("Некорректная строка: " + line);
                    continue;
                }

                String cityName = parts[0].trim();
                City city = cityRepository.getOrCreate(cityName);
                LocalDateTime date = LocalDateTime.parse(parts[1].trim());
                float temperature = Float.parseFloat(parts[2].trim());
                WeatherState state = WeatherState.valueOf(parts[3].trim().toUpperCase());

                weatherRepository.save(city, date, new Weather(temperature, state, city, date));
            }

            System.out.println("Данные из файла успешно загружены в репозиторий.");

        } catch (Exception e) {
            System.err.println("Ошибка при загрузке файла: " + e.getMessage());
        }
    }
}
