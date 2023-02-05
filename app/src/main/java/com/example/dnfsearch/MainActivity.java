package com.example.dnfsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    FCMManager fcm = new FCMManager();

    private TextView FatigueElixirText;
    private TextView subtractPriceToChargeText;
    private TextView goldCubeText;
    private TextView saengSumText;
    private TextView adventurerIngredientText;
    private TextView magicCrystalText;
    private TextView noColorMagicText;
    private TextView sumIngredientText;

    private FatigueElixir fatigueElixir = new FatigueElixir();
    private GoldCube goldCube = new GoldCube();
    private SaengSum saengSum = new SaengSum();
    private AdventurerIngredient adventurerIngredient = new AdventurerIngredient();
    private MagicCrystal magicCrystal = new MagicCrystal();
    private NoColorMagic noColorMagic = new NoColorMagic();

    private String fatigueElixirStringData;
    private String goldCubeData;
    private String saengSumData;
    private String adventurerIngredientData;
    private String magicCrystalData;
    private String noColorMagicData;

    public static int sumIngredient = 0;

    public static String dnfApiKey = "Your DNF Api Key here";
    private String firebaseKey = "Your Firebase Server Key here";
    private String deviceToken = "Your Device Token here";
    private String title = "Notification Title here";
    private String body = "Notification Description here";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitialFCM();
        SetTextView();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted())
                {
                    try {
                        fatigueElixirStringData = fatigueElixir.GetFatigueElixirData();
                        goldCubeData = goldCube.GetGoldCubeData();
                        saengSumData = saengSum.GetSaengSumData();
                        adventurerIngredientData = adventurerIngredient.GetAdventurerIngredient();
                        magicCrystalData = magicCrystal.GetMagicCryStal();
                        noColorMagicData = noColorMagic.GetNoColorMagic();
                        Thread.sleep(1000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                FatigueElixirText.setText(fatigueElixirStringData);
                                goldCubeText.setText(goldCubeData);
                                saengSumText.setText(saengSumData);
                                adventurerIngredientText.setText(adventurerIngredientData);
                                magicCrystalText.setText(magicCrystalData);
                                noColorMagicText.setText(noColorMagicData);
                                sumIngredientText.setText(String.valueOf(sumIngredient));
                                subtractPriceToChargeText.setText(String.valueOf(Integer.parseInt(fatigueElixir.GetSubtractPriceToCharge())));

                                CheckOverPrice();
                                sumIngredient = 0;
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void InitialFCM()
    {
        fcm.InitialChannel(this);
        fcm.GetDeviceToken();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    private void SetTextView()
    {
        FatigueElixirText = findViewById(R.id.FatigueElixirText);
        subtractPriceToChargeText = findViewById(R.id.percentText);
        goldCubeText = findViewById(R.id.GoldCubeText);
        saengSumText = findViewById(R.id.SangSumText);
        adventurerIngredientText = findViewById(R.id.AdventurerText);
        magicCrystalText = findViewById(R.id.magicCrystalText);
        noColorMagicText = findViewById(R.id.noColorMagicText);
        sumIngredientText = findViewById(R.id.SumIngredientText);
    }

    boolean overPriceFlag = false;
    private void CheckOverPrice()
    {
        if (Integer.parseInt(fatigueElixir.GetSubtractPriceToCharge()) > sumIngredient && !overPriceFlag)
        {
            fcm.SendNotification(deviceToken, firebaseKey, title, body);
            overPriceFlag = true;
            subtractPriceToChargeText.setBackground(SetTextViewBgColor(0x96DBB4FF));
        }
        else if (Integer.parseInt(fatigueElixir.GetSubtractPriceToCharge()) < sumIngredient)
        {
            overPriceFlag = false;
            subtractPriceToChargeText.setBackground(SetTextViewBgColor(0x00000000));
        }
    }

    private GradientDrawable SetTextViewBgColor(int colorCode)
    {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(colorCode);
        gd.setStroke(5, 0xFF948B8B);

        return gd;
    }

}
