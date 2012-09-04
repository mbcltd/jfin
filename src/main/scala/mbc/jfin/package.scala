/*
 * jFin - bond math
 *
 * Copyright (C) 2005-2008, 2012 Morgan Brown Consultancy Ltd.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package mbc.jfin

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
