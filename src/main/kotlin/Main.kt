import java.util.function.Consumer

data class Edge(val to: Vertex, val distance: Int = Int.MAX_VALUE)

class Vertex(val name: String = "None") {
    private val edgeConnection: MutableList<Edge> = ArrayList()
    
    fun addEdgeConnection(to: Edge) {
        edgeConnection.add(to)
    }

    fun getEdgeConnections(): List<Edge> {
        return edgeConnection
    }
}

class GraphPath {

    private val vertices: MutableList<Vertex> = ArrayList()
    private val distanceFromStart = HashMap<Vertex, Int>() // <Vertex, Its distance from start node>
    private val predecessorVertex = HashMap<Vertex, Vertex>() // <Vertex, Its predecessor in path>

    fun printShortestRoutes(start: Vertex) {
        // Initialize
        vertices.forEach(Consumer { c: Vertex ->
            distanceFromStart[c] = INFINITE
            predecessorVertex[c] = Vertex()
        })

        // Set distance from start node to 0
        distanceFromStart[start] = 0
        val s: MutableList<Vertex> = ArrayList()
        val q: MutableList<Vertex> = vertices.toMutableList()

        while (q.isNotEmpty()) {
            val u: Vertex = extractMin(q)
            q.remove(u)
            s.add(u)
            for (v in u.getEdgeConnections()) {
                if (distanceFromStart.getOrElse(v.to) { 0 } > distanceFromStart.getOrElse(u) { 0 } + v.distance) {
                    distanceFromStart[v.to] = distanceFromStart.getOrElse(u) { 0 } + v.distance
                    predecessorVertex[v.to] = u
                }
            }
        }
        prettyPrintShortestRoutes(start)
    }

    fun addVertex(vertex: Vertex) {
        vertices.add(vertex)
    }

    private fun prettyPrintShortestRoutes(source: Vertex) {
        for (c in vertices) {
            var road: String = c.name
            var p: Vertex = predecessorVertex.getOrElse(c) { Vertex() }
            while (p.name != "None") {
                road = "${p.name} ->  $road"
                p = predecessorVertex.getOrElse(p) { Vertex() }
            }
            println(
                "Distance from ${source.name} to ${c.name}: ${distanceFromStart[c]} km"
            )
            println("Route: $road\n")
        }
    }

    private fun extractMin(q: List<Vertex>): Vertex {
        var min: Vertex = q[0]
        for (c in q) {
            if (distanceFromStart.getOrElse(c){0} < distanceFromStart.getOrElse(min){0}) {
                min = c
            }
        }
        return min
    }

    companion object {
        private const val INFINITE = Int.MAX_VALUE
    }
}

fun main(args: Array<String>) {
    val amsterdam = Vertex("Amsterdam")
    val hilversum = Vertex("Hilversum")
    val utrecht = Vertex("Utrecht")
    val ede = Vertex("Ede")
    val tiel = Vertex("Tiel")
    val arnhem = Vertex("Arnhem")

    amsterdam.addEdgeConnection(Edge(hilversum, 34))
    amsterdam.addEdgeConnection(Edge(utrecht, 43))
    hilversum.addEdgeConnection(Edge(ede, 56))
    hilversum.addEdgeConnection(Edge(tiel, 57))
    hilversum.addEdgeConnection(Edge(utrecht, 20))
    utrecht.addEdgeConnection(Edge(hilversum, 20))
    utrecht.addEdgeConnection(Edge(tiel, 50))
    ede.addEdgeConnection(Edge(arnhem, 20))
    tiel.addEdgeConnection(Edge(ede, 37))

    val path = GraphPath()
    path.addVertex(amsterdam)
    path.addVertex(hilversum)
    path.addVertex(utrecht)
    path.addVertex(ede)
    path.addVertex(tiel)
    path.addVertex(arnhem)

    path.printShortestRoutes(amsterdam)

}
