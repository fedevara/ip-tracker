package service;

import model.Country;
import model.IpInformationResponse;
import com.google.gson.Gson;
import model.utils.Ip2country;

public class ReportService {

    public static String retrieveIpInformation(String ip) {

        Ip2country ip2c = new Ip2country(ip);

        Country countryIp = SingletonCountries.getInstance().find(ip2c.getCountryCode());

        Gson gson = new Gson();
        IpInformationResponse response = new IpInformationResponse(countryIp);
        response.fillExtraData(ip);
        String json = gson.toJson(response);

        return json;

    }
}
