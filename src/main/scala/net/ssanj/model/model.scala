package net.ssanj.model

final case class Name(first: String, last: String)

final case class Age(age: Int)

final case class Person(name: Name, age: Age)

case class Config(name: String, age: Int)
