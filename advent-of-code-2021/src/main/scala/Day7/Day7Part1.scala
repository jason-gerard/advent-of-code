package Day7

object Day7Part1 extends App {
    val source = io.Source.fromFile("./src/main/scala/Day7/input.txt")
    val lines = try source.getLines().toList.head.split(",").toList.map(_.toInt) finally source.close()

    val middleIndex = ((lines.size-1)/2)
    val median = lines
        .sorted
        .apply(middleIndex)

    val fuelCost = lines
        .map(pos => (pos - median).abs)

    println(fuelCost.sum)
}
