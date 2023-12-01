package Day3

import java.util

object Day3Part1 extends App {
    val source = io.Source.fromFile("./src/main/scala/Day3/input.txt")
    val lines = try source.getLines().toList finally source.close()

    val gammaRate = lines
        .map(_.toList)
        .transpose
        .map(_
            .groupBy(identity)
            .mapValues(_.size)
            .maxBy(_._2)
            ._1)
        .mkString

    val gammaRateNum = Integer.parseInt(gammaRate, 2)
    val epsilonRateNum = gammaRateNum ^ Integer.parseInt("111111111111", 2)

    println(gammaRateNum)
    println(epsilonRateNum)
    println(gammaRateNum*epsilonRateNum)
}
