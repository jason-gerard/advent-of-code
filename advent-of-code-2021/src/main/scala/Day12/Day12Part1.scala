package Day12

import Day12.Day12Part1.graph

object Day12Part1 extends App {
    val source = io.Source.fromFile("./src/main/scala/Day12/input.txt")
    val lines = try source.getLines().toList finally source.close()

    case class Vertex(name: String)
    case class Edge(v1: Vertex, v2: Vertex)

    val vertices = lines.flatMap(_.split("-")).toSet.map(Vertex.apply)
    val edges = lines.map(_.split("-") match { case Array(v1, v2) => Edge(Vertex(v1), Vertex(v2)) })

    val graph = vertices
        .map(v => v -> edges
            .flatMap {
                case e if e.v1 == v => Some(e.v2)
                case e if e.v2 == v => Some(e.v1)
                case _ => None
            })
        .toMap
        .transform((_, v) => v.filter(_.name != "start"))

    def traverse(graph: Map[Vertex, List[Vertex]], vertex: Vertex): List[List[Vertex]] = {
        println(vertex)
        println(graph.mkString("\n"))
        println()

        if (vertex.name == "end") {
            List(List(Vertex("end")))
        } else {
            val isSmallCave = vertex.name.toLowerCase == vertex.name
            val newGraph = if (isSmallCave) graph - vertex else graph

            val moves = graph.getOrElse(vertex, List())
            moves
                .flatMap(nextVertex => traverse(newGraph, nextVertex))
                .map(path => vertex +: path)
        }
    }

    val paths = traverse(graph, Vertex("start"))
    println(paths.mkString("\n"))

    println(s"There are a total of ${paths.size} paths")
}
