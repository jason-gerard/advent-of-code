package Day9

object Day9Part2 extends App {
    val source = io.Source.fromFile("./src/main/scala/Day9/input.txt")
    val lines = try source.getLines().toList finally source.close()

    val width = lines.head.length
    val heightmap = lines.toVector.flatten.map(_.asDigit)

    var s: Set[Int] = Set()
    val lowPoints = heightmap
        .zipWithIndex
        .map { case (point, index) => {
            val x = bfs(heightmap)(index, s)
            s = x._2
            x._1
        }
        }
        .filter(_.nonEmpty)
        .map(_.size)
        .sorted
        .reverse
        .take(3)
        .product

    println(lowPoints)
}

def bfs(heightmap: Vector[Int])(index: Int, visited: Set[Int]): (List[Int], Set[Int]) = {
    if (visited.contains(index)) {
        (List(), visited)
    } else {
        heightmap.lift(index) match {
            case Some(value) if value == 9 => {
                (List(), visited + index)
            }
            case Some(value) => {
                val up = bfs(heightmap)(index - 100, visited + index)
                val down = bfs(heightmap)(index + 100, up._2)
                val left = if ((index / 100) == ((index-1) / 100))bfs(heightmap)(index - 1, down._2) else (List(), down._2)
                val right = if ((index / 100) == ((index+1) / 100)) bfs(heightmap)(index + 1, left._2) else (List(), left._2)

                (index +: (up._1 ++ down._1 ++ left._1 ++ right._1), right._2)
            }
            case None => (List(), visited)
        }
    }
}
