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

class UniformScheduleSpec extends SpecificationWithJUnit with DataTables with TheTest {

  "An annual schedule with no stub from 2006-3-28 to 2011-3-28" should {
    val schedule = ScheduleGenerator.generateSchedule(new LocalDate(2006, 3, 28), new LocalDate(2011, 3, 28), years(1), NoStub)

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
    val schedule = ScheduleGenerator.generateSchedule(new LocalDate(2006, 3, 28), new LocalDate(2007, 3, 28), months(3), NoStub)

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
    val schedule = ScheduleGenerator.generateSchedule(new LocalDate(2006, 3, 31), new LocalDate(2007, 3, 31), months(3), NoStub)

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
      ScheduleGenerator.generateSchedule(new LocalDate(2006, 3, 28), new LocalDate(2011, 2, 28), years(1), NoStub) must throwA[AssertionError]
    }
  }
}


class ShortLastStubScheduleSpec extends SpecificationWithJUnit with DataTables with TheTest {
  "An annual schedule with a short last stub from 2006-3-28 to 2011-4-28" should {
    val schedule = ScheduleGenerator.generateSchedule(new LocalDate(2006, 3, 28), new LocalDate(2011, 4, 28), years(1), ShortLast)

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

//    "the stub period should have a reference start 2011-3-28 and end 2012-3-28" in {
//      schedule(5).reference.start must be equalTo (new LocalDate(2011, 3, 28))
//      schedule(5).reference.end must be equalTo (new LocalDate(2012, 3, 28))
//    }
  }
}

class LongLastStubScheduleSpec extends SpecificationWithJUnit with DataTables with TheTest {
  "An annual schedule with a long last stub from 2006-3-28 to 2011-4-28" should {
    val schedule = ScheduleGenerator.generateSchedule(new LocalDate(2006, 3, 28), new LocalDate(2011, 4, 28), years(1), LongLast)

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

//    "the stub period should have a reference start 2010-3-28 and end 2011-3-28" in {
//      schedule(4).reference.start must be equalTo (new LocalDate(2010, 3, 28))
//      schedule(4).reference.end must be equalTo (new LocalDate(2011, 3, 28))
//    }
  }
}

class ShortFirstStubScheduleSpec extends SpecificationWithJUnit with DataTables with TheTest {
  "An annual schedule with a short first stub from 2006-3-28 to 2011-4-28" should {
    val schedule = ScheduleGenerator.generateSchedule(new LocalDate(2006, 3, 28), new LocalDate(2011, 4, 28), years(1), ShortFirst)

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

//    "the stub period should have a reference start 2005-4-28 and end 2006-4-28" in {
//      schedule(0).reference.start must be equalTo (new LocalDate(2005, 4, 28))
//      schedule(0).reference.end must be equalTo (new LocalDate(2006, 4, 28))
//    }
  }
}

class LongFirstStubScheduleSpec extends SpecificationWithJUnit with DataTables with TheTest {
  "An annual schedule with a long first stub from 2006-3-28 to 2011-4-28" should {
    val schedule = ScheduleGenerator.generateSchedule(new LocalDate(2006, 3, 28), new LocalDate(2011, 4, 28), years(1), LongFirst)

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

//    "the stub period should have a reference start 2006-4-28 and end 2007-4-28" in {
//      schedule(0).reference.start must be equalTo (new LocalDate(2006, 4, 28))
//      schedule(0).reference.end must be equalTo (new LocalDate(2007, 4, 28))
//    }
  }
}