package net.ssanj.kc

object Main {

  def main(args: Array[String]) {
    composeFunctions()
    composeKleisliFunctions()
    monadicBind()
    monadicBind2()
  }

  private def composeFunctions(): Unit = {
    import FunctionComposition._

    val pipeline: Int => String = intToString compose mul2 compose doubleToInt compose power2

    val result = pipeline(3)
    println(s"result of function composition: $result")
  }

  // private def composeFunctionsInContext(): Unit = {
  //   import KleisliComposition._
       //won't compile as the types don't align
  //   val pipeline: String => Option[Int] = stringToNumber compose stringToNonEmptyString
  //   val result = pipeline("1000")
  //   println(s"result of function composition: $result")
  // }

  private def composeKleisliFunctions(): Unit = {
    import KleisliComposition._
    import cats.data.Kleisli
    import cats.implicits._

    val stringToNonEmptyStringK: Kleisli[Option, String, String] = Kleisli(stringToNonEmptyString)
    val stringToNumberK: Kleisli[Option, String, Int] = Kleisli(stringToNumber)

    val pipeline: Kleisli[Option, String, Int] = stringToNumberK compose stringToNonEmptyStringK
    val result1 = pipeline("1000")
    val result2 = pipeline("")
    val result3 = pipeline("A12B")
    println(s"result of Kleisli composition for number: $result1")
    println(s"result of Kleisli composition for empty String: $result2")
    println(s"result of Kleisli composition for alphanumeric: $result3")
  }

  private def monadicBind(): Unit = {
    import KleisliComposition._
    import cats.implicits._

    val pipeline: String => Option[Int] = Option(_) >>= stringToNonEmptyString >>= stringToNumber
    val result1 = pipeline("1000")
    val result2 = pipeline("")
    val result3 = pipeline("A12B")
    println(s"result of Monadic bind for number: $result1")
    println(s"result of Monadic bind for empty String: $result2")
    println(s"result of Monadic bind for alphanumeric: $result3")
  }

  private def monadicBind2(): Unit = {
    import KleisliComposition._
    import cats.implicits._

    val result1: Option[Int] = Option("1000") >>= stringToNonEmptyString >>= stringToNumber
    val result2: Option[Int] = Option("") >>= stringToNonEmptyString >>= stringToNumber
    val result3: Option[Int] = Option("A12B") >>= stringToNonEmptyString >>= stringToNumber

    println(s"result of Monadic bind2 for number: $result1")
    println(s"result of Monadic bind2 for empty String: $result2")
    println(s"result of Monadic bind2 for alphanumeric: $result3")
  }
}

