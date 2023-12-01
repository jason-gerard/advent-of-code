package Day4

object Day4Part1 extends App {
    val source = io.Source.fromFile("./src/main/scala/Day4/input.txt")
    val lines = try source.getLines().toList.map(_.toList) finally source.close()
}
