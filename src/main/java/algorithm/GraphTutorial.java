package algorithm;

import java.util.Set;

public class GraphTutorial {


    static class Connection {
        int weight;
        GraphNode directedNode;
    }

    static class GraphNode {
        Set<Connection> connections;

        void addConnection(Connection directedNode) {
            if(!connections.contains(directedNode)) {
                connections.add(directedNode);
            }
        }
    }


}
