package net.ssanj.klocal

import cats.data.Kleisli
import net.ssanj.model._

object KleisliLocal {

  def readName: String => Option[Name] = name => {
    val parts = name.split(" ")
    if (parts.length > 1) Option(Name(parts(0), parts.drop(1).mkString(" "))) else None
  }

  def readAge: Int => Option[Age] = age => {
    if (age >= 1 && age <= 150) Option(Age(age)) else None
  }

  def readNameK: Kleisli[Option, String, Name] = Kleisli(readName)

  def readAgeK: Kleisli[Option, Int, Age] = Kleisli(readAge)
}