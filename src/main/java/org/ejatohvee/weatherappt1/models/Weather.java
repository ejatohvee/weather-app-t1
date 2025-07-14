package org.ejatohvee.weatherappt1.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.ejatohvee.weatherappt1.models.enums.WeatherState;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
public class Weather {
    private Float temperature;
    private WeatherState state;
    private City city;
    private LocalDate date;
}