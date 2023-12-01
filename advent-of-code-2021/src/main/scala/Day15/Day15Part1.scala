package Day15

object Day15Part1 extends App {
    def printGraph(g: List[List[Int]]): Unit = g.foreach(row => println(row.mkString))

    val source = io.Source.fromFile("./src/main/scala/Day15/input-test.txt")
    val lines = try source.getLines().toList finally source.close()

    val graph = lines.map(_.map(_.asDigit).toList)

    printGraph(graph)
}
