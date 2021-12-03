package Day2

object Day2Part2 extends App {
    val source = io.Source.fromFile("./src/main/scala/Day2/input.txt")
    val lines = try source.getLines().toList finally source.close()

    val directions = lines.zipWithIndex.map((line) => (line._1.split(" "), line._2) match {
        case (Array(direction, magnitude), index) => (index, direction, magnitude.toInt)
    })

    val forwards = directions
        .filter(_._2 == "forward")

    val aims = forwards
        .map(f => directions.filter {
            case (index, "up" | "down", _) => index < f._1
            case (_, _, _) => false
        }.map {
            case (_, "up", mag) => -mag
            case (_, "down", mag) => mag
        }.sum)


    val pairs = forwards.map(_._3) zip aims

    val horizontal = pairs.map(_._1).sum
    val depth = pairs.map(p => p._1 * p._2).sum

    print(horizontal * depth)
}
