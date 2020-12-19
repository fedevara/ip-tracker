package model.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.CurrencyResponse;
import model.TimeZone;
import model.mapper.Currency;
import model.mapper.CurrencyData;
import model.mapper.Position;

import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static String getActualDate() {

        Date date = new Date();
        String format = "dd/MM/yyyy hh:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        return sdf.format(date);

    }

    public static List<CurrencyResponse> rateCurrencies(List<Currency> currencies) {

        return currencies.stream()
                .map(currency -> buildCurrencyResponse(currency.getCode()))
                .collect(Collectors.toList());

    }

    private static CurrencyResponse buildCurrencyResponse(String currency) {
        return new CurrencyResponse(currency, rateCurrency(currency));
    }

    private static String rateCurrency(String localCurrency) {

        if ("USD".equalsIgnoreCase(localCurrency)) {
            return "1";
        }

        JsonObject jsonObj = JsonConverter.getJson("http://data.fixer.io/api/latest?access_key=cea94c9892f047be8f51caa4d2e42e32&symbols=USD," + localCurrency + "&format=1");
        Gson gson = new Gson();
        CurrencyData cData = gson.fromJson(jsonObj.toString(), CurrencyData.class);

        return cData.getRates().get("USD").divide(cData.getRates().get(localCurrency), 2, RoundingMode.DOWN).toString();

    }

    public static List<TimeZone> valueTimeZone(List<String> timezones) {

        return timezones.stream()
                .map(timeZone -> createTimeZone(timeZone))
                .collect(Collectors.toList());

    }

    private static TimeZone createTimeZone(String timeZone) {

        return new TimeZone(timeZone, valorizeTimeZone(timeZone));

    }

    private static String valorizeTimeZone(String timeZone) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");

        ZoneId fromTimeZone = ZoneId.of((Calendar.getInstance().getTimeZone()).getID());
        ZoneId toTimeZone = ZoneId.of(timeZone);

        ZonedDateTime currentISTime = LocalDateTime.now().atZone(fromTimeZone);
        ZonedDateTime currentETime = currentISTime.withZoneSameInstant(toTimeZone);

        return formatter.format(currentETime);

    }

    public static String calculateDistance(Position latLong) {

        return String.valueOf(getDistance(-34, -64, latLong.getLatitude(), latLong.getLongitude()));

    }

    public static int getDistance(double lat1, double lon1, double lat2, double lon2) {

        Double distance = Math.acos(Math.sin(lat2 * Math.PI / 180.0) * Math.sin(lat1 * Math.PI / 180.0) +
                Math.cos(lat2 * Math.PI / 180.0) * Math.cos(lat1 * Math.PI / 180.0) *
                        Math.cos((lon1 - lon2) * Math.PI / 180.0)) * 6371;
        return distance.intValue();
    }

}
