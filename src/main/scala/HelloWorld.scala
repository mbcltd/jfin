import mbc.jfin.ScheduleGenerator
import scalaz.Digit._0

/**
 * Created with IntelliJ IDEA.
 * User: dmb
 * Date: 30/08/2012
 * Time: 14:56
 * To change this template use File | Settings | File Templates.
 */
import org.scala_tools.time.Imports._
import mbc.jfin._
import org.scala_tools.time.Imports.Period._

object HelloWorld extends App {


  val scheduleGenerator = new ScheduleDefinition(new LocalDate(2006, 3, 28), new LocalDate(2011, 2, 28), years(1)) with LastStub with LongStub

  val schedule = ScheduleGenerator.generateSchedule(new LocalDate(2006, 3, 28), new LocalDate(2011, 2, 28), years(1), NoStub)

  schedule.foreach(println(_))


}
