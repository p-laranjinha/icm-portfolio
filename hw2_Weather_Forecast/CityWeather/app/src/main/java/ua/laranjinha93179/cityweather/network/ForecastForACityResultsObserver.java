package ua.laranjinha93179.cityweather.network;

import java.util.HashMap;
import java.util.List;

import ua.laranjinha93179.cityweather.datamodel.Weather;
import ua.laranjinha93179.cityweather.datamodel.WeatherType;

public interface ForecastForACityResultsObserver {
    public void receiveForecastList(List<Weather> forecast);
    public void onFailure(Throwable cause);
}
