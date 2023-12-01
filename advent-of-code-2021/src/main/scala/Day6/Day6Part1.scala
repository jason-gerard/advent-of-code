package Day6

import scala.annotation.tailrec

object Day6Part1 extends App {
    val source = io.Source.fromFile("./src/main/scala/Day6/input.txt")
    val lines = try source.getLines().toList finally source.close()

    val initialState = lines
        .head
        .split(",")
        .toList
        .map(_.toInt)

    @tailrec
    def processDay(state: List[Int], daysLeft: Int): List[Int] = {
        println(daysLeft)
        println(state)
        if (daysLeft == 0) {
            state
        } else {
            val newState = state.flatMap(reduceTimer)
            processDay(newState, daysLeft - 1)
        }
    }

    def reduceTimer(timer: Int): List[Int] = timer match {
        case timer if timer >= 1 => List(timer - 1)
        case timer if timer == 0 => List(6, 8)
    }

    val after80Days = processDay(initialState, 80)
    println(after80Days.size)
}
