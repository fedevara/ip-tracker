package model.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String getActualDate() {

        Date date = new Date();
        String format = "dd/MM/yyyy hh:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        return sdf.format(date);
    }

}
