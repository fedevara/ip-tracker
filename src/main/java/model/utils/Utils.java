package model.utils;

import model.CurrencyResponse;
import model.TimeZone;
import model.mapper.Currency;
import model.mapper.Position;
import service.CurrencyService;
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

    private static final CurrencyService currService = new CurrencyService();

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

        return currService.rateCurrency(localCurrency);

    }

    public static List<TimeZone> valueTimeZone(List<String> timezones) {

        return timezones.stream()
                .map(timeZone -> createTimeZone(timeZone))
                .collect(Collectors.toList());

    }

    private static TimeZone createTimeZone(String timeZone) {

        return new TimeZone(valorizeTimeZone(timeZone), timeZone);

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
