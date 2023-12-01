package Day10

import scala.annotation.tailrec

object Day10Part2 extends App {
    val openingChars = Set('(', '[', '{', '<')
    val closingChars = Set(')', ']', '}', '>')
    val mapClosingToOpeningChars = Map(
        ')' -> '(',
        ']' -> '[',
        '}' -> '{',
        '>' -> '<',
    )
    val mapOpeningToClosingChars = Map(
        '(' -> ')',
        '[' -> ']',
        '{' -> '}',
        '<' -> '>',
    )
    val charsToPoints = Map(
        ')' -> 1,
        ']' -> 2,
        '}' -> 3,
        '>' -> 4,
    )

    @tailrec
    def isLineCorrupted(stack: List[Char])(chars: List[Char]): Boolean =
        chars.headOption match {
            case Some(currentChar) if openingChars.contains(currentChar) => isLineCorrupted(currentChar +: stack)(chars.tail)
            case Some(currentChar) if closingChars.contains(currentChar) => {
                if (mapClosingToOpeningChars(currentChar) == stack.head) {
                    isLineCorrupted(stack.tail)(chars.tail)
                } else {
                    true
                }
            }
            case Some(_) | None => false
        }

    @tailrec
    def findRemainingChars(stack: List[Char])(chars: List[Char]): List[Char] =
        chars.headOption match {
            case Some(currentChar) if openingChars.contains(currentChar) => findRemainingChars(currentChar +: stack)(chars.tail)
            case Some(currentChar) if closingChars.contains(currentChar) => findRemainingChars(stack.tail)(chars.tail)
            case Some(_) | None => stack
        }

    val source = io.Source.fromFile("./src/main/scala/Day10/input.txt")
    val lines = try source.getLines().toList finally source.close()

    val characterList = lines.map(_.toCharArray.toList)
    val corruptedLines = characterList.map(isLineCorrupted(List[Char]()))

    val incompleteLines = (characterList zip corruptedLines).filter(!_._2).map(_._1)
    println(incompleteLines)

    val remainingChars = incompleteLines.map(findRemainingChars(List[Char]())(_)
        .map(mapOpeningToClosingChars))
    println(remainingChars)

    val points = remainingChars
        .map(_.foldLeft(BigInt(0))((total, char) => (total * 5) + charsToPoints(char)))
        .sorted
    println(points)

    val mid = (points.size - 1) / 2
    println(points(mid))
}
