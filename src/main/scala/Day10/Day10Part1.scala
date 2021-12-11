package Day10

import scala.annotation.tailrec

object Day10Part1 extends App {
    @tailrec
    def findFirstIncorrectChar(stack: List[Char])(chars: List[Char]): Option[Char] =
        chars.headOption match {
            case Some(currentChar) if openingChars.contains(currentChar) => findFirstIncorrectChar(currentChar +: stack)(chars.tail)
            case Some(currentChar) if closingChars.contains(currentChar) => {
                if (mapClosingToOpeningChars(currentChar) == stack.head) {
                    findFirstIncorrectChar(stack.tail)(chars.tail)
                } else {
                    Some(currentChar)
                }
            }
            case Some(_) | None => None
        }

    val source = io.Source.fromFile("./src/main/scala/Day10/input.txt")
    val lines = try source.getLines().toList finally source.close()

    val characterList = lines.map(_.toCharArray.toList)
    println(characterList)

    val incorrectChars = characterList.flatMap(findFirstIncorrectChar(List[Char]()))
    println(incorrectChars)

    val points = incorrectChars
        .map(mapCharsToPoints)
        .sum
    println(points)
}

val openingChars = Set('(', '[', '{', '<')
val closingChars = Set(')', ']', '}', '>')
val mapClosingToOpeningChars = Map(
    ')' -> '(',
    ']' -> '[',
    '}' -> '{',
    '>' -> '<',
)
val mapCharsToPoints = Map(
    ')' -> 3,
    ']' -> 57,
    '}' -> 1197,
    '>' -> 25137,
)
