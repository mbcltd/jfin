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