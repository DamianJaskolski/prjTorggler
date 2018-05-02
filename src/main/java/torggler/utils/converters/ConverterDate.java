package torggler.utils.converters;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class ConverterDate {

    public static Date convertToDate(LocalDateTime localDateTime){
     return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime convertToLocalDate(Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        /*
        LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    */
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

 /*   public static LocalDateTime convertToLocaDateTime(LocalDateTime localDateTime){


    }
*/
}
