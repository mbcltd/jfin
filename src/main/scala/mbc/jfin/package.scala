package mbc.jfin

/**
 * Created with IntelliJ IDEA.
 * User: dmb
 * Date: 30/08/2012
 * Time: 19:30
 * To change this template use File | Settings | File Templates.
 */
import org.scala_tools.time.Imports._
import org.scala_tools.time.Imports.Period._

package object datemath {

  type Schedule = List[CouponPeriod]

  implicit def MultiplyablePeriod(underlying: Period) = new {
    def *(times: Int): Period = new Period(underlying.years * times, underlying.months * times, underlying.weeks * times, underlying.days * times, underlying.hours * times, underlying.minutes * times, underlying.seconds * times, underlying.millis * times, underlying.periodType)
  }

  implicit def PeriodGeneratingInt(value: Int) = new {
    def YEARS = years(value)

    def YEAR = YEARS

    def Y = YEARS

    def MONTHS = months(value)

    def MONTH = MONTHS

    def M = MONTHS

    def WEEKS = weeks(value)

    def WEEK = WEEKS

    def W = WEEKS

    def DAYS = days(value)

    def DAY = DAYS

    def D = DAYS
  }
}
