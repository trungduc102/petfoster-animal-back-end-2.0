package com.poly.petfoster.ultils;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.poly.petfoster.response.ApiResponse;

@Component
public class FormatUtils {

    public String dateToString(Date date, String pattern) {

        SimpleDateFormat dateString = null;
        try {
            dateString = new SimpleDateFormat(pattern);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString.format(date);
    }

    public Date stringToDate(String dateString, String pattern) {

        SimpleDateFormat format = null;
        Date date = null;
        try {
            format = new SimpleDateFormat(pattern);
            date = format.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }

    public Date convertMilisecondsToDate(String miliString) throws NumberFormatException {
        Long miliseconds = Long.parseLong(miliString);
        return new Date(miliseconds);
    }

    public Date dateToDateFormat(Date date, String pattern) {

        SimpleDateFormat format = new SimpleDateFormat(pattern);

        Date formattedDate = null;
        try {
            formattedDate = format.parse(format.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return formattedDate;
    }

    public String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    public Map<String, Date> changeDateRange(Optional<Date> minDate, Optional<Date> maxDate) {

        Date minDateValue = minDate.orElse(null);
        Date maxDateValue = maxDate.orElse(null);

        if (minDateValue == null && maxDateValue != null) {
            minDateValue = maxDateValue;
        }

        if (maxDateValue == null && minDateValue != null) {
            maxDateValue = minDateValue;
        }

        Map<String, Date> result = new HashMap<>();
        result.put("minDateValue", minDateValue);
        result.put("maxDateValue", maxDateValue);

        return result;

    }

    //join address
    public String getAddress(String street, String ward, String district, String province) {
        return String.join(", ", street, ward, district, province);
    }

}
