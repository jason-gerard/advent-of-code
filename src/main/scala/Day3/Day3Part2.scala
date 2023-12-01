package Day3

import java.util
import scala.annotation.tailrec

object Day3Part2 extends App {
    val source = io.Source.fromFile("./src/main/scala/Day3/input.txt")
    val lines = try source.getLines().toList.map(_.toList) finally source.close()
    val oxygenRate = Integer.parseInt(getCommon(mostCommonN)("", lines, 0), 2)
    val co2Rate = Integer.parseInt(getCommon(leastCommonN)("", lines, 0), 2)

    def mostCommonN(input: List[List[Char]], index: Int): Char = input
        .transpose
        .apply(index)
        .groupBy(identity)
        .mapValues(_.size)
        .reduce(maxByDefault('1'))
        ._1

    def maxByDefault(default: Char)(a: (Char, Int), b: (Char, Int)): (Char, Int) = {
        if (a._2 == b._2) {
            (default, a._2)
        } else {
            if (a._2 > b._2) a else b
        }
    }

    def leastCommonN(input: List[List[Char]], index: Int): Char = input
        .transpose
        .apply(index)
        .groupBy(identity)
        .mapValues(_.size)
        .reduce(minByDefault('0'))
        ._1

    def minByDefault(default: Char)(a: (Char, Int), b: (Char, Int)): (Char, Int) = {
        if (a._2 == b._2) {
            (default, a._2)
        } else {
            if (a._2 < b._2) a else b
        }
    }

    def remainingNLines(input: List[List[Char]], index: Int, filterChar: Char): List[List[Char]] = input.filter(_ (index) == filterChar)

    @tailrec
    def getCommon(commonF: (List[List[Char]], Int) => Char)(currVal: String, input: List[List[Char]], index: Int): String = {
        if (index == input(0).length) {
            return currVal
        }

        val commonVal = commonF(input, index)
        val remaningLine = remainingNLines(input, index, commonVal)
        getCommon(commonF)(currVal + commonVal, remaningLine, index + 1)
    }

    println(oxygenRate * co2Rate)
}
