package com.akamyshev.timetableclock


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.annotation.IntRange
import android.support.annotation.UiThread
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.akamyshev.timetableclock.TimeX.*
import java.util.*
import com.akamyshev.timetableclock.UtilsX.LessonTime.getLessonTime
import kotlinx.android.synthetic.main.fragment_timetable_clock.*


/**
 * A simple [Fragment] subclass.
 */
class TimetableClock : Fragment() {
    var timeAnalyzer: TimeAnalyzer?= null
    var arcProgressView: ArcProgress? = null
    val handler = Handler()


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_timetable_clock, container, false)
        timeAnalyzer = TimeAnalyzer(context)
        arcProgressView = view.findViewById(R.id.arc_progress_view)

        val recycler = view.findViewById<RecyclerView>(R.id.clock_lessons_recycler_view)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.setHasFixedSize(true)

        recycler.adapter = LessonRecyclerAdapter()

        return view
    }

    class LessonRecyclerAdapter : RecyclerView.Adapter<LessonViewHolder>() {

        override fun onBindViewHolder(holder: LessonViewHolder?, position: Int) {
            holder?.bindView(TimetableClock.LessonTime(Time(8, 30), Time(9, 15), 10), position+1)
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): LessonViewHolder {
            val view = LayoutInflater.from(parent?.context).inflate(R.layout.clock_info_item, parent, false)
            return LessonViewHolder(view)
        }

        override fun getItemCount(): Int {
            return 6
        }

    }

    class LessonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numberLesson = itemView.findViewById<TextView>(R.id.clock_info_item_number_lesson_text_view)
        val titleLesson = itemView.findViewById<TextView>(R.id.clock_info_item_title_lesson)
        val freeTime = itemView.findViewById<TextView>(R.id.clock_info_item_free_time_text_view)

        fun bindView(lessonTime: LessonTime, position: Int) {
            numberLesson.text = position.toString()
//            titleLesson.text = "С  ${lessonTime.startTime} до ${lessonTime.endTime}"
            titleLesson.text = listOf("Русский язык", "Математика")[position % 2]
            freeTime.text = "перемена после урока ${lessonTime.freeTime} минут"
        }
    }

    class Time(@IntRange(from = 0, to = 23) val hour: Int, @IntRange(from = 0, to = 59) val min: Int) {
        override fun toString(): String = "$hour:$min"
    }

    class LessonTime(val startTime: Time, val endTime: Time, val freeTime: Int)

//    override fun onStart() {
//        super.onStart()
//        val mResreshInfoRunnable = object : Runnable {
//            override fun run() {
//                Log.i("HANDLER", "in handler: " + Calendar.getInstance().get(Calendar.MINUTE) + ":" + Calendar.getInstance().get(Calendar.SECOND))
//                if (Calendar.getInstance().get(Calendar.SECOND) == 0) {
//                    Log.i("TimetableClock", "seconds == 0")
//                    if (!setAllData()) return
//                }
//                handler.postDelayed(this, 1000)
//            }
//        }
//        mResreshInfoRunnable.run()
//
//    }
//
//
//
//    private fun setAllData(): Boolean {
//        var biTime = timeAnalyzer?.getTimeLesson()
//        if (biTime == null) {
//            biTime = BiTime().setMessage(BiTime.Message.BiTimeIsNull)
//            setDataMessage(biTime!!.getStringMessage())
//            return false
//        }
//        if (biTime!!.getMessage() != BiTime.Message.NoError) {
//            setDataMessage(biTime!!.getStringMessage())
//            return false
//        }
//
//        if (biTime!!.isLesson()) {
//            val lesson = biTime!!.getLessonTime()
//            setLessonData(lesson)
//        } else {
//            val rest = biTime!!.getRestTime()
//            setRestData(rest)
//        }
//        return true
//    }
//
//    private fun setDataMessage(stringMessage: String) {
//        Toast.makeText(context, stringMessage, Toast.LENGTH_SHORT).show()
//    }
//
//    private fun setRestData(rest: RestTime) {
//
//
//        arcProgressView?.setMax(rest.getRestFullTime());
//        arcProgressView?.setProgress(rest.getRestPassed());
//        arcProgressView?.setBottomText("Минут(ы) прошло");
//        arcProgressView?.refreshDrawableState();
//    }
//
//    private fun setLessonData(lesson: LessonTime) {
//
//        arcProgressView?.max = ((lesson.lessonEndAt.time - lesson.lessonStartAt.time) / 1000 / 60).toInt();
//        arcProgressView?.progress = lesson.lessonPassed;
//        arcProgressView?.bottomText = lesson.lessonPassed.toString();
//        arcProgressView?.refreshDrawableState();
//    }




}
