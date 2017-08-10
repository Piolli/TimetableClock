package com.akamyshev.timetableclock

import com.akamyshev.timetableclock.timez.LessonPeriod
import com.akamyshev.timetableclock.timez.TimeAnalyzerResult
import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    val dataTimes = arrayListOf(
            LessonPeriod("8:30", "9:15", 15),
            LessonPeriod("9:30", "10:15", 20),
            LessonPeriod("10:35", "11:20", 20),
            LessonPeriod("11:40", "12:25", 5),
            LessonPeriod("12:30", "13:15", 15),
            LessonPeriod("13:30", "14:15", 15))

    @Test
    fun time_result_is_right1() {



        val testDate = Date(0, 0, 0, 10, 16)
        val timeA = com.akamyshev.timetableclock.timez.TimeAnalyzer(dataTimes, testDate)
        val result: TimeAnalyzerResult = timeA.analyzeTime()

        assertEquals("Remain time: ", 19, result.remainTimeInMinutes)
        assertEquals("Passed time: ", 1, result.passedTimeInMinutes)
        assertEquals("Type of result: ", TimeAnalyzerResult.TypeResult.FreeTime, result.typeResult)
        assertEquals("Length all time: ", 20, result.lenghtAllTimeInMinutes)
        assertEquals("Number lesson: ", 2, result.numberLesson)

    }

    @Test
    fun time_result_is_right2() {

        val testDate = Date(0, 0, 0, 11, 16)
        val timeA = com.akamyshev.timetableclock.timez.TimeAnalyzer(dataTimes, testDate)
        val result: TimeAnalyzerResult = timeA.analyzeTime()

        assertEquals("Remain time: ", 4, result.remainTimeInMinutes)
        assertEquals("Passed time: ", 41, result.passedTimeInMinutes)
        assertEquals("Type of result: ", TimeAnalyzerResult.TypeResult.Lesson, result.typeResult)
        assertEquals("Length all time: ", 45, result.lenghtAllTimeInMinutes)
        assertEquals("Number lesson: ", 3, result.numberLesson)

    }

    @Test
    fun time_result_is_right3() {

        val testDate = Date(0, 0, 0, 8, 30)
        val timeA = com.akamyshev.timetableclock.timez.TimeAnalyzer(dataTimes, testDate)
        val result: TimeAnalyzerResult = timeA.analyzeTime()

        assertEquals("Remain time: ", 45, result.remainTimeInMinutes)
        assertEquals("Passed time: ", 0, result.passedTimeInMinutes)
        assertEquals("Type of result: ", TimeAnalyzerResult.TypeResult.Lesson, result.typeResult)
        assertEquals("Length all time: ", 45, result.lenghtAllTimeInMinutes)
        assertEquals("Number lesson: ", 1, result.numberLesson)

    }


}
