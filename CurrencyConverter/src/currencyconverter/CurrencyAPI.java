package currencyconverter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONObject;

public class CurrencyAPI {

    public static double getExchangeRate(String from, String to) {
        try {
            String apiKey = "YOUR API KEY";
            String url_str = "https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + from + "/" + to;

            URL url = new URL(url_str);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();

            JSONObject obj = new JSONObject(sb.toString());

            // Check if API result is success
            if (!obj.getString("result").equals("success")) {
                System.out.println("API Error: " + obj.toString());
                return 0.0;
            }

            // Correct field is "conversion_rate", not "conversion_rates"
            return obj.getDouble("conversion_rate");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    public static Map<String, String> getSupportedCurrencies() {
    Map<String, String> currencyMap = new LinkedHashMap<>();
    try {
        String apiKey = "ad8908e90be3167964916ffc";
        String url_str = "https://v6.exchangerate-api.com/v6/" + apiKey + "/codes";

        URL url = new URL(url_str);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();

        JSONObject obj = new JSONObject(sb.toString());
        if (!obj.getString("result").equals("success")) {
            System.out.println("API Error: " + obj.toString());
            return currencyMap;
        }

        // Now fetch codes
        for (Object arrObj : obj.getJSONArray("supported_codes")) {
            var arr = (org.json.JSONArray) arrObj;
            String code = arr.getString(0);
            String name = arr.getString(1);
            currencyMap.put(code, code + " - " + name);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return currencyMap;
}
}

