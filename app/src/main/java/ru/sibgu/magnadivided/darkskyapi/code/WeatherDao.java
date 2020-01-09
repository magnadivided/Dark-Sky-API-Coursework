package ru.sibgu.magnadivided.darkskyapi.code;

import java.util.List;

import androidx.room.*;

@Dao
public interface WeatherDao {

    @Query("SELECT * FROM weather")
    List<Weather> getAll();

    @Query("SELECT * FROM weather WHERE time = :id")
    Weather getByTime(long id);

    @Insert
    void insert(Weather weather);

    @Delete
    void delete(Weather weather);

}
