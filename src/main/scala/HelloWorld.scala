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

import org.scala_tools.time.Imports._
import mbc.jfin._
import org.scala_tools.time.Imports.Period._

object HelloWorld extends App {


  val scheduleGenerator = new ScheduleDefinition(new LocalDate(2006, 3, 28), new LocalDate(2011, 2, 28), years(1)) with LastStub with LongStub

  val schedule = ScheduleGenerator.generateSchedule(new LocalDate(2006, 3, 28), new LocalDate(2011, 2, 28), years(1), NoStub)

  schedule.foreach(println(_))


}
