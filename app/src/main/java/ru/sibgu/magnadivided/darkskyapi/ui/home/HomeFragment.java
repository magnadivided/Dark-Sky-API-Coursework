package ru.sibgu.magnadivided.darkskyapi.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import ru.sibgu.magnadivided.darkskyapi.App;
import ru.sibgu.magnadivided.darkskyapi.R;
import ru.sibgu.magnadivided.darkskyapi.code.*;

public class HomeFragment extends Fragment {

    private List<Weather> weatherList = new ArrayList<>();
    private Functions func = new Functions();
    private RoomDB room = App.getInstance().getDatabase();
    private WeatherDao weatherDao = room.weatherDao();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        weatherList = new ArrayList<>();
        weatherList = weatherDao.getAll();
        setInitialData();

        Collections.reverse(weatherList);

        RecyclerView recyclerView = root.findViewById(R.id.recycler_view_list);
        WeatherAdapter adapter = new WeatherAdapter(this.getContext(), weatherList);
        recyclerView.setAdapter(adapter);

        return root;
    }

    private void setInitialData() {

        List<String> latlng = new ArrayList<>();
        List<String> city = new ArrayList<>();
        city.add("Красноярск");
        latlng.add("56.0065,92.871");
        for (String position : latlng) {

            JSONObject json = func.request_task(getResources().getString(R.string.weather_url) + getResources().getString(R.string.api_key) + "/" + position);

            JSONArray weather_json = null;
            try {
                weather_json = json.getJSONObject("daily").getJSONArray("data");
                Log.i("JSON", "" + weather_json.length());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (weather_json != null) {
                for (int i = 0; i < weather_json.length(); i++) {
                    Weather weather = new Weather(city.get(latlng.indexOf(position)), "NaN", "NaN");
                    try {
                        weather.setTempMin(weather_json.getJSONObject(i).getString("temperatureMin"), true);
                        weather.setTempMax(weather_json.getJSONObject(i).getString("temperatureMax"), true);
                        weather.setTime(weather_json.getJSONObject(i).getLong("time")*1000);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Weather dummy = weatherDao.getByTime(weather.getTime());
                    if (dummy == null) {
                        weatherList.add(weather);
                        weatherDao.insert(weather);
                    }
                }
            }
        }

    }

}