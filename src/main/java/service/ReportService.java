package service;

import model.StatisticResponse;
import model.mapper.*;
import model.IpInformationResponse;
import com.google.gson.Gson;
import model.utils.Utils;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.http.MimeTypes;
import spark.Request;
import spark.Response;

public class ReportService {

    private static final SingletonCountries singleCountries = SingletonCountries.getInstance();

    public static String retrieveIpInformation(Request request, Response response) {

        String ip = request.params("ip");

        if (isInvalidIp(ip)) {

            response.status(HttpStatus.BAD_REQUEST_400);
            return "<h1>Error</h1><br><h2>El texto ingresado no es una IP valida</h2>";

        }

        Ip2country ip2c = new Ip2country(ip);
        Country countryIp = singleCountries.find(ip2c.getCountryCode());

        IpInformationResponse responseObject = new IpInformationResponse();

        responseObject.setIp(ip);
        responseObject.setConsultDate(Utils.getActualDate());

        responseObject.setCountry(countryIp.getNativeName());
        responseObject.setCountryEng(countryIp.getName());
        responseObject.setIsoCode(countryIp.getAlpha2Code());

        responseObject.setLanguage(countryIp.getLanguages());
        responseObject.setCurrency(Utils.rateCurrencies(countryIp.getCurrencies()));
        responseObject.setTimeZone(Utils.valueTimeZone(countryIp.getTimezones()));

        Position pos = new Position(countryIp.getLatlng().get(0), countryIp.getLatlng().get(1));
        responseObject.setLatLong(pos);
        String distance = Utils.calculateDistance(pos);
        responseObject.setAverageDistance(distance);

        singleCountries.updateInvocations(ip2c.getCountryCode(), distance);

        response.status(HttpStatus.OK_200);
        response.header(HttpHeader.CONTENT_TYPE.toString(), MimeTypes.Type.APPLICATION_JSON_UTF_8.toString());

        Gson gson = new Gson();
        return gson.toJson(responseObject);
    }

    private static boolean isInvalidIp(String ip) {

        return !ip.matches("\\b((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.|$)){4}\\b");

    }

    public static String calculateDistanceInfo() {

        StatisticResponse stResponse = new StatisticResponse();
        stResponse.setLongestDistance(singleCountries.fillLongestCountryConsult());
        stResponse.setShortestDistance(singleCountries.fillShortestCountryConsult());
        stResponse.setAverageDistance(singleCountries.calculateAverageDistance());

        Gson gson = new Gson();
        return gson.toJson(stResponse);

    }
}
