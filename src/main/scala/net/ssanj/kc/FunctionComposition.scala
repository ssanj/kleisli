package net.ssanj.kc

object FunctionComposition {

  def mul2: Int => Int = _ * 2

  def power2: Int => Double = Math.pow(2, _)

  def doubleToInt: Double => Int = _.toInt

  def intToString: Int => String = _.toString
}
