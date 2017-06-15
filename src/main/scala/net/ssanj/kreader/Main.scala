package net.ssanj
package kreader

import cats.Id
import cats.data.{Kleisli, Reader, ReaderT}
import net.ssanj.model._

object Main {

  def main(args: Array[String]): Unit = {
    readerAsReaderT()
    readerOfSameEnv()
    readerOfSameEnvAp2()
    readerOfSameEnvMap2()
  }

  def readerOfSameEnvAp2(): Unit = {
    import KleisliReader._
    import cats.Apply
    import cats.implicits._

    type KOptionConfig[A] = Kleisli[Option, Config, A]
    type PersonFunc = (Name, Age) => Person

    val config = Config("mr peanutbutter", 30)
    val readNameKOC: KOptionConfig[Name] = readNameK
    val readAgeKOC: KOptionConfig[Age] = readAgeK
    val personKOC: KOptionConfig[PersonFunc] = Kleisli( (_: Config) => Option(Person(_, _)))

    val personK = Apply[KOptionConfig].ap2(personKOC)(readNameKOC, readAgeKOC)
    val result = personK(config)
    println(result)
  }

  def readerOfSameEnvMap2(): Unit = {
    import KleisliReader._
    import cats.Apply
    import cats.implicits._

    type KOptionConfig[A] = Kleisli[Option, Config, A]

    val config = Config("Diane Nguyen", 27)
    val readNameKOC: KOptionConfig[Name] = readNameK
    val readAgeKOC: KOptionConfig[Age] = readAgeK

    val personK = Apply[KOptionConfig].map2(readNameKOC, readAgeKOC) { Person(_, _) }
    val result = personK(config)
    println(result)
  }

  def readerOfSameEnv(): Unit = {
    import KleisliReader._
    import cats.implicits._

    val personK: Kleisli[Option, Config, Person] =
      for {
        name <- readNameK
        age  <- readAgeK
      } yield Person(name, age)

    val result1 = personK(Config("Bojack Horseman", 42))
    println(result1)

    val result2 = personK(Config("Jake", 20))
    println(result2)

    val result3 = personK(Config("Fred Flintstone", 50000))
    println(result3)
  }

  def readerAsReaderT(): Unit = {
    type MyReader   = ReaderT[Id, Int, String]
    val MyReader    = Reader.apply[Int, String](_)
    val r: MyReader = MyReader((n: Int) => s"you gave me: $n")
    val result = r.run(10)
    println(result)
  }

}