package service;

import model.StatisticResponse;
import model.mapper.*;
import model.IpInformationResponse;
import model.utils.Utils;

public class ReportService {

    private static final SingletonCountries singleCountries = SingletonCountries.getInstance();

    public static IpInformationResponse retrieveIpInformation(String ip) {

        Ip2country ip2c = new Ip2country(ip);
        Country countryIp = singleCountries.find(ip2c.getCountryCode());

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
        String distance = Utils.calculateDistance(pos);
        response.setAverageDistance(distance);

        singleCountries.updateInvocations(ip2c.getCountryCode(), distance);

        return response;
    }

    public static StatisticResponse calculateDistanceInfo() {
        StatisticResponse stResponse = new StatisticResponse();
        stResponse.setLongestDistance(singleCountries.fillLongestCountryConsult());
        stResponse.setShortestDistance(singleCountries.fillShortestCountryConsult());
        stResponse.setAverageDistance(singleCountries.calculateAverageDistance());
        return stResponse;
    }
}
