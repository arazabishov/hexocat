package com.abishov.hexocat.home.trending;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

class QueryDateProvider {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    String weekBeforeToday() {
        // Move calendar seven days back.
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);

        // Retrieve date and format it.
        return new SimpleDateFormat(DATE_FORMAT, Locale.US).format(calendar.getTime());
    }
}
