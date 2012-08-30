package mbc.jfin

/**
 * Created with IntelliJ IDEA.
 * User: dmb
 * Date: 30/08/2012
 * Time: 19:24
 * To change this template use File | Settings | File Templates.
 */
import DateAdjustment._
import org.scala_tools.time.TypeImports._

case class DateRange(start: LocalDate, end: LocalDate)

case class CouponPeriod(unadjusted: DateRange, reference: DateRange, businessDayConvention: BusinessDayConvention, holidayCalendar: HolidayCalendar) {

  def this(unadjusted: DateRange, businessDayConvention: BusinessDayConvention, holidayCalendar: HolidayCalendar) =
    this(unadjusted, unadjusted, businessDayConvention, holidayCalendar)

  def adjusted:DateRange = adjust(unadjusted, holidayCalendar, businessDayConvention)

}
