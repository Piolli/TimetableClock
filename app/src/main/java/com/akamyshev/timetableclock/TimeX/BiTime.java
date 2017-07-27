package com.akamyshev.timetableclock.TimeX;

import java.util.Date;

/**
 * Created by Alexander on 26.12.2015.
 */
public class BiTime {
    boolean isLesson;
    LessonTime lessonTime;
    RestTime restTime;
    Date maxTimeLesson;

    Message mMessage;

    public BiTime() {

    }

    public BiTime setData(LessonTime lessonTime) {
        this.lessonTime = lessonTime;
        isLesson = true;
        setMessage(Message.NoError);
        return this;
    }

    public BiTime setData(RestTime restTime) {
        this.restTime = restTime;
        isLesson = false;
        setMessage(Message.NoError);
        return this;
    }


    public Date getMaxTimeLesson() {
        return maxTimeLesson;
    }

    public void setMaxTimeLesson(Date maxTimeLesson) {
        this.maxTimeLesson = maxTimeLesson;
    }

    public LessonTime getLessonTime() {
        return lessonTime;
    }

    public RestTime getRestTime() {
        return restTime;
    }

    public boolean isLesson() {
        return isLesson;
    }

    public Message getMessage() {
        return mMessage;
    }

    public BiTime setMessage(Message message) {
        mMessage = message;
        return this;
    }

    public String getStringMessage() {
        Message message = getMessage();
        return headGetStringMessage(message);
    }

    public String getStringMessage(Message message) {
        return headGetStringMessage(message);
    }

    private String headGetStringMessage(Message message) {
        String messageStr = "Message";
        switch (message) {
            case HMRIsNull: {
                messageStr = "HMR is null";
                break;
            }
            case HoursIsNotEqualMinuts: {
                messageStr = "Hours.length != Minutes.length";
                break;
            }
            case TimeIndexOfIsNull: {
                messageStr = "Time index of is null";
                break;
            }
            case TodayIsNotSupport: {
                messageStr = "Today is not support analyzer";
                break;
            }
            case BiTimeIsNull: {
                messageStr = "BiTime is null";
                break;
            }
            case BeforeLesson: {
                messageStr = "Уроки уже закончились";
                break;
            }
            case AfterLesson: {
                messageStr = "Уроки еще не начались";
                break;
            }
            case TodayIsSunday: {
                messageStr = "Сегодня выходной :)";
                break;
            }
        }
        return messageStr;
    }

    /**
     * @HMRIsNull - если массив часов, минут, перемен равен null
     * @HoursIsNotEqualMinuts - длины массивов часов и минут не совпадают
     * @TodayIsNotSupport - этот день не поддерживается
     * @TimeIndexOfIsNull - если все циклы, проверяющие вхождение локального времени возвращают null
     * @BiTimeIsNull - если класс равен null
     * @beforeLesson - если время на устройстве БОЛЬШЕ максимального времени последнего урока
     * @afterLesson - если время на устройстве МЕНЬШЕ максимального времени последнего урока
     * @TodayIsSunday - если на устройстве день недели: воскресенье
     */
    public enum Message { HMRIsNull, HoursIsNotEqualMinuts, NoError, TodayIsNotSupport,
                          TimeIndexOfIsNull, BiTimeIsNull, BeforeLesson, AfterLesson, TodayIsSunday }
}
