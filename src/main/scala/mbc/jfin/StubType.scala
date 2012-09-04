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
