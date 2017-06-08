package net.ssanj.kc

// import cats.data.Kleisli

// val k1 = Kleisli((s: String) => if (s.trim.isEmpty) None else Option(s))
// val k2 = Kleisli((s: String) => Option(s.length))
// val k3 = Kleisli((n: Int) => if (n % 2 == 0) Option(true) else Option(false))

// val k4 = k3 compose k2 compose k1
// k4.run("testing")


object KleisliComposition {

  def stringToNonEmptyString: String => Option[String] = value =>
    if (value.nonEmpty) Option(value) else None

  def stringToNumber: String => Option[Int] = value =>
    if (value.matches("-?[0-9]+")) Option(value.toInt) else None

  def numberToAge: Int => Option[Age] = num =>
    if (num >= 1 && num <= 150) Option(Age(num)) else None
}
