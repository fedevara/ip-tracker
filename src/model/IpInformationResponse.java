package model;

import model.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class IpInformationResponse {

    private String ip;
    private String consultDate;

    private String country;
    private String countryEng;
    private String isoCode;

    private List<Language> language;

    private List<Currency> currency;

    private List<String> timeZone;

    private String distanceEstimated;
    private String distanceUnit;

    private Position localLongitudeCity;
    private Position referenceCity;

    public IpInformationResponse(Country countryIp) {

        consultDate = Utils.getActualDate();

        country = countryIp.getNativeName();
        countryEng = countryIp.getName();
        isoCode = countryIp.getAlpha2Code();

        language = countryIp.getLanguages();

        currency = countryIp.getCurrencies();

        timeZone = countryIp.getTimezones();

    }

    public void fillExtraData(String ip) {

        this.ip = ip;

    }

  /*
    "IP": "83.44.196.93",
    "Fecha": "21/11/2016 16:01:23"
    "País": "España (spain)"
    "ISO Code": "es"
    "Idiomas": "Español (es)"
    "Moneda": "EUR (1 EUR = 1.0631 U$S)"
    "Hora": "20:01:23 (UTC)" o "21:01:23 (UTC+01:00)"
    "Distancia estimada": 10270 kms (-34, -64) a (40, -4)

   */


}
