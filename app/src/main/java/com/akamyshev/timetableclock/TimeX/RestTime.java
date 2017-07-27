package com.akamyshev.timetableclock.TimeX;

import java.sql.Time;

/**
 * Created by Alexander on 24.12.2015.
 */
public class RestTime {

    int restPassed,               //Перемена уже прошла по времени: mm
        restAfterNumberLesson,    //Идет перемена после N урока
        restCompleteIn,           //Перемена закончится через: mm
        restFullTime;             //Перемена длится: mm
    Time nextLessonTime;          //Следующий урок начнется в: hh:mm
    TimeAnalyzer.typeToday today; //Тип расписания

    public int getRestAfterNumberLesson()   {return restAfterNumberLesson;}
    public Time getNextLessonTime()         {return nextLessonTime;}
    public int getRestCompleteIn()          {return restCompleteIn;}
    public int getRestFullTime()            {return restFullTime;}
    public int getRestPassed()              {return restPassed;}
    public TimeAnalyzer.typeToday getToday(){return today;}

    public void setNextLessonTime(Time nextLessonTime)              {this.nextLessonTime = nextLessonTime;}
    public void setRestAfterNumberLesson(int restAfterNumberLesson) {this.restAfterNumberLesson = restAfterNumberLesson;}
    public void setRestCompleteIn(int restCompleteIn)               {this.restCompleteIn = restCompleteIn;}
    public void setRestFullTime(int restFullTime)                   {this.restFullTime = restFullTime;}
    public void setRestPassed(int restPassed)                       {this.restPassed = restPassed;}
    public void setToday(TimeAnalyzer.typeToday today)              {this.today = today;}
}
