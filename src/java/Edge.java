package src.java;

import java.util.Vector;

public class Edge {
    // constructor
    Edge(Node firstNode, Node secondNode, String name) {
        this.connectedNodes[0] = firstNode;
        this.connectedNodes[1] = secondNode;
        this.name = name;
    };

    // variables
    String name;
    int nBridges;
    int nPossibleBridges = 2;
    Vector<Node> nodesInPath = new Vector<Node>();
    Node[] connectedNodes = new Node[2];
    boolean blocked = false;

    // getter and setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getnBridges() {
        return nBridges;
    }

    public void setnBridges(int nBridges) {
        this.nBridges = nBridges;
    }

    public Vector<Node> getNodesInPath() {
        return nodesInPath;
    }

    public void setŃodesInPath(Vector<Node> nodesInPath) {
        this.nodesInPath.clear();
        for (Node node : nodesInPath) {
            this.nodesInPath.add(node);
        }
    }

    public Node[] getconnectedNodes() {
        return connectedNodes;
    }

    public void setconnectedNodes(Node[] connectedNodes) {
        this.connectedNodes = connectedNodes;
    }

    public void setNodesInPath(Vector<Node> nodesInPath) {
        this.nodesInPath = nodesInPath;
    }

    public Node[] getConnectedNodes() {
        return connectedNodes;
    }

    public void setConnectedNodes(Node[] connectedNodes) {
        this.connectedNodes = connectedNodes;
    }

    public void setNPossibleBridges(int nPossibleBridges) {
        this.nPossibleBridges = nPossibleBridges;
    }

    // methods
    public int getNPossibleBridges() {
        if (this.nPossibleBridges != 0) {
            if (connectedNodes[0].getWeightDifference() == 0 || connectedNodes[1].getWeightDifference() == 0) {
                setNPossibleBridges(0);
            } else if (connectedNodes[0].getWeightDifference() == 1 || connectedNodes[1].getWeightDifference() == 1) {
                setNPossibleBridges(1);
            }
        }
        return nPossibleBridges;
    }

    public void incrementNBridges() {
        this.nBridges++;
        this.nPossibleBridges--;
        this.connectedNodes[0].incrementActualWeight();
        this.connectedNodes[1].incrementActualWeight();
    }

    public void blockCrossingEdges(Vector<Edge> edges) {
        if (nodesInPath.size() > 0) {
            for (Edge edge : edges) {
                if (edge != this) {
                    for (Node nodeA : edge.getNodesInPath()) {
                        for (Node nodeB : nodesInPath) {
                            if (nodeA == nodeB) {
                                edge.setNPossibleBridges(0);
                                ;
                            }
                        }
                    }
                }

            }
        }
    }
}
