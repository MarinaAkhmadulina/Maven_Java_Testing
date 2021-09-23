package HomeWork_6;

import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;

public class Weather extends Thread {
    public static void main(String[] args) throws IOException {
        Weather weather = new Weather();
        Response response = null;
        try {
            response = weather.client.newCall(weather.request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonData = response.body().string();
        JSONObject jObject = new JSONObject(jsonData);

        System.out.println(jObject);
    }

    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
            .url("http://dataservice.accuweather.com/forecasts/v1/daily/5day" +
                    "/294021?apikey=mOY0FrJNKxAHLvS1Hlt7gXgAKsz1EEvN")
            .build();
}