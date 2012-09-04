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