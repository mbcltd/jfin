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