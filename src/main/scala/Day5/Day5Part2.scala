package Day5

object Day5Part2 extends App {
    case class Point(x: Int, y: Int)

    val source = io.Source.fromFile("./src/main/scala/Day5/input.txt")
    val lines = try source.getLines().toList finally source.close()

    val points = lines
        .map(_
            .split("->")
            .toList
            .map(pair => {
                val splitPair = pair.trim.split(",")
                Point(splitPair(0).toInt, splitPair(1).toInt)
            }))
        .map(points => (points(0), points(1)))
        .flatMap {
            case (p1, p2) if p1.x == p2.x => {
                val range = ((p1.y min p2.y) to (p1.y max p2.y)).toList
                range.map(y => Point(p1.x, y))
            }
            case (p1, p2) if p1.y == p2.y => {
                val range = ((p1.x min p2.x) to (p1.x max p2.x)).toList
                range.map(x => Point(x, p1.y))
            }
            case (p1, p2) => {
                val m = (p2.y - p1.y) / (p2.x - p1.x)
                val b = p1.y - (m * p1.x)

                val range = ((p1.x min p2.x) to (p1.x max p2.x)).toList
                range.map(x => Point(x, (m * x) + b))
            }
        }
        .groupBy(identity)
        .transform((_, v) => v.size)

    val overlappingPoints = points.filter(_._2 >= 2)

    println(overlappingPoints.size)
}
