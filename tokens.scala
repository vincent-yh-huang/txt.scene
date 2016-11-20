/* Current goals:
 *	- implement token structure:
 *		- type: token type
 *		- value: token value 
 *			(will _always_ be a string b/c Java typing)
 *		- line: line number
 *	- recognize (or in this case, ignore) single-line comments "//"
 *	- recognize keyword notation "![name]"
 *		- 'val', 'var', and 'include' are particularly important
 *	- recognize word recognization "Lorem ipsum." => 
 *		"[	{token1.type = "word", token1.value = "Lorem"}
 *		 ,	{token2.type = "word", token2.value = "ipsum"}
 *		 ]"
 *	- recognize scene notation "##"
 * 
 * Next goals:
 *	- recognize mapping operator "<-"
 *	- recognize replace notation "_"
 *	- recognize speaker notation "@" 
 *		-basic format: @[name] (parenthetical) [dialogue]
 *	- recognize line continuation operator "..."
 *
 *	Remember: 
 *		- newline characters are the only differentiator between lines/commands
 *			- all other whitespace is ignored.
 */


import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex

sealed class TType()
case class TNum() extends TType
case class TKeyword() extends TType
case class TWord() extends TType
case class TScene() extends TType
case class TNewLine() extends TType
case class TParen() extends TType

def test(regex: Regex, char: Char): Boolean = (regex findFirstIn char.toString).nonEmpty

class TToken(t: TType, v: String, l: Int, i: Int) {
	val tType: TType = t		//token type
	val tValue: String = v		//token value
	val tLine: Int = l			//token line
	val tIndex: Int = i			//token index

	override def toString: String = {
		var rstr: String = "\t\n{ " + tType + ",\t"
		rstr += tValue + ",\t" + tLine
		rstr += ",\t" + tIndex + " }"
		rstr
	}
};

/*	function tokenizer
 *	params:
 *		'input' - Iterator[String] which holds every line from the input
 *	output:
 *		returns a ListBuffer[TxtToken]
 */
def tokenizer(input: Iterator[String]) :ListBuffer[TToken] = {
	var tokenList = ListBuffer[TToken]()
	var lNum = 0
	//regex definitions
	val rParens = "[()]".r
	val rWhitespace = """[^\S^\n]""".r
	val rNum = "[0-9]".r
	val rWord = "[a-zA-Z]".r

	for( line <- input) {
		var position = 0
		var index = position
		while (position < line.length) {
			var char = line(position)
				//println("\tchar: " +char)
				//println(position)
			index = position
			//TParens check
			if (test(rParens, char)) {
					//println("matches parens")
				var value = char.toString()
				tokenList += new TToken(TParen(), value, lNum, index)
				position += 1
			//Whitespace check
			} else if (test(rWhitespace, char)) {
					//println("matches whitespace")
				position += 1
			//TNum() check
			} else if (test(rNum, char)) {
					//println("matches num")
				var value = ""
				while (position < line.length && test("[0-9]".r, char)) {
						//println("\tchar: "+char)
						//println("\tpos: "+position)
					char = line(position)
					if (test(rWord, char)) {
						Console.err.println(f"Error at $value, line $lNum, index $index")
						return ListBuffer[TToken]()
					}
					value += char
					position += 1
				}
				tokenList += new TToken(TNum(), value, lNum, index)
			} else if (test(rWord, char)) {

			}
		}
		index += 1
		tokenList += new TToken(TNewLine(),"",lNum, index)
		lNum += 1
	}
	tokenList
}