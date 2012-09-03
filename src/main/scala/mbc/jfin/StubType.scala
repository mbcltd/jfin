package mbc.jfin

/**
 * Created with IntelliJ IDEA.
 * User: dmb
 * Date: 03/09/2012
 * Time: 06:53
 * To change this template use File | Settings | File Templates.
 */
sealed trait StubType {
  def name: String
}

case object NoStub extends StubType {
  val name = "NoStub"
}

case object ShortFirst extends StubType {
  val name = "ShortFirst"
}

case object ShortLast extends StubType {
  val name = "ShortLast"
}

case object LongFirst extends StubType {
  val name = "LongFirst"
}

case object LongLast extends StubType {
  val name = "LongLast"
}

object StubType {
  val all: Seq[StubType] = NoStub :: ShortFirst :: ShortLast :: LongFirst :: LongLast :: Nil
}
