package io.github.vyhhuang.txt_scene.parser

sealed abstract class TType() {
  type T
  val tVal: T
  val s: String
  override def toString = s + tVal
}
case class TNum(tVal: Int) extends TType {type T=Int;val s="#"}
case class TKWord(tVal: String) extends TType {type T=String;val s="!"}
case class TWord(tVal: String) extends TType {type T=String;val s="\""}
case class TScene(tVal: String) extends TType {type T=String;val s="##"}
case class TParen(tVal: String) extends TType {type T=String;val s="\""}
case class TNLine(tVal: String="\\n") extends TType {type T=String;val s=""}