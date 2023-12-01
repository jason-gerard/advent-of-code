package Day13

import scala.annotation.tailrec

object Day13Part2 extends App {
    def printDots(dots: Set[(Int, Int)]): Unit = {
        val width = getWidth(dots)
        val height = getHeight(dots)

        val grid = (0 to height).map(y => (0 to width).map(x => (x, y)).toList).toList
        val dotGrid = grid.map(_.map {
            case p if dots.contains(p) => "# "
            case _ => ". "
        })

        dotGrid.foreach(line => println(line.mkString(" ")))
    }

    def getDots(lines: List[String]): Set[(Int, Int)] = lines.flatMap {
        case point if point.contains(",") => {
            val parts = point.split(",")
            Some((parts(0).toInt, parts(1).toInt))
        }
        case _ => None
    }.toSet

    def getFolds(lines: List[String]): List[(String, Int)] = lines.flatMap {
        case point if point.contains("=") => {
            val parts = point.split(" ")(2).split("=")
            Some((parts(0), parts(1).toInt))
        }
        case _ => None
    }

    def getWidth(dots: Set[(Int, Int)]): Int = dots.maxBy(_._1)._1
    def getHeight(dots: Set[(Int, Int)]): Int = dots.maxBy(_._2)._2

    def doFold(dots: Set[(Int, Int)], fold: (String, Int)): Set[(Int, Int)] = fold match {
        case ("y", foldLine) => {
            dots.flatMap {
                case (x, y) if y < foldLine  => Some((x, y))
                case (x, y) if y >= foldLine && y <= (foldLine*2) => Some((x, (foldLine*2) - y))
                case _ => None
            }
        }
        case ("x", foldLine) => {
            dots.flatMap {
                case (x, y) if x < foldLine  => Some((x, y))
                case (x, y) if x > foldLine && x <= (foldLine*2) => Some(((foldLine*2) - x, y))
                case _ => None
            }
        }
    }

    @tailrec
    def printAllFolds(dots: Set[(Int, Int)], folds: List[(String, Int)]): Unit = {
        if (folds.isEmpty) {
            println(s"${dots.size} dots visible after folding")
        } else {
            val fold = folds.head
            val foldedDots = doFold(dots, fold)
            printDots(foldedDots)
            println()

            printAllFolds(foldedDots, folds.tail)
        }
    }

    val source = io.Source.fromFile("./src/main/scala/Day13/input.txt")
    val lines = try source.getLines().toList finally source.close()

    val dots = getDots(lines)
    val folds = getFolds(lines)

    printAllFolds(dots, folds)
}
