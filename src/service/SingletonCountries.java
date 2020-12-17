package service;

import model.Country;
import model.utils.JsonConverter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.List;

public class SingletonCountries {

    private List<Country> countryList;
    private static SingletonCountries singleCountries;

    private SingletonCountries(){

        JsonArray jsonArray = JsonConverter.getJsonArray("https://restcountries.eu/rest/v2/all");

        Gson gson= new Gson();

        for (int i = 0; i < jsonArray.size(); i++) {
            countryList.add(gson.fromJson(jsonArray.get(i).toString(), Country.class));
        }

    }

    public static SingletonCountries getInstance() {

        if (singleCountries==null) {
            singleCountries = new SingletonCountries();
        }
        return singleCountries;

    }

    public Country find(String isoCode){

        return countryList.stream()
                .filter(country -> country.getAlpha2Code().equalsIgnoreCase(isoCode))
                .findFirst()
                .get();

    }

}
