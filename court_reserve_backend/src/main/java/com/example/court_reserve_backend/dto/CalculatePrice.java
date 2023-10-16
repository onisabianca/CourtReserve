package com.example.court_reserve_backend.dto;

import java.math.BigDecimal;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

public class CalculatePrice {

    public static boolean checkMonth(String monthString){
        try {
            Month.valueOf(monthString.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean checkDay(String dayString) {
        try {
            DayOfWeek.valueOf(dayString.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean checkValidTimeInterval(Time start, Time end){
        if(start.toLocalTime().getHour() >= 6 && end.toLocalTime().getHour() <= 22){
            return true;
        }
        return false;
    }

    public static int getDayNumber(String dayString){
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(dayString.toUpperCase());
        return dayOfWeek.getValue();
    }

    public static int getMonthNumber(String monthString){
        Month month = Month.valueOf(monthString.toUpperCase());
        return month.getValue();
    }

    public static int countDaysInMonth(int day, int year, int month) {
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        int daysUntilFirstDayOfWeek = day - firstDayOfMonth.getDayOfWeek().getValue();
        if (daysUntilFirstDayOfWeek < 0) {
            daysUntilFirstDayOfWeek += 7;
        }
        int daysInMonth = firstDayOfMonth.lengthOfMonth();
        int numDays = (daysInMonth - daysUntilFirstDayOfWeek) / 7 + 1;
        return numDays;
    }

    public static int countDaysInCurrentMonth(int day){
        LocalDate currentDate = LocalDate.now();
        LocalDate lastDayOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
        int count = 0;
        while(currentDate.isBefore(lastDayOfMonth) || currentDate.equals(lastDayOfMonth)){
            if (currentDate.getDayOfWeek() == DayOfWeek.of(day)) {
                count++;
            }
            currentDate = currentDate.plusDays(1);
        }
        return count;
    }


    public static String getSeason(int monthNumber){
        Month month = Month.of(monthNumber);
        if(month == Month.JUNE || month == Month.JULY || month == Month.AUGUST){
            return "summer";
        }
        if(month == Month.DECEMBER || month == Month.JANUARY || month == Month.FEBRUARY){
            return "winter";
        }
        return null;
    }

    public static String getTimeOfDay(Time start){
        int hour = start.toLocalTime().getHour();
        if(hour >= 6 && hour <12){
            return "morning";
        }
        if(hour >= 12 && hour < 18){
            return "evening";
        }
        if(hour >= 18 && hour < 22){
            return "night";
        }
        return null;
    }

    public static boolean isDayOfWeekend(int day){
        if(DayOfWeek.of(day) == DayOfWeek.SUNDAY || DayOfWeek.of(day) == DayOfWeek.SATURDAY ){
            return true;
        }
        return false;
    }

    public static int getHoursBetween(Time startTime, Time endTime){
        long timeMillis = endTime.getTime() - startTime.getTime();
        int hours = (int) (timeMillis / (1000 * 60 * 60));
        return hours;
    }

    public static double applyDiscount(double price, int numberOfDays){
        double discountPrice = 0;
        if(numberOfDays == 5){
            discountPrice = price - ((price * 15) / 100 );
        }
        else if(numberOfDays == 4){
            discountPrice = price - ((price * 12) / 100);
        }
        else if(numberOfDays == 3){
            discountPrice = price - ((price * 8) / 100);
        }
        else if(numberOfDays == 2){
            discountPrice = price- ((price * 5) / 100);
        }
        else {
            discountPrice = price;
        }
        return discountPrice;
    }

    public static double lessDecimals(double price){
        BigDecimal bd = new BigDecimal(price);
        bd = bd.setScale(2);
        double approximatedNumber = bd.doubleValue();
        return approximatedNumber;
    }

    public static String monthFromIntToString(int month_nr)
    {
        if(month_nr==0) return "JANUARY";
        if(month_nr==1) return "FEBRUARY";
        if(month_nr==2) return "MARCH";
        if(month_nr==3) return "APRIL";
        if(month_nr==4) return "MAY";
        if(month_nr==5) return "JUNE";
        if(month_nr==6) return "JULY";
        if(month_nr==7) return "AUGUST";
        if(month_nr==8) return "SEPTEMBER";
        if(month_nr==9) return "OCTOBER";
        if(month_nr==10) return "NOVEMBER";
        if(month_nr==11) return "DECEMBER";
        return null;
    }


    public static String getDayOfWeekFromDate(Date givenDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(givenDate);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault());
    }
}
