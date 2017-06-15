package net.ssanj
package klocal

import cats.data.Kleisli
import net.ssanj.model._

object Main {
  def main(args: Array[String]): Unit = {
    readerOfDiffEnv()
    readerOfDifEnvMap2()
  }

  def readerOfDiffEnv(): Unit = {
    import KleisliLocal._
    import cats.implicits._

    val personK: Kleisli[Option, Config, Person] =
      for {
        name <- readNameK.local[Config](_.name)
        age  <- readAgeK.local[Config](_.age)
      } yield Person(name, age)

    val result1 = personK(Config("Bojack Horseman", 42))
    println(result1)

    val result2 = personK(Config("Jake", 20))
    println(result2)

    val result3 = personK(Config("Fred Flintstone", 50000))
    println(result3)
  }

  def readerOfDifEnvMap2(): Unit = {
    import KleisliLocal._
    import cats.Apply
    import cats.implicits._

    type KOptionConfig[A] = Kleisli[Option, Config, A]

    val config = Config("Diane Nguyen", 27)
    val readNameKOC: KOptionConfig[Name] = readNameK.local[Config](_.name)
    val readAgeKOC: KOptionConfig[Age] = readAgeK.local[Config](_.age)

    val personK = Apply[KOptionConfig].map2(readNameKOC, readAgeKOC) { Person(_, _) }
    val result = personK(config)
    println(result)
  }
}