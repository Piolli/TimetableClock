package com.akamyshev.timetableclock.TimeX;

import java.sql.Time;

/**
 * Created by Alexander on 24.12.2015.
 */
public class LessonTime {

    int  restAfterLesson,   //Перемена после урока: mm
         numberLesson,      //Номер урока
         lessonCompleteIn,  //Урок закончится через: mm
         lessonPassed;      //Урок идет по времени: mm

    Time lessonStartAt,     //Урок начался в: hh:mm
         lessonEndAt;       //Урок закончится в hh:mm

    TimeAnalyzer.typeToday today; //Тип расписания

    public int getRestAfterLesson()  {return restAfterLesson;}
    public int getNumberLesson()     {return numberLesson;}
    public int getLessonCompleteIn() {return lessonCompleteIn;}
    public Time getLessonEndAt()     {return lessonEndAt;}
    public int getLessonPassed()     {return lessonPassed;}
    public Time getLessonStartAt()   {return lessonStartAt;}
    public TimeAnalyzer.typeToday getToday() {return today;}

    public void setRestAfterLesson(int restAfterLesson)    {this.restAfterLesson = restAfterLesson;}
    public void setNumberLesson(int numberLesson)          {this.numberLesson = numberLesson;}
    public void setLessonCompleteIn(int lessonCompleteIn)  {this.lessonCompleteIn = lessonCompleteIn;}
    public void setLessonEndAt(Time lessonEndAt)           {this.lessonEndAt = lessonEndAt;}
    public void setLessonPassed(int lessonPassed)          {this.lessonPassed = lessonPassed;}
    public void setLessonStartAt(Time lessonStartAt)       {this.lessonStartAt = lessonStartAt;}
    public void setToday(TimeAnalyzer.typeToday today)     {this.today = today;}
}
