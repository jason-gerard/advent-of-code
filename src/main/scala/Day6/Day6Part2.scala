package Day6

import scala.annotation.tailrec

object Day6Part2 extends App {
    val source = io.Source.fromFile("./src/main/scala/Day6/input.txt")
    val lines = try source.getLines().toList finally source.close()

    val initialState = lines
        .head
        .split(",")
        .toList
        .map(_.toInt)
        .groupBy(identity)
        .transform((_, v) => BigInt(v.size))

    @tailrec
    def processDay(totalDays: Int)(state: Map[Int, BigInt], day: Int): Map[Int, BigInt] = {
        if (day == totalDays) {
            state
        } else {
            val today = day % 9
            val nextDay = ((day % 9) + 7) % 9

            val newTimerNum = state.getOrElse(nextDay, BigInt(0)) + state.getOrElse(today, BigInt(0))
            val newState = state + (nextDay -> newTimerNum)

            processDay(totalDays)(newState, day+1)
        }
    }

    val after80Days = processDay(256)(initialState, 0)
    val numberOfFish = after80Days.map((k, v) => v).sum
    println(s"$numberOfFish lanternfish")
}
