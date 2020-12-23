package service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.mapper.CurrencyData;
import model.utils.JsonConverter;
import model.utils.Utils;

import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CurrencyService {

    private final Map<String, CurrencyData> currencyCache = new HashMap<>();

    public String rateCurrency(String localCurrency) {

        String ret = "";

        try {

            CurrencyData cData;
            if (currencyCache.containsKey(localCurrency)) {
                if (isValidCurrency(localCurrency)) {
                    cData = getCurrencyData(localCurrency);
                } else {
                    cData = currencyCache.get(localCurrency);
                }
            } else {
                cData = getCurrencyData(localCurrency);
            }

            ret = cData.getRates().get("USD").divide(cData.getRates().get(localCurrency), 2, RoundingMode.DOWN).toString();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ret;

    }

    private CurrencyData getCurrencyData(String localCurrency) {
        CurrencyData cData;
        JsonObject jsonObj = JsonConverter.getJson("http://data.fixer.io/api/latest?access_key=cea94c9892f047be8f51caa4d2e42e32&symbols=USD," + localCurrency + "&format=1");
        Gson gson = new Gson();
        cData = gson.fromJson(jsonObj.toString(), CurrencyData.class);
        cData.setUpdatedDate(Utils.getActualDate());
        currencyCache.put(localCurrency, cData);
        return cData;
    }

    private boolean isValidCurrency(String localCurrency) throws ParseException {

        Date dateCurrency = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(currencyCache.get(localCurrency).getUpdatedDate());

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tenSecondsLater = new Timestamp(dateCurrency.getTime()).toLocalDateTime();

        return ChronoUnit.MINUTES.between(now, tenSecondsLater) > 10;

    }
}
