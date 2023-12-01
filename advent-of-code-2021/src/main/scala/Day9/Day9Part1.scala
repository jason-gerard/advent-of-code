package Day9

object Day9Part1 extends App {
    val source = io.Source.fromFile("./src/main/scala/Day9/input.txt")
    val lines = try source.getLines().toList finally source.close()

    val width = lines.head.length
    val heightmap = lines.toVector.flatten.map(_.asDigit)

    val lowPoints = heightmap
        .zipWithIndex
        .map { case (point, index) =>
            val directions = List(index - 1, index + 1, index - width, index + width)
            val isLowest = directions.forall(isLessThan(point, heightmap))
            if (isLowest) point else -1
        }
        .filter(_ != -1)

    val riskLevel = lowPoints.sum + lowPoints.size
    println(riskLevel)
}

def isLessThan(point: Int, heightmap: Vector[Int])(index: Int): Boolean = {
    heightmap.lift(index) match {
        case Some(value) => point < value
        case None => true
    }
}
