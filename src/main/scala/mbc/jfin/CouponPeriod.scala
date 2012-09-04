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

import DateAdjustment._
import org.scala_tools.time.TypeImports._

case class DateRange(start: LocalDate, end: LocalDate)

case class CouponPeriod(unadjusted: DateRange, reference: DateRange, businessDayConvention: BusinessDayConvention, holidayCalendar: HolidayCalendar) {

  def this(unadjusted: DateRange, businessDayConvention: BusinessDayConvention, holidayCalendar: HolidayCalendar) =
    this(unadjusted, unadjusted, businessDayConvention, holidayCalendar)

  def adjusted:DateRange = adjust(unadjusted, holidayCalendar, businessDayConvention)

}
