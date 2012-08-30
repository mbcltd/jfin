package mbc.jfin

/**
 * Created with IntelliJ IDEA.
 * User: dmb
 * Date: 30/08/2012
 * Time: 19:25
 * To change this template use File | Settings | File Templates.
 */
import org.scala_tools.time.Imports._
import org.joda.time.DateTimeConstants._


trait HolidayCalendar {
  def name: String
}


case object WesternWeekendHolidayCalendar extends HolidayCalendar {
  def name = "W/E"
}

case object NoneHolidayCalendar extends HolidayCalendar {
  def name = "None"
}

case object LondonBusinessDaysHolidayCalendar extends HolidayCalendar {
  def name = "LNb"
}

trait HolidayCalendarService {
  def isWorkingDay(holidayCalendar: HolidayCalendar, date: LocalDate): Option[Boolean]
}

object SampleHolidayCalendarService extends HolidayCalendarService {
  def isWorkingDay(holidayCalendar: HolidayCalendar, date: LocalDate) = holidayCalendar match {
    case WesternWeekendHolidayCalendar => Some(date.getDayOfWeek != SATURDAY && date.getDayOfWeek != SUNDAY)
    case NoneHolidayCalendar => Some(true)
    case _ => None
  }
}