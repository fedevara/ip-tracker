package model;

import lombok.Getter;
import lombok.Setter;
import model.mapper.Language;
import model.mapper.Position;

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
    private List<CurrencyResponse> currency;
    private List<TimeZone> timeZone;

    private Position latLong;
    private String averageDistance;

}
