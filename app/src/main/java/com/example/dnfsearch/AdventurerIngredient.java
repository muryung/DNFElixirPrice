package com.example.dnfsearch;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AdventurerIngredient {
    private String key = "EVETgNNQJhLBXxXm9JQg37MsfWS8OHt7";
    private String stringData;

    String GetAdventurerIngredient() throws IOException {
        String unitPrice = "";
        try {

            StringBuilder urlBuilder = new StringBuilder("https://api.neople.co.kr/df/auction?itemName=노련한 모험가의 영혼&sort=unitPrice:asc&apikey="); /*URL*/
            urlBuilder.append(key);
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            BufferedReader rd;
            rd = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

            stringData = rd.readLine();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(stringData);
            JSONArray rows = (JSONArray)jsonObject.get("rows");

            for(int i = 0; i < rows.size(); i++) {
                JSONObject genres_genreNm = (JSONObject)rows.get(i);
                unitPrice += " " + genres_genreNm.get("unitPrice") + " " + genres_genreNm.get("count") + "\n";

                if (i == 0)
                    MainActivity.sumIngredient += Integer.parseInt(genres_genreNm.get("unitPrice").toString()) * 9;
            }

            rd.close();
            conn.disconnect();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return unitPrice;
    }
}
