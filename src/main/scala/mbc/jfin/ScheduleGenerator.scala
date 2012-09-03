package mbc.jfin

/**
 * Created with IntelliJ IDEA.
 * User: dmb
 * Date: 30/08/2012
 * Time: 19:23
 * To change this template use File | Settings | File Templates.
 */
import org.scala_tools.time.Imports._
import mbc.jfin.datemath._

object ScheduleGenerator {

  def generateSchedule(start: LocalDate, end: LocalDate, frequency: Period, stubType: StubType): Schedule = generateSchedule(start, end, frequency, stubType, Unadjusted, NoneHolidayCalendar)

  def generateSchedule(start: LocalDate, end: LocalDate, frequency: Period, stubType: StubType, businessDayConvention: BusinessDayConvention, holidayCalendar: HolidayCalendar): Schedule = stubType match {
    case NoStub => uniformScheduleGenerator(start, end, frequency, businessDayConvention, holidayCalendar, 0)
    case ShortFirst => shortFirstStubScheduleGenerator(start, end, frequency, businessDayConvention, holidayCalendar, 0)
    case ShortLast => shortLastStubScheduleGenerator(start, end, frequency, businessDayConvention, holidayCalendar, 0)
    case LongFirst => longFirstStubScheduleGenerator(start, end, frequency, businessDayConvention, holidayCalendar, 0)
    case LongLast => longLastStubScheduleGenerator(start, end, frequency, businessDayConvention, holidayCalendar, 0)

  }

  def uniformScheduleGenerator(start: LocalDate, end: LocalDate, frequency: Period, businessDayConvention: BusinessDayConvention, holidayCalendar: HolidayCalendar, period: Int): Schedule = {
    val periodStart = start + frequency * period

    assert(!periodStart.isAfter(end), "A non-stub schedule won't fit exactly in start and end dates: " + start + " to " + end)

    val periodEnd = start + frequency * (period + 1)

    if (periodStart.isBefore(end))
      new CouponPeriod(DateRange(periodStart, periodEnd), businessDayConvention, holidayCalendar) :: uniformScheduleGenerator(start, end, frequency, businessDayConvention, holidayCalendar, period + 1)
    else Nil
  }

  def shortLastStubScheduleGenerator(start: LocalDate, end: LocalDate, frequency: Period, businessDayConvention: BusinessDayConvention, holidayCalendar: HolidayCalendar, period: Int): Schedule = {
    val periodStart = start + frequency * period
    val periodEnd = start + frequency * (period + 1)

    if (periodEnd.isEqual(end) || periodEnd.isAfter(end))
      new CouponPeriod(DateRange(periodStart, end), DateRange(periodStart, periodEnd), businessDayConvention, holidayCalendar) :: Nil
    else
      new CouponPeriod(DateRange(periodStart, periodEnd), businessDayConvention, holidayCalendar) :: shortLastStubScheduleGenerator(start, end, frequency, businessDayConvention, holidayCalendar, period + 1)
  }

  def longLastStubScheduleGenerator(start: LocalDate, end: LocalDate, frequency: Period, businessDayConvention: BusinessDayConvention, holidayCalendar: HolidayCalendar, period: Int): Schedule = {
    val periodStart = start + frequency * period
    val periodEnd = start + frequency * (period + 1)
    val nextPeriodEnd = start + frequency * (period + 2)

    if (nextPeriodEnd.isEqual(end) || nextPeriodEnd.isAfter(end))
      new CouponPeriod(DateRange(periodStart, end), DateRange(periodStart, periodEnd), businessDayConvention, holidayCalendar) :: Nil
    else
      new CouponPeriod(DateRange(periodStart, periodEnd), businessDayConvention, holidayCalendar) :: longLastStubScheduleGenerator(start, end, frequency, businessDayConvention, holidayCalendar, period + 1)
  }

  def shortFirstStubScheduleGenerator(start: LocalDate, end: LocalDate, frequency: Period, businessDayConvention: BusinessDayConvention, holidayCalendar: HolidayCalendar, period: Int): Schedule = {
    val periodStart = end - frequency * (period + 1)
    val periodEnd = end - frequency * period

    if (periodStart.isEqual(start) || periodStart.isBefore(start))
      new CouponPeriod(DateRange(start, periodEnd), DateRange(periodStart, periodEnd), businessDayConvention, holidayCalendar) :: Nil
    else
      shortFirstStubScheduleGenerator(start, end, frequency, businessDayConvention, holidayCalendar, period + 1) ::: new CouponPeriod(DateRange(periodStart, periodEnd), businessDayConvention, holidayCalendar) :: Nil
  }

  def longFirstStubScheduleGenerator(start: LocalDate, end: LocalDate, frequency: Period, businessDayConvention: BusinessDayConvention, holidayCalendar: HolidayCalendar, period: Int): Schedule = {
    val periodStart = end - frequency * (period + 1)
    val periodEnd = end - frequency * period
    val nextPeriodStart = end - frequency * (period + 2)

    if (nextPeriodStart.isBefore(start))
      new CouponPeriod(DateRange(start, periodEnd), DateRange(periodStart, periodEnd), businessDayConvention, holidayCalendar) :: Nil
    else
      longFirstStubScheduleGenerator(start, end, frequency, businessDayConvention, holidayCalendar, period + 1) ::: new CouponPeriod(DateRange(periodStart, periodEnd), businessDayConvention, holidayCalendar) :: Nil
  }
}