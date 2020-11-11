package ua.laranjinha93179.cityweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CityWeatherDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather_details);

        if (savedInstanceState == null) {
            String selectedCity = getIntent().getStringExtra("city_info");
            CityWeatherFragment fragment = CityWeatherFragment.newInstance(selectedCity);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

}