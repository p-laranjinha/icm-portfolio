package ua.laranjinha93179.cityweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import ua.laranjinha93179.cityweather.datamodel.City;
import ua.laranjinha93179.cityweather.datamodel.Weather;
import ua.laranjinha93179.cityweather.datamodel.WeatherType;
import ua.laranjinha93179.cityweather.network.CityResultsObserver;
import ua.laranjinha93179.cityweather.network.ForecastForACityResultsObserver;
import ua.laranjinha93179.cityweather.network.IpmaWeatherClient;
import ua.laranjinha93179.cityweather.network.WeatherTypesResultsObserver;

public class MainActivity extends AppCompatActivity {

    private boolean mTwoPane = false;
    public LinkedList<String> cityList = new LinkedList<>();
    private String city_info = "";

    IpmaWeatherClient client = new IpmaWeatherClient();
    private HashMap<String, City> cities;
    private HashMap<Integer, WeatherType> weatherDescriptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the toolbar as the app bar.
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle(getTitle());

        // Get the song list as a RecyclerView.
        cityList.addAll(Arrays.asList(new String[]{"Aveiro", "Porto", "Lisboa", "Santarém"}));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.city_list);
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(cityList));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (findViewById(R.id.city_detail) != null) {
            mTwoPane = true;
        }
    }

    class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final LinkedList<String> mValues;

        SimpleItemRecyclerViewAdapter(LinkedList<String> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_list_content, parent, false);
            return new ViewHolder(view, this);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.mView.setText(cityList.get(position));
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            final TextView mView;
            final SimpleItemRecyclerViewAdapter mAdapter;

            ViewHolder(View view, SimpleItemRecyclerViewAdapter adapter) {
                super(view);
                mView = view.findViewById(R.id.city);
                mAdapter = adapter;
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                callWeatherForecastForACityStep1(cityList.get(getLayoutPosition()), v.getContext());

            }
        }
    }


    private void callWeatherForecastForACityStep1(String city, Context v) {
        Toast.makeText(getApplicationContext(),"Getting forecast for: " + city,Toast.LENGTH_SHORT).show();
        // call the remote api, passing an (anonymous) listener to get back the results
        client.retrieveWeatherConditionsDescriptions(new WeatherTypesResultsObserver() {
            @Override
            public void receiveWeatherTypesList(HashMap<Integer, WeatherType> descriptorsCollection) {
                weatherDescriptions = descriptorsCollection;
                callWeatherForecastForACityStep2(city, v);
            }
            @Override
            public void onFailure(Throwable cause) {
                Toast.makeText(getApplicationContext(),"Failed to get weather conditions!",Toast.LENGTH_SHORT).show();
//                getActivity().finish();
            }
        });

    }

    private void callWeatherForecastForACityStep2(String city, Context v) {
        client.retrieveCitiesList(new CityResultsObserver() {

            @Override
            public void receiveCitiesList(HashMap<String, City> citiesCollection) {
                cities = citiesCollection;
                City cityFound = cities.get(city);
                if( null != cityFound) {
                    int locationId = cityFound.getGlobalIdLocal();
                    callWeatherForecastForACityStep3(locationId, v);
                } else {
                    Toast.makeText(getApplicationContext(),"unknown city: " + city,Toast.LENGTH_SHORT).show();
//                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Throwable cause) {
                Toast.makeText(getApplicationContext(),"Failed to get cities list!",Toast.LENGTH_SHORT).show();
//                getActivity().finish();
            }
        });
    }

    private void callWeatherForecastForACityStep3(int localId, Context v) {
        client.retrieveForecastForCity(localId, new ForecastForACityResultsObserver() {
            @Override
            public void receiveForecastList(List<Weather> forecast) {
                city_info = "";
                for (Weather day : forecast) {
                    city_info += (day.toString()+"\t");
                }
                if (mTwoPane) {
                    CityWeatherFragment fragment = CityWeatherFragment.newInstance(city_info);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment, fragment)
                            .addToBackStack(null)
                            .commit();
                } else {
                    Context context = v;
                    Intent intent = new Intent(context, CityWeatherDetailsActivity.class);
                    intent.putExtra("city_info", city_info);
                    context.startActivity(intent);
                }
            }
            @Override
            public void onFailure(Throwable cause) {
                Toast.makeText(getApplicationContext() ,"Failed to get forecast for 5 days",Toast.LENGTH_SHORT).show();
            }
        });

    }
}