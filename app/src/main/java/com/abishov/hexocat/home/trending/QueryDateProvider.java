package com.abishov.hexocat.home.trending;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

class QueryDateProvider {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    String dateBefore(int days) {
        // Move calendar seven days back.
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, negatePositiveInt(days));

        // Retrieve date and format it.
        return new SimpleDateFormat(DATE_FORMAT, Locale.US).format(calendar.getTime());
    }

    private static int negatePositiveInt(int days) {
        if (days > 0) {
            return days * -1;
        }

        return days;
    }
}
