package io.github.vyhhuang.txt_scene.parser

class Token(t: TType, l: Int, i: Int) {
  val tType: TType = t //token type
  val tLine: Int = l //token line
  val tIndex: Int = i //token index

  override def toString: String = {
    var rstr: String = "\t\n{ " + tType + ",\t"
    rstr += tLine + ",\t" + tIndex + " }"
    rstr
  }
}