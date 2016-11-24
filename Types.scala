sealed class TType()
case class TNum() extends TType
case class TKeyword() extends TType
case class TWord() extends TType
case class TScene() extends TType
case class TNewLine() extends TType
case class TParen() extends TType
case class BadToken() extends TType

class TToken(t: TType, v: String, l: Int, i: Int) {
    val tType: TType = t        //token type
    val tValue: String = v      //token value
    val tLine: Int = l          //token line
    val tIndex: Int = i         //token index

    override def toString: String = {
        var rstr: String = "\t\n{ " + tType + ",\t"
        rstr += tValue + ",\t" + tLine
        rstr += ",\t" + tIndex + " }"
        rstr
    }
};