package ru.sibgu.magnadivided.darkskyapi.code;

import androidx.room.*;

@Database(entities = {Weather.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    public abstract WeatherDao weatherDao();
}