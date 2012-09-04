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
import org.joda.time

trait ScheduleGenerator {
  def startDate:LocalDate
  def endDate:LocalDate
  def frequency:Period

  def dateForPoint(i: Int) = startDate + frequency * i

  def generateSchedule:List[LocalDate] = startDate :: generateScheduleFromPoint(1)

  def maybeTheEnd(date: LocalDate):Option[LocalDate] = if (date>=endDate) Some(endDate) else if (date<=startDate) Some(startDate) else None

  def generateScheduleFromPoint(point:Int):List[LocalDate] = {
    val nextDate = dateForPoint(point)
    val finalDate = maybeTheEnd(nextDate)
    finalDate.map( _ :: Nil ).getOrElse( nextDate :: generateScheduleFromPoint(point+1) )
  }
}

case class ScheduleThang(startDate:LocalDate, endDate:LocalDate, frequency:Period) extends ScheduleGenerator