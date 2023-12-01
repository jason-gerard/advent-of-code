package Day14

import scala.annotation.tailrec

object Day14Part1 extends App {
    @tailrec
    def doStep(template: List[Char], pairs: Map[List[Char], List[Char]], currStep: Int): List[Char] = {
        if (currStep == 0) {
            template
        } else {
            val mappedPairs = template
                .sliding(2)
                .map(pair => pairs.getOrElse(pair, pair))
                .toList

            val filteredPairs = mappedPairs
                .init
                .flatMap(_.take(2))

            doStep(filteredPairs ++ mappedPairs.last, pairs, currStep - 1)
        }
    }

    val source = io.Source.fromFile("./src/main/scala/Day14/input.txt")
    val lines = try source.getLines().toList finally source.close()

    val template = lines.head.toCharArray.toList
    val pairs = lines.tail.flatMap {
        case s if s.contains("->") => {
            val parts = s.split("").map(_.toCharArray.apply(0))
            Some(List(parts(0), parts(1)) -> List(parts(0), parts(6), parts(1)))
        }
        case _ => None
    }.toMap

    println(template)
    println(pairs)

    val numSteps = 10
    val output = doStep(template, pairs, numSteps)
    println(output)

    val occurrences = output.groupBy(identity).transform((_, v) => v.size)
    val mostCommon = occurrences.maxBy(_._2)
    val leastCommon = occurrences.minBy(_._2)

    println(mostCommon)
    println(leastCommon)
    println(mostCommon._2 - leastCommon._2)
}
