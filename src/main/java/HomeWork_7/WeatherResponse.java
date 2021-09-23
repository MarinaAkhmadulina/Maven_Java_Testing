package HomeWork_7;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherResponse {

    public static void main(String[] args) throws IOException, ParseException {
        WeatherResponse weather = new WeatherResponse();
        Response response = null;
        try {
            response = weather.client.newCall(weather.request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonData = response.body().string();
        ObjectMapper objectMapper = new ObjectMapper();
        for (int i = 0; i < 5; i++) {
            JsonNode city = objectMapper
                    .readTree(jsonData)
                    .at("/Headline/Link");
            JsonNode date = objectMapper
                    .readTree(jsonData)
                    .at("/DailyForecasts/" +i + "/Date");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            JsonNode weatherText = objectMapper
                    .readTree(jsonData)
                    .at("/DailyForecasts/" +i + "/Day/PrecipitationType");
            JsonNode weatherType = objectMapper
                    .readTree(jsonData)
                    .at("/DailyForecasts/" +i + "/Day/HasPrecipitation");
            JsonNode temperature = objectMapper
                    .readTree(jsonData)
                    .at("/DailyForecasts/" +i + "/Temperature/Maximum/Value");

            String city1 = city.asText().substring(city.asText().indexOf("ru/")+3);
            city1 = city1.substring(0,city1.indexOf("/"));
            Date date1 = df.parse(date.asText());
            String weather1;
            if (weatherType.asText() == "true") {
                weather1 = weatherText.asText();
            }
            else weather1 = "погода без осадков";
            
            System.out.println("В городе " + city1 + " на дату " + df.format(date1) +
                    " ожидается " + weather1 + ", температура " +
                    (int) ((temperature.asInt() - 32) / 1.8) + "\u2103");
        }
    }

    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
            .url("http://dataservice.accuweather.com/forecasts/v1/daily/5day" +
                    "/294021?apikey=mOY0FrJNKxAHLvS1Hlt7gXgAKsz1EEvN")
            .build();
}