package com.example.dnfsearch;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class FatigueElixir{
    private String stringData;

    String GetFatigueElixirData() throws IOException {
        String unitPrice = "";
        try {
            StringBuilder urlBuilder = new StringBuilder("https://api.neople.co.kr/df/auction?itemName=피로 회복의 영약&sort=unitPrice:asc&apikey="); /*URL*/
            urlBuilder.append(MainActivity.dnfApiKey);
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
                    SetSubtractPriceToCharge(genres_genreNm.get("unitPrice"));
            }

            rd.close();
            conn.disconnect();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.out.println("잘못된 URL");
        } catch (SocketTimeoutException e) {
            System.out.println("타임 아웃");
        } catch (IOException e) {
            System.out.println("네트워크 문제");
        } catch (Exception e) {
            System.out.println("기타 문제");
            e.printStackTrace();
        }

        return unitPrice;
    }

    private int price;
    private int charge;
    void SetSubtractPriceToCharge(Object unitPrice) {
        price = (Integer.parseInt(unitPrice.toString()));
        charge = (int) ((double) price * 0.03);
    }

    String GetSubtractPriceToCharge() {
        return String.valueOf(price - charge);
    }

}
