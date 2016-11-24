/* Current goals:
 *	- implement token structure: !!DONE
 *		- type: token type
 *		- value: token value 
 *		- line: line number
 *	- recognize (or in this case, ignore) single-line comments "//"
 *	- recognize keyword notation "![name]"
 *		- 'val', 'var', and 'include' are particularly important
 *	- recognize word recognization "Lorem ipsum." => !!DONE
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

def test(regex: Regex, char: Char): Boolean = (regex findFirstIn char.toString).nonEmpty

def longToken(rRight: Regex, rLeft: Regex = "".r, line: String, 
	position: Int, lNum: Int, index: Int): (TToken, Int) = 
{
	var value = ""
	var pos = position
	var char = line(pos)

	while (pos < line.length && test(rRight, line(pos))) {
			println("\tchar: "+char)
			println("\tpos: "+pos)
		char = line(pos)
		//checks to see if number is properly defined
		if (test(rLeft, char)) {
			//var errMsg = ("SYNTAX ERROR: ")
			//errMsg += (f"\t'$value$char' @ $lNum:$index")
			println("Error! Returning...")
			return (new TToken(BadToken(), "", -1, -1), pos)
		}
		value += char
		pos += 1
	}
	(new TToken(TNum(), value, lNum, index), pos)
}


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
	val rParens = "[()]"
	val rWhitespace = """[^\S^\n]"""
	val rNum = "[0-9]"
	val rWord = """[a-zA-Z\.\,\:\;\!\?\"\'\-]"""

	for( line <- input) {
		var position = 0
		var index = position
		while (position < line.length) {
			var char = line(position)
				//println("\tchar: " +char)
				//println("\tposition: "+position)
			index = position
			//TParens check
			if (test(rParens.r, char)) {
					//println("matches parens")
				var value = char.toString()
				tokenList += new TToken(TParen(), value, lNum, index)
				position += 1
			//Whitespace check
			} else if (test(rWhitespace.r, char)) {
					//println("matches whitespace")
				position += 1
			//TNum() check
			} else if (test(rNum.r, char)) {
				/*
				var result =
					longToken(rNum.r, rWord.r, line, 
						position, lNum, index) match {
					case Left(Failure(str)) => Console.err.println(str)
					case Right((tToken, pos)) => (tToken, pos)
				}
				if (result == (TToken, Int)) {
					tokenList += result._1
					position = result._2
				} else {
					return Iterator()
				}
				*/
				///*
					//println("matches num")
				var value = ""
				while (position < line.length && test(rNum.r, line(position))) {
						//println("\tchar: "+char)
						//println("\tpos: "+position)
					char = line(position)
					//checks to see if number is properly defined
					if (test(rWord.r, char)) {
						Console.err.println("SYNTAX ERROR: ")
						Console.err.println(f"\t'$value$char' @ $lNum:$index")
						return ListBuffer[TToken]()
					}
					value += char
					position += 1
				}
				tokenList += new TToken(TNum(), value, lNum, index)
				//*/
				/*
					println("matches num")
				var result = longToken(rNum.r, rWord.r, line, 
					position, lNum, index)
				if (result._1.tType == BadToken()) {
					println(result._1.tValue)
					return ListBuffer()
				} else {
					tokenList += result._1
					position = result._2
				}
				*/
			//TWord() check
			} else if (test(rWord.r, char)) {
				var value = ""
				while (position < line.length && test((rWord+rNum).r, line(position))) {
					//println(value)
					char = line(position)
					value += char
					position += 1
				}
				tokenList += new TToken(TWord(), value, lNum, index)
			}
		}
		index += 1
		tokenList += new TToken(TNewLine(),"",lNum, index)
		lNum += 1
	}
	tokenList
}