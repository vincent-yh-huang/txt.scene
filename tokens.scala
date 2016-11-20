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

sealed class TValue
case class TxtString(str: String) extends TValue
case class TxtInt(num: Int) extends TValue

sealed class TType
case class TKeyword() extends TType
case class TWord() extends TType
case class TScene() extends TType

class TxtToken(t: TType, v: TValue, l: Int) {
	val tType: TType = t
	val tValue: TValue = v
	val tLine: Int	= l
};

/*	function tokenizer
 *	params:
 *		'input' - List<String> which holds every line from the input
 *					- use input.listIterator() to return list iterator
 *	output:
 *		returns a List<TxtToken>
 */
def tokenizer(input: Iterator[String]) :ListBuffer[TxtToken] = {
	var tokenList = ListBuffer[TxtToken]()
	tokenList
}