package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Country {

    private String name;
    private String nativeName;
    private String alpha2Code;
    private List<Double> latlng;
    private List<String> timezones;
    private List<Currency> currencies;
    private List<Language> languages;

}
