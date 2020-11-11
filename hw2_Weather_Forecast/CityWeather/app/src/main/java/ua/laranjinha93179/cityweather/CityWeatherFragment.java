package ua.laranjinha93179.cityweather;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import ua.laranjinha93179.cityweather.datamodel.City;
import ua.laranjinha93179.cityweather.datamodel.Weather;
import ua.laranjinha93179.cityweather.datamodel.WeatherType;
import ua.laranjinha93179.cityweather.network.CityResultsObserver;
import ua.laranjinha93179.cityweather.network.ForecastForACityResultsObserver;
import ua.laranjinha93179.cityweather.network.IpmaWeatherClient;
import ua.laranjinha93179.cityweather.network.WeatherTypesResultsObserver;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CityWeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CityWeatherFragment extends Fragment {

    public String city_info;

    public CityWeatherFragment() {
        // Required empty public constructor
    }

    public static CityWeatherFragment newInstance(String selectedCity) {
        CityWeatherFragment fragment = new CityWeatherFragment();
        Bundle arguments = new Bundle();
        arguments.putString("city_info", selectedCity);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("city_info")) {
            city_info = getArguments().getString("city_info");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_weather, container, false);
        if (city_info != null)
            ((TextView) view.findViewById(R.id.city_detail)).setText(city_info);
        return view;
    }


}