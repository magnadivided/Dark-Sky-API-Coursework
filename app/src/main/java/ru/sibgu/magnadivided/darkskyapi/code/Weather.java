package ru.sibgu.magnadivided.darkskyapi.code;

import java.util.Locale;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Weather {

    private String city, tempMax, tempMin;
    @PrimaryKey
    private long time;

    public Weather(String city, String tempMin, String tempMax) {
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.time = 0;
        this.city = city;
    }


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTempMax() {
        return tempMax;
    }

    public void setTempMax(String tempMax, boolean needCoversion) {
        if (needCoversion) {
            double temp = Double.parseDouble(tempMax);
            this.tempMax = String.format(Locale.ROOT,"%.1f",((temp-32)*5/9));
        } else this.tempMax = tempMax;
    }

    public String getTempMin() {
        return tempMin;
    }

    public void setTempMin(String tempMin, boolean needCoversion) {
        if (needCoversion) {
            double temp = Double.parseDouble(tempMin);
            this.tempMin = String.format(Locale.ROOT,"%.1f",((temp-32)*5/9));
        } else this.tempMin = tempMin;
    }
}
