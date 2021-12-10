package Day9

object Day9Part1 extends App {
    val source = io.Source.fromFile("./src/main/scala/Day9/input.txt")
    val lines = try source.getLines().toList finally source.close()

    val width = lines.head.length
    val heightmap = lines.toVector.flatten.map(_.asDigit)

    val lowPoints = heightmap
        .zipWithIndex
        .map { case (point, index) => if (isLowestPoint(index, width, isLessThan(point, heightmap))) point else -1 }
        .filter(_ != -1)

    val riskLevel = lowPoints.sum + lowPoints.size
    println(riskLevel)
}

def isLowestPoint(index: Int, width: Int, lessThanPoint: Int => Boolean): Boolean =
    lessThanPoint(getLeft(index))
    && lessThanPoint(getRight(index))
    && lessThanPoint(getUp(index, width))
    && lessThanPoint(getDown(index, width))

def isLessThan(point: Int, heightmap: Vector[Int])(index: Int): Boolean = {
    heightmap.lift(index) match {
        case Some(value) => point < value
        case None => true
    }
}

val getLeft = (index: Int) => index - 1
val getRight = (index: Int) => index + 1
val getUp = (index: Int, width: Int) => index - width
val getDown = (index: Int, width: Int) => index + width
