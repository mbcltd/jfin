package mbc.jfin

/**
 * Created with IntelliJ IDEA.
 * User: dmb
 * Date: 30/08/2012
 * Time: 19:25
 * To change this template use File | Settings | File Templates.
 */
sealed trait BusinessDayConvention {
  def name: String
}

case object Unadjusted extends BusinessDayConvention {
  val name = "Unadjusted"
}

case object Following extends BusinessDayConvention {
  val name = "Following"
}

case object ModifiedFollowing extends BusinessDayConvention {
  val name = "ModifiedFollowing"
}

case object ModifiedFollowingEndOfMonth extends BusinessDayConvention {
  val name = "ModifiedFollowingEndOfMonth"
}

case object Preceding extends BusinessDayConvention {
  val name = "Preceding"
}

case object ModifiedPreceding extends BusinessDayConvention {
  val name = "ModifiedPreceding"
}

object BusinessDayConvention {
  val all = Unadjusted :: Following :: ModifiedFollowing :: Preceding :: ModifiedPreceding :: Nil
}