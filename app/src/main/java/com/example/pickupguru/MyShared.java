package com.example.pickupguru;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;

public class MyShared extends Object {

    public static void showRateActive(Context context){
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }

    public static String getTitle(String fileName)
    {
        switch (fileName)
        {
            case "openers.txt":
                return "Фразы для знакомства";
            case "compliments.txt":
                return "Комплименты";
            case "roof.txt":
                return "Крышесносы";
            case "scenario.txt":
                return "Сценарии для свиданий";
            case "presents.txt":
                return "Подарки";
            case "routines.txt":
                return "Рутины";
        }
        return "";
    }

    public static String getFileString(Context context, String text)
    {
        byte[] buffer = null;
        InputStream is;
        try {
            is = context.getAssets().open(text);
            int size = is.available();
            buffer = new byte[size];
            is.read(buffer);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String str_data = new String(buffer);
        return  str_data;
    }

}
