package io.github.vyhhuang.txt_scene.token {
    class Token(t: TType, l: Int, i: Int) {
        val tType: TType = t        //token type
        val tLine: Int = l          //token line
        val tIndex: Int = i         //token index

        override def toString: String = {
            var rstr: String = "\t\n{ " + tType + ",\t"
            rstr += tLine + ",\t" + tIndex + " }"
            rstr
        }
    }
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
    case class TNLine(tVal: Null) extends TType {type T=Null;val s="""\n"""}
    case class TParen(tVal: String) extends TType {type T=String;val s="\""}
}
