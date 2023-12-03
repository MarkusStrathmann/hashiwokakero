package src.java;

import java.util.Vector;

public class Edge {
    // constructor
    public Edge(Node firstNode, Node secondNode, String name) {
        this.connectedNodes[0] = firstNode;
        this.connectedNodes[1] = secondNode;
        this.name = name;
    };

    private String name;
    private int nBridges;
    private int nPossibleBridges = 2;
    private Vector<Node> nodesInPath = new Vector<Node>();
    private Node[] connectedNodes = new Node[2];

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
        if (nBridges < 0 || nBridges > 2) {
            throw new IllegalArgumentException("Argument must be between 0 and 2 but is " + nBridges + "!");
        } else {
            this.nBridges = nBridges;
        }
    }

    public Vector<Node> getNodesInPath() {
        return nodesInPath;
    }

    public void setŃodesInPath(Vector<Node> nodesInPath) {
        if (nodesInPath.size() == 0) {
            throw new IllegalArgumentException(
                    "Argument must contain at least one node but contains " + nodesInPath.size() + "!");
        } else {
            this.nodesInPath.clear();
            for (Node node : nodesInPath) {
                this.nodesInPath.add(node);
            }
        }
    }

    public Node[] getConnectedNodes() {
        return connectedNodes;
    }

    public void setConnectedNodes(Node[] connectedNodes) {
        if (connectedNodes.length != 6) {
            throw new IllegalArgumentException(
                    "Argument must contain 6 nodes but contains " + connectedNodes.length + "!");
        } else {
            this.connectedNodes = connectedNodes;
        }
    }

    public void setNPossibleBridges(int nPossibleBridges) {
        if(nPossibleBridges < 0 || nPossibleBridges > 2){
            throw new IllegalArgumentException("Argument must be between 0 and to but is " + nPossibleBridges + "!");
        }
        this.nPossibleBridges = nPossibleBridges;
    }

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
        if (getnBridges() > 1 || getnBridges() < 0){
            throw new IllegalStateException("Unable to increment nBridges. NBridges must be either 0 or 1 but is " + getNPossibleBridges() + "!");
        } else {
            this.nBridges++;
            this.nPossibleBridges--;
            this.connectedNodes[0].incrementActualWeight();
            this.connectedNodes[1].incrementActualWeight();
        }
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
