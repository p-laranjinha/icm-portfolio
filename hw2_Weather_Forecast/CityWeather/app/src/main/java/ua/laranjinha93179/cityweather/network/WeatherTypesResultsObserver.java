package ua.laranjinha93179.cityweather.network;

import java.util.HashMap;

import ua.laranjinha93179.cityweather.datamodel.WeatherType;

public interface WeatherTypesResultsObserver {
    public void receiveWeatherTypesList(HashMap<Integer, WeatherType> descriptorsCollection);
    public void onFailure(Throwable cause);
}
