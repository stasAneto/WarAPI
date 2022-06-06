package org.example;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Format;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class warStats {

    class StatsType{
        String en_name;
        String ua_name;
        int value;
    }

    private final String apiPath = "https://russianwarship.rip/api/v1/statistics/2022-06-06";
    private List<StatsType> statsList;

    public warStats(){
        statsList = new ArrayList<StatsType>();

        StatsType newType;

        newType = new StatsType();
        newType.en_name = "personnel_units";
        newType.ua_name = "особовий склад";
        newType.value = 0;
        statsList.add(newType);

        newType = new StatsType();
        newType.en_name = "tanks";
        newType.ua_name = "танки";
        newType.value = 0;
        statsList.add(newType);

        newType = new StatsType();
        newType.en_name = "armoured_fighting_vehicles";
        newType.ua_name = "ББМ";
        newType.value = 0;
        statsList.add(newType);

        newType = new StatsType();
        newType.en_name = "artillery_systems";
        newType.ua_name = "арт. системи";
        newType.value = 0;
        statsList.add(newType);

        newType = new StatsType();
        newType.en_name = "mlrs";
        newType.ua_name = "РСЗВ";
        newType.value = 0;
        statsList.add(newType);

        newType = new StatsType();
        newType.en_name = "aa_warfare_systems";
        newType.ua_name = "засоби ППО";
        newType.value = 0;
        statsList.add(newType);

        newType = new StatsType();
        newType.en_name = "planes";
        newType.ua_name = "літаки";
        newType.value = 0;
        statsList.add(newType);

        newType = new StatsType();
        newType.en_name = "helicopters";
        newType.ua_name = "гелікоптери";
        newType.value = 0;
        statsList.add(newType);

        newType = new StatsType();
        newType.en_name = "vehicles_fuel_tanks";
        newType.ua_name = "автотехніка";
        newType.value = 0;
        statsList.add(newType);

        newType = new StatsType();
        newType.en_name = "warships_cutters";
        newType.ua_name = "кораблі та катери";
        newType.value = 0;
        statsList.add(newType);

        newType = new StatsType();
        newType.en_name = "cruise_missiles";
        newType.ua_name = "БПЛА";
        newType.value = 0;
        statsList.add(newType);

        newType = new StatsType();
        newType.en_name = "uav_systems";
        newType.ua_name = "спец техніка";
        newType.value = 0;
        statsList.add(newType);

        newType = new StatsType();
        newType.en_name = "special_military_equip";
        newType.ua_name = "установки ОТРК/ТРК";
        newType.value = 0;
        statsList.add(newType);

        newType = new StatsType();
        newType.en_name = "atgm_srbm_systems";
        newType.ua_name = "крилаті ракети";
        newType.value = 0;
        statsList.add(newType);
    }

    private void setField(String name, int value){
        for (StatsType findStat:statsList) {
            if (findStat.en_name.equals(name)){
                findStat.value = value;
                return;
            }
        }
    }

    public String getUAField(String name){
        for (StatsType findStat:statsList) {
            if (findStat.en_name.equals(name)){
                return findStat.ua_name + " - " + findStat.value;
            }
        }
        return "";
    }

    public String getAllStats(){
        String result = "";
        for (StatsType findStat:statsList) {
            result += findStat.ua_name + " - " + findStat.value + System.lineSeparator();
        }
        return result;
    }

    public void getApiData() throws Exception{
        //Устанавливаем адрес подключения
        URL apiURL = new URL(apiPath);
        //Открываем подключение
        HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();
        //Устанавливаем тип запроса
        connection.setRequestMethod("GET");
        //Устанавливаем заголовки
        connection.setRequestProperty("accept", "application/json");
        //Отправляем запрос и получаем результат
        int responseCode = connection.getResponseCode();

        //Считываем ответ через буффер
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //Загружаем полученные данные в список
        JSONObject respJSON = new JSONObject(response.toString());
        JSONObject data_JSON = respJSON.getJSONObject("data");
        JSONObject stats_JSON = data_JSON.getJSONObject("stats");

        String[] namesArray = JSONObject.getNames(stats_JSON);
        for (String name:namesArray) {
            try{
                setField(name,stats_JSON.getInt(name));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
