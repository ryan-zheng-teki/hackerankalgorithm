package algorithm;

import java.util.*;

public class TravelingSalesMan {
    static int shortestPath = Integer.MAX_VALUE;

    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.init(4);

        graph.addConnection(1, 2, 10);
        graph.addConnection(1, 3, 15);
        graph.addConnection(1, 4, 20);
        graph.addConnection(2, 3, 35);
        graph.addConnection(2, 4, 25);
        graph.addConnection(3, 4, 30);


    }

    /*
    This is the same as finding all the permutations.The time complexity is O(n!)
    But because of symmetricity.The time could be n!/2 by some ways.Hard to do it.
     */
    static void shortestHamiltonianCycle(Graph graph, LinkedHashSet<Integer> visited, int nodeId, int length) {
        //linkedHashSet even preserve order. Cool
        visited.add(nodeId);

        Set<Connection> connectedNodes = graph.getConnections(nodeId);
        boolean allVisited = true;
        for (Connection connection : connectedNodes) {
            if (visited.contains(connection.getNodeId())) {
                continue;
            } else {
                allVisited = false;
                shortestHamiltonianCycle(graph, visited, connection.getNodeId(),
                        length + connection.getWeight());
                //clear All Elements After NodeId
            }
        }

        if (allVisited) {
            shortestPath = Math.min(shortestPath, length);
            return;
        }
    }


    static class Connection {
        int nodeId;
        int weight;

        Connection(int nodeId, int weight) {
            this.nodeId = nodeId;
            this.weight = weight;
        }

        int getNodeId() {
            return nodeId;
        }

        int getWeight() {
            return weight;
        }

        @Override
        public int hashCode() {
            return nodeId;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Connection other = (Connection) obj;
            if (nodeId != other.nodeId) {
                return false;
            } else {
                return true;
            }
        }
    }

    static class Graph {
        int vertexCount;

        //the index represents the graph node id
        List<Set<Connection>> vertices;

        void init(int vertexCount) {
            vertices = new ArrayList<>(vertexCount);
            for (int i = 0; i < vertexCount; i++) {
                vertices.add(new HashSet<>());
            }
        }


        Set<Connection> getConnections(int sourceNode) {
            return vertices.get(sourceNode);
        }

        int getWeight(int sourceNode, int targetNode) {
            return vertices.get(sourceNode).stream()
                    .filter(connection -> connection.getNodeId() == targetNode)
                    .findFirst().map(Connection::getWeight).get();
        }

        void addConnection(int sourceNode, int targetNode, int weight) {
            vertices.get(sourceNode - 1).add(new Connection(targetNode, weight));
        }


        void depthFirstSearch() {

        }

        void breadthFirstSearch() {

        }
    }
}
