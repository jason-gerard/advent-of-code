package Day11

import scala.annotation.tailrec

object Day11Part1 extends App {
    case class Coord(value: Int, row: Int, col: Int)

    def printGrid(grid: List[List[Coord]]): Unit = grid.foreach(line => println(line.map(_.value).mkString(", ")))

    @tailrec
    def flashGrid(grid: List[List[Coord]], visited: Set[Coord]): List[List[Coord]] = {
        def flash(grid: List[List[Coord]], visited: Set[Coord])(coord: Coord): (Coord, Set[Coord]) = {
            val points = getAdjacentPoints(coord.row, coord.col)
            val flashedPoints = points
                .flatMap(point => grid
                    .lift(point._1)
                    .flatMap(_.lift(point._2)))
                .filter(coord => coord.value > 9 && !visited.contains(coord))
            val inc = flashedPoints.size

            (coord.copy(value = coord.value+inc), visited ++ flashedPoints.toSet)
        }

        printGrid(grid)
        println(visited)
        println(isFlashReady(grid, visited))
        if (isFlashReady(grid, visited)) {
            val res = grid.map(_.map(flash(gridInc1, visited)))
            val flashedGrid = res.map(_.map(_._1))
            val resultingVisited = res.flatMap(_.flatMap(_._2)).toSet

            flashGrid(flashedGrid, resultingVisited)
        } else {
            grid
        }
    }

    def getAdjacentPoints(row: Int, col: Int): List[(Int, Int)] = List(
        (row+1, col), (row-1, col), (row, col+1), (row, col-1), (row+1, col+1), (row-1, col-1), (row+1, col-1), (row-1, col+1))

    def isFlashReady(grid: List[List[Coord]], visited: Set[Coord]): Boolean =
        !grid.forall(_.forall(coord => coord.value <= 9 || visited.contains(coord)))

    val source = io.Source.fromFile("./src/main/scala/Day11/input-test.txt")
    val lines = try source.getLines().toList finally source.close()

    val initialGrid = lines.map(_.toList.map(_.asDigit))
    val gridWithIndex = initialGrid
        .zipWithIndex
        .map {
            case (points, row) => points.zipWithIndex.map {
                case (point, column) => Coord(point, row, column) } }

    val gridInc1 = gridWithIndex.map(_.map(coord => coord.copy(value = coord.value+1)))

    val flashesGrid = flashGrid(gridInc1, Set[Coord]())
    printGrid(flashesGrid)

    val resetGrid = flashesGrid
        .map(_
            .map(coord => coord.copy(value = coord.value.abs))
            .map {
                case coord if coord.value > 9 => coord.copy(value = 0)
                case coord => coord
            })

    printGrid(resetGrid)
}
