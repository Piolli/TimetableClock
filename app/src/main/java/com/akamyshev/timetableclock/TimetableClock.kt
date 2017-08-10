package com.akamyshev.timetableclock


import android.os.Bundle
import android.os.Handler
import android.support.annotation.IntRange
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.akamyshev.timetableclock.TimeX.*
import java.util.*
import com.akamyshev.timetableclock.timez.LessonPeriod
import com.akamyshev.timetableclock.timez.TimeAnalyzerResult
import kotlinx.android.synthetic.main.fragment_timetable_clock.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class TimetableClock : Fragment(), TimetableClockMvp.View {
    val handler = Handler()



    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_timetable_clock, container, false)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        clock_lessons_recycler_view.layoutManager = LinearLayoutManager(context)
        clock_lessons_recycler_view.setHasFixedSize(true)

        val dataTimes = arrayListOf(LessonPeriod("8:30", "9:15", 15),
                LessonPeriod("9:30", "10:15", 20),
                LessonPeriod("10:35", "11:20", 20),
                LessonPeriod("11:40", "12:25", 5),
                LessonPeriod("12:30", "13:15", 15),
                LessonPeriod("13:30", "14:15", 15))

        val gregC = GregorianCalendar()

        val timeA = com.akamyshev.timetableclock.timez.TimeAnalyzer(dataTimes, Date())
        val result: TimeAnalyzerResult = timeA.analyzeTime()
        val adapter = LessonRecyclerAdapter(result.numberLesson, dataTimes)

        val mResreshInfoRunnable = object : Runnable {
            override fun run() {

                gregC.add(GregorianCalendar.MINUTE, 1)

                //Log.d("NowTime", "${gregC[Calendar.HOUR_OF_DAY]}:${gregC[Calendar.MINUTE]}")

                val timeA = com.akamyshev.timetableclock.timez.TimeAnalyzer(dataTimes, gregC.time)
                val result: TimeAnalyzerResult = timeA.analyzeTime()

                //Log.d("TimeRESULT",result.toString())
                context.run {

                    clock_progress_view.progress = result.passedTimeInMinutes.toFloat()
                    clock_progress_view.max = result.lenghtAllTimeInMinutes.toFloat()

                    adapter.setNumber(result.numberLesson)
                    clock_title_progress_lesson.text = "Идет ${if(result.typeResult == TimeAnalyzerResult.TypeResult.Lesson) "${result.numberLesson} урок" else "перемена после ${result.numberLesson} урока"}"
                    clock_info_remain_time.text = "До конца ${if(result.typeResult == TimeAnalyzerResult.TypeResult.Lesson) "урока" else "перемены"} осталось ${result.remainTimeInMinutes} минут(ы)"
                }
                if(result.message == TimeAnalyzerResult.AnalyzerMessage.empty)
                    handler.postDelayed(this, 500)
            }
        }
        mResreshInfoRunnable.run()


        clock_lessons_recycler_view.adapter = adapter
    }

    class LessonRecyclerAdapter(var numberLesson: Int, val data: ArrayList<LessonPeriod>) : RecyclerView.Adapter<LessonViewHolder>() {

        override fun onBindViewHolder(holder: LessonViewHolder?, position: Int) {
            holder?.bindView(TimetableClock.LessonTime(Time(data[position].startHour, data[position].startMinute), Time(data[position].endHour, data[position].endtMinute), 10), numberLesson, position+1)
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): LessonViewHolder {
            val view = LayoutInflater.from(parent?.context).inflate(R.layout.clock_info_item, parent, false)
            return LessonViewHolder(view)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        fun setNumber(numberLesson1: Int) {
            Log.d("LessonAdapter", "Adapter.setNumber($numberLesson1)")
            if(numberLesson1 != numberLesson) {
                numberLesson = numberLesson1
                notifyDataSetChanged()
            }
        }

    }

    class LessonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numberLesson = itemView.findViewById<TextView>(R.id.clock_info_item_number_lesson_text_view)
        val titleLesson = itemView.findViewById<TextView>(R.id.clock_info_item_title_lesson)
        val freeTime = itemView.findViewById<TextView>(R.id.clock_info_item_free_time_text_view)
        val imageView = itemView.findViewById<ImageView>(R.id.clock_info_item_is_now_image_view)
        val lessonPeriod = itemView.findViewById<TextView>(R.id.clock_info_item_lesson_period)

        fun bindView(lessonTime: LessonTime, goingLessonPosition: Int, position: Int) {
            numberLesson.text = position.toString()
            imageView.setImageDrawable(if (goingLessonPosition == position) ContextCompat.getDrawable(itemView.context, android.R.drawable.presence_online)
            else ContextCompat.getDrawable(itemView.context, android.R.drawable.presence_invisible))

            lessonPeriod.text = "С  ${lessonTime.startTime} до ${lessonTime.endTime}"
            titleLesson.text = listOf("Русский язык", "Математика")[position % 2] + " (30$position)"
            freeTime.text = "перемена после урока ${lessonTime.freeTime} минут"
        }
    }

    class Time(@IntRange(from = 0, to = 23) val hour: Int, @IntRange(from = 0, to = 59) val min: Int) {
        override fun toString(): String = "$hour:$min"
    }

    class LessonTime(val startTime: Time, val endTime: Time, val freeTime: Int)

    override fun onStart() {
        super.onStart()
//        val mResreshInfoRunnable = object : Runnable {
//            override fun run() {
////                Log.i("HANDLER", "in handler: " + Calendar.getInstance().get(Calendar.MINUTE) + ":" + Calendar.getInstance().get(Calendar.SECOND))
////                if (Calendar.getInstance().get(Calendar.SECOND) == 0) {
////                    Log.i("TimetableClock", "seconds == 0")
////                    if (!setAllData()) return
////                }
//                roundCornerProgressBar.progress++
//                handler.postDelayed(this, 1000)
//            }
//        }
//        mResreshInfoRunnable.run()

    }
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
//        arc_progress_view?.setMax(rest.getRestFullTime());
//        arc_progress_view?.setProgress(rest.getRestPassed());
//        arc_progress_view?.setBottomText("Минут(ы) прошло");
//        arc_progress_view?.refreshDrawableState();
//    }
//
//    private fun setLessonData(lesson: LessonTime) {
//
//        arc_progress_view?.max = ((lesson.lessonEndAt.time - lesson.lessonStartAt.time) / 1000 / 60).toInt();
//        arc_progress_view?.progress = lesson.lessonPassed;
//        arc_progress_view?.bottomText = lesson.lessonPassed.toString();
//        arc_progress_view?.refreshDrawableState();
//    }




}
