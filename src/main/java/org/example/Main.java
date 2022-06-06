package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.HttpURLConnection;

public class Main {

    public static void main(String[] args) {
        try {
            warStats curStats = new warStats();
            curStats.getApiData();

            System.out.println(curStats.getAllStats());
            //System.out.println(response);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        //System.out.println("Hello world!");
    }

    private static void setStats(String name, Object o) {
    }
}