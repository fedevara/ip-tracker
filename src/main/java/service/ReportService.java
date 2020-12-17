package service;

import com.google.gson.JsonObject;
import model.CurrencyResponse;
import model.mapper.Country;
import model.IpInformationResponse;
import com.google.gson.Gson;
import model.mapper.Currency;
import model.mapper.CurrencyData;
import model.mapper.Ip2country;
import model.utils.JsonConverter;
import model.utils.Utils;

import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public class ReportService {

    public static String retrieveIpInformation(String ip) {

        Ip2country ip2c = new Ip2country(ip);

        Country countryIp = SingletonCountries.getInstance().find(ip2c.getCountryCode());

        IpInformationResponse response = new IpInformationResponse();

        response.setIp(ip);
        response.setConsultDate(Utils.getActualDate());

        response.setCountry(countryIp.getNativeName());
        response.setCountryEng(countryIp.getName());
        response.setIsoCode(countryIp.getAlpha2Code());

        response.setLanguage(countryIp.getLanguages());
        response.setCurrency(rateCurrencies(countryIp.getCurrencies()));
        response.setTimeZone(countryIp.getTimezones());

        Gson gson = new Gson();
        String json = gson.toJson(response);

        return json;
    }

    private static List<CurrencyResponse> rateCurrencies(List<Currency> currencies) {

        List<CurrencyResponse> tst = currencies.stream()
                .map(currency -> buildCurrencyResponse(currency.getCode()))
                .collect(Collectors.toList());

        return tst;

    }

    private static CurrencyResponse buildCurrencyResponse(String currency) {
        return new CurrencyResponse(currency, rateCurrency(currency));
    }

    private static String rateCurrency(String localCurrency) {

        if("USD".equalsIgnoreCase(localCurrency)){
            return "1";
        }

        JsonObject jsonObj = JsonConverter.getJson("http://data.fixer.io/api/latest?access_key=cea94c9892f047be8f51caa4d2e42e32&symbols=USD," + localCurrency + "&format=1");
        Gson gson = new Gson();
        CurrencyData cData = gson.fromJson(jsonObj.toString(), CurrencyData.class);

        return cData.getRates().get("USD").divide(cData.getRates().get(localCurrency), 2, RoundingMode.DOWN).toString();

    }

}
