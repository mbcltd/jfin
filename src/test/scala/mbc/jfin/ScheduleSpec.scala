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

import org.specs2.mutable.SpecificationWithJUnit
import org.scala_tools.time.Imports._
import org.scala_tools.time.Imports.Period._
import org.specs2.matcher.{MatchResult, DataTables}
import org.scala_tools.time.Imports

trait TheTest {
  this: SpecificationWithJUnit =>
  val test: List[(LocalDate,LocalDate)] => (Int, (Int, Int, Int), (Int, Int, Int)) => MatchResult[Imports.LocalDate] = schedule => {
    case (index, (ys, ms, ds), (ye, me, se)) =>
      schedule(index)._1 must be equalTo (new LocalDate(ys, ms, ds))
      schedule(index)._2 must be equalTo (new LocalDate(ye, me, se))
  }
}

class ScheduleSpec extends SpecificationWithJUnit with DataTables with TheTest {

  "An annual schedule with no stub from 2006-3-28 to 2011-3-28" should {
    val schedule = ScheduleGenerator.listToPeriods( ScheduleGenerator.generateNormalScheduleList(new LocalDate(2006, 3, 28), new LocalDate(2011, 3, 28), years(1), NoStub) )

    "contain 5 periods" in {
      schedule must have size (5)
    }

    "match the following periods" in {

      "period" | "start" | "end" |
        0 !(2006, 3, 28) !(2007, 3, 28) |
        1 !(2007, 3, 28) !(2008, 3, 28) |
        2 !(2008, 3, 28) !(2009, 3, 28) |
        3 !(2009, 3, 28) !(2010, 3, 28) |
        4 !(2010, 3, 28) !(2011, 3, 28) |> test(schedule)
    }
  }

  "A quarterly schedule with no stub from 2006-03-28 to 2007-03-28" should {
    val schedule = ScheduleGenerator.listToPeriods( ScheduleGenerator.generateNormalScheduleList(new LocalDate(2006, 3, 28), new LocalDate(2007, 3, 28), months(3), NoStub) )

    "contain 4 periods" in {
      schedule must have size (4)
    }

    "match the following periods" in {
      "period" | "start" | "end" |
        0 !(2006, 3, 28) !(2006, 6, 28) |
        1 !(2006, 6, 28) !(2006, 9, 28) |
        2 !(2006, 9, 28) !(2006, 12, 28) |
        3 !(2006, 12, 28) !(2007, 3, 28) |> test(schedule)
    }
  }

  "A quarterly schedule with no stub from 2006-03-31 to 2007-03-31" should {
    val schedule = ScheduleGenerator.listToPeriods( ScheduleGenerator.generateNormalScheduleList(new LocalDate(2006, 3, 31), new LocalDate(2007, 3, 31), months(3), NoStub) )

    "contain 4 periods" in {
      schedule must have size (4)
    }

    "match the following periods" in {
      "period" | "start" | "end" |
        0 !(2006, 3, 31) !(2006, 6, 30) |
        1 !(2006, 6, 30) !(2006, 9, 30) |
        2 !(2006, 9, 30) !(2006, 12, 31) |
        3 !(2006, 12, 31) !(2007, 3, 31) |> test(schedule)
    }

  }

  "An annual schedule with no stub from 2006-03-28 to 2011-02-28" should {
    "fail to generate an irregular schedule" in {
      ScheduleGenerator.generateNormalScheduleList(new LocalDate(2006, 3, 28), new LocalDate(2011, 2, 28), years(1), NoStub) must throwA[AssertionError]
    }
  }

  "An annual schedule with a short last stub from 2006-3-28 to 2011-4-28" should {
    val schedule = ScheduleGenerator.listToPeriods( ScheduleGenerator.generateNormalScheduleList(new LocalDate(2006, 3, 28), new LocalDate(2011, 4, 28), years(1), ShortLast) )

    "contain 6 periods" in {
      schedule must have size (6)
    }

    "match the following periods" in {
      "period" | "start" | "end" |
        0 !(2006, 3, 28) !(2007, 3, 28) |
        1 !(2007, 3, 28) !(2008, 3, 28) |
        2 !(2008, 3, 28) !(2009, 3, 28) |
        3 !(2009, 3, 28) !(2010, 3, 28) |
        4 !(2010, 3, 28) !(2011, 3, 28) |
        5 !(2011, 3, 28) !(2011, 4, 28) |> test(schedule)
    }
  }

  "An annual schedule with a long last stub from 2006-3-28 to 2011-4-28" should {
    val schedule = ScheduleGenerator.listToPeriods( ScheduleGenerator.generateNormalScheduleList(new LocalDate(2006, 3, 28), new LocalDate(2011, 4, 28), years(1), LongLast) )

    "contain 5 periods" in {
      schedule must have size (5)
    }

    "match the following periods" in {
      "period" | "start" | "end" |
        0 !(2006, 3, 28) !(2007, 3, 28) |
        1 !(2007, 3, 28) !(2008, 3, 28) |
        2 !(2008, 3, 28) !(2009, 3, 28) |
        3 !(2009, 3, 28) !(2010, 3, 28) |
        4 !(2010, 3, 28) !(2011, 4, 28) |> test(schedule)
    }
  }

  "An annual schedule with a short first stub from 2006-3-28 to 2011-4-28" should {
    val schedule = ScheduleGenerator.listToPeriods( ScheduleGenerator.generateNormalScheduleList(new LocalDate(2006, 3, 28), new LocalDate(2011, 4, 28), years(1), ShortFirst) )

    "contain 6 periods" in {
      schedule must have size (6)
    }

    "match the following periods" in {
      "period" | "start" | "end" |
        0 !(2006, 3, 28) !(2006, 4, 28) |
        1 !(2006, 4, 28) !(2007, 4, 28) |
        2 !(2007, 4, 28) !(2008, 4, 28) |
        3 !(2008, 4, 28) !(2009, 4, 28) |
        4 !(2009, 4, 28) !(2010, 4, 28) |
        5 !(2010, 4, 28) !(2011, 4, 28) |> test(schedule)
    }
  }

  "An annual schedule with a long first stub from 2006-3-28 to 2011-4-28" should {
    val schedule = ScheduleGenerator.listToPeriods(ScheduleGenerator.generateNormalScheduleList(new LocalDate(2006, 3, 28), new LocalDate(2011, 4, 28), years(1), LongFirst))

    "contain 5 periods" in {
      schedule must have size (5)
    }

    "match the following periods" in {
      "period" | "start" | "end" |
        0 !(2006, 3, 28) !(2007, 4, 28) |
        1 !(2007, 4, 28) !(2008, 4, 28) |
        2 !(2008, 4, 28) !(2009, 4, 28) |
        3 !(2009, 4, 28) !(2010, 4, 28) |
        4 !(2010, 4, 28) !(2011, 4, 28) |> test(schedule)
    }
  }
}
