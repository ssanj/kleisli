package net.ssanj.kreader

import cats.data.Kleisli
import net.ssanj.model._

object KleisliReader {

  def readName: Config => Option[Name] = c => {
    val parts = c.name.split(" ")
    if (parts.length > 1) Option(Name(parts(0), parts.drop(1).mkString(" "))) else None
  }

  def readAge: Config => Option[Age] = c => {
    val age = c.age
    if (age >= 1 && age <= 150) Option(Age(age)) else None
  }

  def readNameK = Kleisli(readName)

  def readAgeK = Kleisli(readAge)
}