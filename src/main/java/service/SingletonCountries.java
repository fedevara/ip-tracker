package service;

import model.mapper.Country;
import model.utils.JsonConverter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

public class SingletonCountries {

    private List<Country> countryList;
    private static SingletonCountries singleCountries;
    private List<String> countryListInvocated;

    private SingletonCountries() {

        JsonArray jsonArray = JsonConverter.getJsonArray("https://restcountries.eu/rest/v2/all");

        Gson gson = new Gson();

        countryList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            countryList.add(gson.fromJson(jsonArray.get(i).toString(), Country.class));
        }

    }

    public static SingletonCountries getInstance() {

        if (singleCountries == null) {
            singleCountries = new SingletonCountries();
        }
        return singleCountries;

    }

    public Country find(String isoCode) {

        return countryList.stream()
                .filter(country -> country.getAlpha2Code().equalsIgnoreCase(isoCode))
                .findFirst()
                .get();

    }

    public void updateInvocations(String isoCode) {

        countryList.stream()
                .filter(country -> country.getAlpha2Code().equalsIgnoreCase(isoCode))
                .findFirst()
                .get().increaseInvocation();

        if(countryListInvocated==null){
            countryListInvocated = new ArrayList<>();
        }

        if(!countryListInvocated.contains(isoCode)){
            countryListInvocated.add(isoCode);
        }

    }
}
