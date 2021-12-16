package Day11

import scala.annotation.tailrec

object Day11Part1 extends App {
    case class Point(row: Int, col: Int)
    case class Coord(value: Int, p: Point)

    def printGrid(grid: List[List[Coord]]): Unit = {
        grid.foreach(line => println(line.map(_.value).mkString))
        println()
    }

    @tailrec
    def flashGrid(grid: List[List[Coord]], visited: Set[Point], numFlashes: Int): (List[List[Coord]], Int) = {
        def flash(grid: List[List[Coord]], visited: Set[Point])(coord: Coord): (Coord, Set[Point]) = {
            val points = getAdjacentPoints(coord.p.row, coord.p.col)
            val flashedPoints = points
                .flatMap(point => grid
                    .lift(point._1)
                    .flatMap(_.lift(point._2)))
                .filter(coord => coord.value > 9 && !visited.contains(coord.p))
                .map(_.p)
            val inc = flashedPoints.size

            (coord.copy(value = coord.value+inc), visited ++ flashedPoints.toSet)
        }

        if (isFlashReady(grid, visited)) {
            val res = grid.map(_.map(flash(grid, visited)))
            val flashedGrid = res.map(_.map(_._1))
            val resultingVisited = res.flatMap(_.flatMap(_._2)).toSet

            flashGrid(flashedGrid, resultingVisited, numFlashes+getNumFlashes(grid, visited))
        } else {
            (grid, numFlashes)
        }
    }

    def getAdjacentPoints(row: Int, col: Int): List[(Int, Int)] = List(
        (row+1, col), (row-1, col), (row, col+1), (row, col-1), (row+1, col+1), (row-1, col-1), (row+1, col-1), (row-1, col+1))

    def isFlashReady(grid: List[List[Coord]], visited: Set[Point]): Boolean =
        !grid.forall(_.forall(coord => coord.value <= 9 || visited.contains(coord.p)))

    def getNumFlashes(grid: List[List[Coord]], visited: Set[Point]): Int =
        grid.flatten.count(coord => !(coord.value <= 9 || visited.contains(coord.p)))

    @tailrec
    def step(grid: List[List[Coord]], numSteps: Int, numFlashes: Int): (List[List[Coord]], Int) = {
        if (numSteps == 0) {
            (grid, numFlashes)
        } else {
            val gridInc1 = grid.map(_.map(coord => coord.copy(value = coord.value+1)))
            val flashedGrid = flashGrid(gridInc1, Set[Point](), 0)
            val resetGrid = flashedGrid._1
                .map(_
                    .map(coord => coord.copy(value = coord.value.abs))
                    .map {
                        case coord if coord.value > 9 => coord.copy(value = 0)
                        case coord => coord
                    })

            step(resetGrid, numSteps-1, numFlashes+flashedGrid._2)
        }
    }

    val source = io.Source.fromFile("./src/main/scala/Day11/input.txt")
    val lines = try source.getLines().toList finally source.close()

    val initialGrid = lines.map(_.toList.map(_.asDigit))
    val gridWithIndex = initialGrid
        .zipWithIndex
        .map {
            case (points, row) => points.zipWithIndex.map {
                case (point, column) => Coord(point, Point(row, column)) } }

    val grid = step(gridWithIndex, 100, 0)
    printGrid(grid._1)
    println(s"Number of flashes: ${grid._2}")
}
