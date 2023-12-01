package Day2

object Day2Part1 extends App {
    val source = io.Source.fromFile("./src/main/scala/Day2/input.txt")
    val lines = try source.getLines().toList finally source.close()

    val directions = lines.map(line => line.split(" ") match {
        case Array(direction, magnitude) => (direction, magnitude.toInt)
    })

    val forward_amount = directions
        .map {
            case ("forward", mag) => mag
            case (_, _) => 0
        }
        .sum

    val depth_amount = directions
        .map{
            case ("up", mag) => -mag
            case ("down", mag) => mag
            case (_, _) => 0
        }
        .sum

    println(forward_amount)
    println(depth_amount)
    println(forward_amount * depth_amount)
}
