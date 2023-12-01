package Day1

object Day1Part2 extends App {
    val source = io.Source.fromFile("./src/main/scala/Day1/input.txt")
    val lines = try source.getLines().toList finally source.close()

    val num_inc = lines
        .map(line => line.toInt)
        .sliding(4)
        .map(l => if (l.takeRight(3).sum > l.take(3).sum) 1 else 0)
        .sum

    print(num_inc)
}
