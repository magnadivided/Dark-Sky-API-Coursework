package ru.sibgu.magnadivided.darkskyapi.code;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.*;

public class Functions {

    private JSONObject json;

    public JSONObject request_task(final String url ) {
        Thread task = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                try {
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Response response = client.newCall(request).execute();
                    json = new JSONObject(Objects.requireNonNull(response.body()).string());
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        task.setName("GET REQUEST");
        task.start();
        try {
            task.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return json;
    }

}
