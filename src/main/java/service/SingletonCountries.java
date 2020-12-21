package service;

import model.mapper.Country;
import model.mapper.CountryConsult;
import model.utils.JsonConverter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.HashMap;
import java.util.Map;

public class SingletonCountries {

    private final Map<String, Country> countryList;
    private static SingletonCountries singleCountries;
    private Map<String, Integer> countryListInvoked;

    private String shortestDistanceCode;
    private Double shortestDistanceKilometers;

    private String longestDistanceCode;
    private Double longestDistanceKilometers;

    private SingletonCountries() {

        JsonArray jsonArray = JsonConverter.getJsonArray("https://restcountries.eu/rest/v2/all");

        Gson gson = new Gson();

        countryList = new HashMap<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            Country country = gson.fromJson(jsonArray.get(i).toString(), Country.class);
            countryList.put(country.getAlpha2Code(), country);
        }

    }

    public static SingletonCountries getInstance() {

        if (singleCountries == null) {
            singleCountries = new SingletonCountries();
            singleCountries.shortestDistanceCode = "";
            singleCountries.shortestDistanceKilometers = (double) 0;
            singleCountries.longestDistanceCode = "";
            singleCountries.longestDistanceKilometers = (double) 0;
        }
        return singleCountries;

    }

    public Country find(String isoCode) {

        return countryList.get(isoCode);

    }

    public void updateInvocations(String isoCode, String distance) {

        Country updatedCountry = countryList.get(isoCode);
        updatedCountry.setDistanceToBA(distance);
        countryList.put(isoCode, updatedCountry);

        if (countryListInvoked == null) {
            countryListInvoked = new HashMap<>();
        }

        if (!countryListInvoked.containsKey(isoCode)) {
            countryListInvoked.put(isoCode, 1);
        } else {
            countryListInvoked.put(isoCode, countryListInvoked.get(isoCode) + 1);
        }

        if (countryListInvoked.size() == 1) {

            shortestDistanceKilometers = Double.valueOf(distance);
            shortestDistanceCode = isoCode;
            longestDistanceKilometers = Double.valueOf(distance);
            longestDistanceCode = isoCode;

        } else {

            if (Double.parseDouble(distance) < shortestDistanceKilometers) {
                shortestDistanceKilometers = Double.valueOf(distance);
                shortestDistanceCode = isoCode;
            }

            if (Double.parseDouble(distance) > longestDistanceKilometers) {
                longestDistanceKilometers = Double.valueOf(distance);
                longestDistanceCode = isoCode;
            }
        }

    }

    public String getShortestDistanceCode() {
        return shortestDistanceCode;
    }

    public Double getShortestDistanceKilometers() {
        return shortestDistanceKilometers;
    }

    public String getLongestDistanceCode() {
        return longestDistanceCode;
    }

    public Double getLongestDistanceKilometers() {
        return longestDistanceKilometers;
    }

    public String calculateAverageDistance() {

        int totalDistance = 0;
        Integer totalInvocations = 0;

        if (countryListInvoked != null && !(countryListInvoked.size() == 0)) {

            for (Map.Entry<String, Integer> entry : countryListInvoked.entrySet()) {
                Country country = find(entry.getKey());
                totalInvocations += entry.getValue();
                totalDistance += entry.getValue() * Integer.parseInt(country.getDistanceToBA());
            }

            return String.valueOf(totalDistance / totalInvocations);

        } else {

            return "";

        }

    }

    public String getInvocations(String alpha2Code) {

        return countryListInvoked.get(alpha2Code).toString();

    }

    public CountryConsult fillLongestCountryConsult() {

        CountryConsult countryCons = new CountryConsult();
        String lDistCode = singleCountries.getLongestDistanceCode();
        Double lDistKilo = singleCountries.getLongestDistanceKilometers();

        if (lDistCode != null && !lDistCode.equals("") && lDistKilo != null) {
            countryCons.setInvocations(singleCountries.getInvocations(lDistCode));
            countryCons.setCountryName(countryList.get(lDistCode).getName());
            countryCons.setCountryDistance(lDistKilo.toString());
        }

        return countryCons;

    }

    public CountryConsult fillShortestCountryConsult() {

        CountryConsult countryCons = new CountryConsult();
        String sDistCode = singleCountries.getShortestDistanceCode();
        Double sDistKilo = singleCountries.getShortestDistanceKilometers();

        if (sDistCode != null && !sDistCode.equals("") && sDistKilo != null) {
            countryCons.setInvocations(singleCountries.getInvocations(sDistCode));
            countryCons.setCountryName(countryList.get(sDistCode).getName());
            countryCons.setCountryDistance(sDistKilo.toString());
        }

        return countryCons;

    }
}
