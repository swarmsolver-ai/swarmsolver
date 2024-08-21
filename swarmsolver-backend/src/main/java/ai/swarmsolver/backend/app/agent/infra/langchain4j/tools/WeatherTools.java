package ai.swarmsolver.backend.app.agent.infra.langchain4j.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

import java.time.LocalDate;

public class WeatherTools {

    @Tool("Returns the weather forecast for tomorrow for a given city")
    String getWeather(
            @P("The city for which the weather forecast should be returned") String city,
            @P("The hour between 0 and 23") Integer hour
    ) {
        return String.format("The weather tomorrow in %s at %s hour is  %s °C", city, hour, simulateTemperature(hour));
    }


    @Tool("Transforms Celsius degrees into Fahrenheit")
    double celsiusToFahrenheit(@P("The celsius degree to be transformed into fahrenheit") double celsius) {
        return (celsius * 1.8) + 32;
    }

    public static double simulateTemperature(int hour) {
        double minTemp = 18; // Minimum temperature at 6 AM
        double maxTemp = 30; // Maximum temperature at 6 PM

        double amplitude = (maxTemp - minTemp) / 2;
        double averageTemp = (maxTemp + minTemp) / 2;

        double temperature = amplitude * Math.sin(Math.toRadians(15 * (hour - 6))) + averageTemp;

        return Math.round(temperature * 10) / 10.0;
    }


}