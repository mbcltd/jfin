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
  def generateSchedule(start:LocalDate, end:LocalDate, frequency:Period, stub:StubType) = {
    val scheduleDefinition = stub match {
      case NoStub => new ScheduleDefinition(start,end,frequency) with ShortStub with LastStub
      case ShortFirst => new ScheduleDefinition(start,end,frequency) with ShortStub with StartStub
      case ShortLast => new ScheduleDefinition(start,end,frequency) with ShortStub with LastStub
      case LongFirst => new ScheduleDefinition(start,end,frequency) with LongStub with StartStub
      case LongLast => new ScheduleDefinition(start,end,frequency) with LongStub with LastStub
    }

    convertToTuple( scheduleDefinition.generateSchedule )
  }


  val convertToTuple = (dates:List[LocalDate]) => dates.dropRight(1).zip(dates.drop(1))

}

trait ScheduleGenerator {
  def startDate:LocalDate
  def endDate:LocalDate
  def frequency:Period

  def dateForPoint(i: Int):LocalDate
  def testDateForPoint(i: Int):LocalDate

  def generateSchedule:List[LocalDate]

  def maybeTheEnd(point:Int):Option[LocalDate] = {
    val date = testDateForPoint(point)
    if (date>=endDate) Some(endDate) else if (date<=startDate) Some(startDate) else None
  }

  def generateScheduleFromPoint(point:Int):List[LocalDate] = {
    val nextDate = dateForPoint(point)
    val finalDate = maybeTheEnd(point)
    finalDate.map( _ :: Nil ).getOrElse( nextDate :: generateScheduleFromPoint(point+1) )
  }

}

trait LastStub extends ScheduleGenerator {
  def dateForPoint(i: Int) = startDate + frequency * i
  def generateSchedule:List[LocalDate] = startDate :: generateScheduleFromPoint(1)
}

trait StartStub extends ScheduleGenerator {
  def dateForPoint(i: Int) = endDate - frequency * i
  def generateSchedule:List[LocalDate] = (endDate :: generateScheduleFromPoint(1)).reverse
}

trait ShortStub extends ScheduleGenerator {
  def testDateForPoint(i: Int):LocalDate = dateForPoint(i)
}

trait LongStub extends ScheduleGenerator {
  def testDateForPoint(i: Int):LocalDate = dateForPoint(i+1)
}

abstract case class ScheduleDefinition(startDate:LocalDate, endDate:LocalDate, frequency:Period) extends ScheduleGenerator