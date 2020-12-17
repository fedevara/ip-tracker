package model.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class CurrencyData {

    private String success;
    private String timestamp;
    private String base;
    private String date;
    private Map<String, BigDecimal> rates;

}
