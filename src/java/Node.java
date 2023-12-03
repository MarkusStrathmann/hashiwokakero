package src.java;

public class Node {
    // constructor
    Node() {
    };

    public Node(Node node) {
        setActualWeight(node.actualWeight);
        setTargetWeight(node.targetWeight);
        setConnectedEdges(node.connectedEdges);
        setNeighborNodes(node.getNeighborNodes());
    };

    private String name;
    private int actualWeight = 0;
    private int targetWeight;
    private Edge[] connectedEdges = new Edge[6]; // index 0-5 shows direction
    private Node[] neighborNodes = new Node[6]; // index 0-5 shows direction

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getActualWeight() {
        return actualWeight;
    }

    public void setActualWeight(int actualWeight) {
        this.actualWeight = actualWeight;
    }

    public int getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(int targetWeight) {
        this.targetWeight = targetWeight;
    }

    public Edge getConnectedEdge(int direction) {
        return connectedEdges[direction];
    }

    public Edge[] getConnectedEdges() {
        return connectedEdges;
    }

    public void setConnectedEdge(int direction, Edge connectedEdge) {
        this.connectedEdges[direction] = connectedEdge;
    }

    public void setConnectedEdges(Edge[] connectedEdges) {
        this.connectedEdges = connectedEdges;
    }

    public Node getNeighborNode(int direction) {
        return neighborNodes[direction];
    }

    public Node[] getNeighborNodes() {
        return neighborNodes;
    }

    public void setNeighborNode(int direction, Node neighborNode) {
        this.neighborNodes[direction] = neighborNode;
    }

    public void setNeighborNodes(Node[] neighborNodes) {
        this.neighborNodes = neighborNodes;
    }

    public int getNConnectedEdges() {
        int nConnectedEdges = 0;
        for (Edge edge : connectedEdges) {
            if (edge != null) {
                nConnectedEdges++;
            }
        }
        ;
        return nConnectedEdges;
    }

    public int getNFreeEdges() {
        int nFreeEdges = 0;
        for (Edge edge : connectedEdges) {
            if (edge != null && edge.getNPossibleBridges() != 0) {
                nFreeEdges++;
            }
        }
        ;
        return nFreeEdges;
    }

    public int getNPossibleBridges() {
        int nPossibleBridges = 0;
        Edge edge;
        for (int dir = 0; dir < 6; dir++) {
            edge = this.getConnectedEdge(dir);
            if (edge != null && edge.getNPossibleBridges() != 0) {
                if (edge.getNPossibleBridges() == 1) {
                    nPossibleBridges++;
                } else if (edge.getNPossibleBridges() == 2) {
                    nPossibleBridges++;
                    nPossibleBridges++;
                }
            }
        }
        ;
        return nPossibleBridges;
    }

    public int getWeightDifference() {
        return this.targetWeight - this.actualWeight;
    }

    public int getNFunnyEdges() { // funny edges are edges that show the same targetWeight on both connected nodes
        int nFunnyEdges = 0;
        for (int dir = 0; dir < 6; dir++) {
            if (getConnectedEdge(dir) != null && getConnectedEdge(dir).getNPossibleBridges() != 0)
                if ((getTargetWeight() == 2 && getNeighborNode(dir).getTargetWeight() == 2)
                        || (getTargetWeight() == 1 && getNeighborNode(dir).getTargetWeight() == 1)) {
                    nFunnyEdges++;
                }
        }
        return nFunnyEdges;
    }

    public void incrementActualWeight() {
        this.actualWeight++;
    }

    public void decrementActualWeight() {
        this.actualWeight--;
    }
}
