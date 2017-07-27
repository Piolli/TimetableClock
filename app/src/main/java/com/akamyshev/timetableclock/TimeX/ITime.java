package com.akamyshev.timetableclock.TimeX;



import java.util.Date;

/**
 * Created by Alexander on 26.12.2015.
 */
public class ITime {
//    @SerializedName("date")
    public int[] date;

//    @SerializedName("hours")
    public int[] hours;

//    @SerializedName("minut")
    public int[] minut;

//    @SerializedName("rests")
    public int[] rests;

    public void setHours(int[] hours) {
        this.hours = hours;
    }

    public void setMinut(int[] minut) {
        this.minut = minut;
    }

    public void setRests(int[] rests) {
        this.rests = rests;
    }

    public Date getTime() {
        Date time = new Date(0);
        if(date == null) return null;
            time.setDate(date[0]);
            time.setMonth(date[1] - 1); //-1 так как Calendar month начинается с 0
            time.setYear(date[2]);
        return time;
    }

    public String getDateString() {
        if(date == null) return "";
        StringBuilder dateString = new StringBuilder();
        for(int i = 0; i < date.length; i++)
            dateString.append(date[i] + ".");
        dateString.setCharAt(dateString.length() - 1, '\0');
        return dateString.toString();
    }
}
