package ua.laranjinha93179.cityweather.network;

import java.util.HashMap;

import ua.laranjinha93179.cityweather.datamodel.City;

public interface  CityResultsObserver {
    public void receiveCitiesList(HashMap<String, City> citiesCollection);
    public void onFailure(Throwable cause);
}
