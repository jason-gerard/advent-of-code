package Day7

object Day7Part2 extends App {
    val source = io.Source.fromFile("./src/main/scala/Day7/input.txt")
    val lines = try source.getLines().toList.head.split(",").toList.map(_.toInt) finally source.close()

    // O(max(n) * n)
    val uniquePositions = (0 to lines.max).toSet
    val fuelCosts = uniquePositions
        .map(newPos => lines
            .map(pos => {
                val cost = (pos - newPos).abs
                (0 to cost).sum
            }))

    println(fuelCosts.map(_.sum).min)
}
