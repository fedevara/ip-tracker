package model.utils;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ip2country {

    private String countryCode;
    private String countryCode3;
    private String countryName;
    private String countryEmoji;

    public Ip2country(String ip) {

        JsonObject jsonObj = JsonConverter.getJson("https://api.ip2country.info/ip?".concat(ip));

        countryCode = jsonObj.get("countryCode").getAsString();
        countryCode3 = jsonObj.get("countryCode3").getAsString();
        countryName = jsonObj.get("countryName").getAsString();
        countryEmoji = jsonObj.get("countryEmoji").getAsString();

    }

}
