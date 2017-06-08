package net.ssanj.kc

object KleisliComposition {

  def stringToNonEmptyString: String => Option[String] = value =>
    if (value.nonEmpty) Option(value) else None

  def stringToNumber: String => Option[Int] = value =>
    if (value.matches("-?[0-9]+")) Option(value.toInt) else None

  def numberToAge: Int => Option[Age] = num =>
    if (num >= 1 && num <= 150) Option(Age(num)) else None
}
