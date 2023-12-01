package Day8

object Day8Part1 extends App {
    val lengthToWordMap = Map(
        2 -> 1,
        4 -> 4,
        3 -> 7,
        7 -> 8,
    )

    val source = io.Source.fromFile("./src/main/scala/Day8/input.txt")
    val lines = try source.getLines().toList finally source.close()
    val pairs = lines.map(lineToTuple)

    val occurrences = pairs
        .flatMap(pair => pair
            ._2
            .map(word => lengthToWordMap.getOrElse(word.length, -1)))
        .groupBy(identity)
        .transform((_, v) => v.size)

    val validOccurrences = occurrences - (-1)
    val numValidOccurrences = validOccurrences.foldLeft(0)((acc, pair) => acc + pair._2)

    println(validOccurrences)
    println(numValidOccurrences)

    def lineToTuple(line: String): (List[String], List[String]) = {
        val split = line.split("\\|")
        val separateWords = (line: String) => line.trim.split(" ").map(_.trim).toList
        (separateWords(split(0)), separateWords(split(1)))
    }
}
