package service;

import model.mapper.*;
import model.IpInformationResponse;
import com.google.gson.Gson;
import model.utils.Utils;

public class ReportService {

    public static String retrieveIpInformation(String ip) {

        Ip2country ip2c = new Ip2country(ip);
        Country countryIp = SingletonCountries.getInstance().find(ip2c.getCountryCode());
        SingletonCountries.getInstance().updateInvocations(ip2c.getCountryCode());

        IpInformationResponse response = new IpInformationResponse();

        response.setIp(ip);
        response.setConsultDate(Utils.getActualDate());

        response.setCountry(countryIp.getNativeName());
        response.setCountryEng(countryIp.getName());
        response.setIsoCode(countryIp.getAlpha2Code());

        response.setLanguage(countryIp.getLanguages());
        response.setCurrency(Utils.rateCurrencies(countryIp.getCurrencies()));
        response.setTimeZone(Utils.valueTimeZone(countryIp.getTimezones()));

        Position pos = new Position(countryIp.getLatlng().get(0), countryIp.getLatlng().get(1));
        response.setLatLong(pos);
        response.setAverageDistance(Utils.calculateDistance(pos));

        Gson gson = new Gson();
        return gson.toJson(response);
    }

}
