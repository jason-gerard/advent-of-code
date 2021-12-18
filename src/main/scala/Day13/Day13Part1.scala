package Day13

object Day13Part1 extends App {
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

    val source = io.Source.fromFile("./src/main/scala/Day13/input.txt")
    val lines = try source.getLines().toList finally source.close()

    val dots = getDots(lines)
    val folds = getFolds(lines)

    println(dots)
    println(folds)

    val foldedDots = doFold((dots), folds(0))
    println(foldedDots)
    println(s"${foldedDots.size} dots visible after folding")
}
