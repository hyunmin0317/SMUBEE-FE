package com.example.sumubi

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import java.util.*
import kotlin.collections.HashSet

class SaturdayDecorator: DayViewDecorator {

    val calendar = Calendar.getInstance();

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        day!!.copyTo(calendar)
        val weekDay = calendar.get(Calendar.DAY_OF_WEEK)
        return weekDay == Calendar.SATURDAY
    }

    override fun decorate(view: DayViewFacade?) {
        view!!.addSpan(ForegroundColorSpan(Color.BLUE))
    }
}

class SundayDecorator: DayViewDecorator {

    val calendar = Calendar.getInstance();

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        day!!.copyTo(calendar)
        val weekDay = calendar.get(Calendar.DAY_OF_WEEK)
        return weekDay == Calendar.SUNDAY
    }

    override fun decorate(view: DayViewFacade?) {
        view!!.addSpan(ForegroundColorSpan(Color.RED))
    }
}

class SelectorDecorator(context: Activity): DayViewDecorator {

    val drawable = context.resources.getDrawable(R.drawable.my_selector)

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return true
    }

    override fun decorate(view: DayViewFacade?) {
        view!!.setSelectionDrawable(drawable)
    }
}

class OneDayDecorator: DayViewDecorator {

    val date = CalendarDay.today()

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day!!.equals(date)
    }

    override fun decorate(view: DayViewFacade?) {
        view!!.addSpan(StyleSpan(Typeface.BOLD))
        view!!.addSpan(RelativeSizeSpan(1.4f))
        view!!.addSpan(ForegroundColorSpan(Color.GREEN))
    }
}

class EventDecorator(color: Int, dates: Collection<CalendarDay>): DayViewDecorator {

    val color = color
    val dates = HashSet(dates)

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade?) {
        view!!.addSpan(DotSpan(5F, color))
    }
}