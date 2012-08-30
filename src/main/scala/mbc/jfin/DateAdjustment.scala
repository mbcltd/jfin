package mbc.jfin

/**
 * Created with IntelliJ IDEA.
 * User: dmb
 * Date: 30/08/2012
 * Time: 19:26
 * To change this template use File | Settings | File Templates.
 */
import org.scala_tools.time.Imports._

object DateAdjustment {

  // TODO: This shouldn't be hard wired!
  val service = SampleHolidayCalendarService

  def adjust(period: DateRange, calendar: HolidayCalendar, convention: BusinessDayConvention): DateRange = {
    new DateRange(adjust(period.start, calendar, convention), adjust(period.end, calendar, convention))
  }

  def adjust(date: LocalDate, calendar: HolidayCalendar, convention: BusinessDayConvention): LocalDate = convention match {
    case Unadjusted => date
    case Following => following(date, calendar)
    case ModifiedFollowing => modifiedFollowing(date, date, calendar)
    case ModifiedFollowingEndOfMonth => throw new UnsupportedOperationException
    case Preceding => preceding(date, calendar)
    case ModifiedPreceding => modifiedPreceding(date, date, calendar)
  }

  // TODO: These should return Option[LocalDate] and perform maps

  def following(date: LocalDate, calendar: HolidayCalendar): LocalDate = if (service.isWorkingDay(calendar, date).get) date else following(date.plusDays(1), calendar)

  def modifiedFollowing(date: LocalDate, originalDate: LocalDate, calendar: HolidayCalendar): LocalDate =
    if (service.isWorkingDay(calendar, date).get) {
      if (datesInSameMonth(date, originalDate)) date else preceding(originalDate, calendar)
    } else modifiedFollowing(date.plusDays(1), originalDate, calendar)

  def preceding(date: LocalDate, calendar: HolidayCalendar): LocalDate = if (service.isWorkingDay(calendar, date).get) date else preceding(date.minusDays(1), calendar)

  def modifiedPreceding(date: LocalDate, originalDate: LocalDate, calendar: HolidayCalendar): LocalDate =
    if (service.isWorkingDay(calendar, date).get) {
      if (datesInSameMonth(date, originalDate)) date else following(originalDate, calendar)
    } else modifiedPreceding(date.minusDays(1), originalDate, calendar)

  def datesInSameMonth(date1: LocalDate, date2: LocalDate): Boolean = date1.getMonthOfYear == date2.getMonthOfYear
}