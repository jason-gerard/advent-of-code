package Day1

object Day1Part1 extends App {
    val source = io.Source.fromFile("./src/main/scala/Day1/input.txt")
    val lines = try source.getLines().toList finally source.close()
    
    val depths = lines.map(line => line.toInt)
    val num_inc = depths
        .sliding(2)
        .map{ case List(curr: Int, next: Int) => if (next > curr) 1 else 0 }
        .sum

    print(num_inc)
}
