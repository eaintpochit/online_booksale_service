package com.example.crudapi.util;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;


@Service
public class DateTimeUtil {

    public Date dobFormat(String strDate) {

        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = dateFormat.parse(strDate);
        } catch (ParseException ignored) {
        }


        return date;
    }

    public Date getCurrentDateTime() {
        TimeZone.setDefault(TimeZone.getTimeZone("MMT"));
        return Date.from(LocalDateTime.now().atZone(ZoneId.of("Asia/Yangon")).toInstant());

    }

    public String getStringDate() {
        String date = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        try {
            date = formatter.format(Instant.now());
        } catch (DateTimeException de) {
        }
        return date;
    }
}
