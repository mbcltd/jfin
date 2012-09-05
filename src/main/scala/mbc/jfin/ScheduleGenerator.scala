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
import mbc.jfin.datemath._

object ScheduleGenerator {

  def generateNormalScheduleList(start:LocalDate, end:LocalDate, frequency:Period, stub:StubType):List[LocalDate] = ScheduleDefinitionForStubType(stub, start, end, frequency).generateNormalSchedule
  def generateReferenceScheduleList(start:LocalDate, end:LocalDate, frequency:Period, stub:StubType):List[LocalDate] = ScheduleDefinitionForStubType(stub, start, end, frequency).generateReferenceSchedule

  val listToPeriods = (dates:List[LocalDate]) => dates.dropRight(1).zip(dates.drop(1))

  private def ScheduleDefinitionForStubType(stub: StubType, start: LocalDate, end: LocalDate, frequency: Period):ScheduleGenerator = stub match {
      case NoStub => new ScheduleDefinition(start, end, frequency) with RegularStub
      case ShortFirst => new ScheduleDefinition(start, end, frequency) with ShortStub with StartStub
      case ShortLast => new ScheduleDefinition(start, end, frequency) with ShortStub with LastStub
      case LongFirst => new ScheduleDefinition(start, end, frequency) with LongStub with StartStub
      case LongLast => new ScheduleDefinition(start, end, frequency) with LongStub with LastStub
    }
}

private trait ScheduleGenerator {
  def startDate:LocalDate
  def endDate:LocalDate
  def frequency:Period

  def dateForPoint(i: Int):LocalDate
  def testDateForPoint(i: Int):LocalDate

  def generateNormalSchedule:List[LocalDate]
  def generateReferenceSchedule:List[LocalDate]
  def maybeTheEndPoint(point:Int):Option[LocalDate] = maybeTheEndDate(testDateForPoint(point))
  def maybeTheEndDate(date:LocalDate):Option[LocalDate]

  def generateNormalScheduleFromPoint(point:Int):List[LocalDate] = {
    val nextDate = dateForPoint(point)
    val finalDate = maybeTheEndPoint(point)
    finalDate.map( _ :: Nil ).getOrElse( nextDate :: generateNormalScheduleFromPoint(point+1) )
  }

  def generateReferenceScheduleFromPoint(point:Int):List[LocalDate] = {
    val nextDate = dateForPoint(point)
    val finalDate = maybeTheEndPoint(point)
    if( finalDate.isDefined ) nextDate :: Nil else nextDate :: generateReferenceScheduleFromPoint(point+1)
  }

}

private trait RegularStub extends ScheduleGenerator {
  def dateForPoint(i: Int) = startDate + frequency * i
  def generateNormalSchedule:List[LocalDate] = startDate :: generateNormalScheduleFromPoint(1)
  def generateReferenceSchedule:List[LocalDate] = startDate :: generateReferenceScheduleFromPoint(1)
  def testDateForPoint(i: Int):LocalDate = dateForPoint(i)
  def maybeTheEndDate(date:LocalDate):Option[LocalDate] = {
    assert(date<=endDate, "Regular frequency does not fit exactly into date range with NoStub")
    if (date==endDate) Some(endDate) else None
  }
}

private trait LastStub extends ScheduleGenerator {
  def dateForPoint(i: Int) = startDate + frequency * i
  def generateNormalSchedule:List[LocalDate] = startDate :: generateNormalScheduleFromPoint(1)
  def generateReferenceSchedule:List[LocalDate] = startDate :: generateReferenceScheduleFromPoint(1)

  def maybeTheEndDate(date:LocalDate):Option[LocalDate] = if (date>=endDate) Some(endDate) else None
}

private trait StartStub extends ScheduleGenerator {
  def dateForPoint(i: Int) = endDate - frequency * i
  def generateNormalSchedule:List[LocalDate] = (endDate :: generateNormalScheduleFromPoint(1)).reverse
  def generateReferenceSchedule:List[LocalDate] = (endDate :: generateReferenceScheduleFromPoint(1)).reverse

  def maybeTheEndDate(date:LocalDate):Option[LocalDate] = if (date<=startDate) Some(startDate) else None
}

private trait ShortStub extends ScheduleGenerator {
  def testDateForPoint(i: Int):LocalDate = dateForPoint(i)
}

private trait LongStub extends ScheduleGenerator {
  def testDateForPoint(i: Int):LocalDate = dateForPoint(i+1)
}

private case class ScheduleDefinition(startDate:LocalDate, endDate:LocalDate, frequency:Period)