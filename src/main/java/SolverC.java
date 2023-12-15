import java.util.Vector;

public class SolverC extends AbstractSolver {
    // constructor
    public SolverC() {
    };

    // ambiguous square
    public static boolean apply(Node node, Vector<Edge> edges) {
        buildedBridge = false;
        // Node
        Node NodeA;
        Node NodeB;
        Node NodeC;
        Node NodeD;
        // Edge
        Edge EdgeA;
        Edge EdgeB;
        Edge EdgeC;
        Edge EdgeD;
        // weightDifferenc
        int wdA;
        int wdB;
        int wdC;
        int wdD;
        // getNBridges == 0
        boolean a0;
        boolean b0;
        boolean c0;
        boolean d0;

        int[] inclinationDirs = new int[] { 2, 3 }; // for ambiguous sqaures inclined to left and right

        for (int incDir : inclinationDirs) {
            if (buildedBridge == true) {
                break;
            }
            try {
                NodeA = node;
                NodeB = node.getNeighborNode(incDir);
                NodeC = node.getNeighborNode(incDir).getNeighborNode(4);
                NodeD = node.getNeighborNode(4);
                EdgeA = node.getConnectedEdge(incDir);
                EdgeB = node.getNeighborNode(incDir).getConnectedEdge(4);
                EdgeC = node.getNeighborNode(4).getConnectedEdge(incDir);
                EdgeD = node.getConnectedEdge(4);
                EdgeA.getnBridges();
                EdgeB.getnBridges();
                EdgeC.getnBridges();
                EdgeD.getnBridges();
            } catch (Exception e) {
                continue;
            }
            wdA = NodeA.getWeightDifference();
            wdB = NodeB.getWeightDifference();
            wdC = NodeC.getWeightDifference();
            wdD = NodeD.getWeightDifference();

            a0 = EdgeA.getnBridges() == 0;
            b0 = EdgeB.getnBridges() == 0;
            c0 = EdgeC.getnBridges() == 0;
            d0 = EdgeD.getnBridges() == 0;

            Vector<Node> squareNodes = new Vector<Node>();
            squareNodes.add(NodeA);
            squareNodes.add(NodeB);
            squareNodes.add(NodeC);
            squareNodes.add(NodeD);

            if ((wdA == wdB && wdB == wdC && wdC == wdD && wdD == 1)
                    && checkForPossibleBridgesOutsideOfSquare(squareNodes) == false) {
                if ((!a0 && b0 && c0 && d0) || (!c0 && a0 && b0 && d0)) {
                    EdgeB.incrementNBridges();
                    EdgeB.blockCrossingEdges(edges);
                    buildInfo = buildInfo(NodeB, 4);
                    System.out.println(buildInfo
                            + " with value 1: ambiguous square (builded bridge connected to existing edge)");
                    EdgeD.incrementNBridges();
                    EdgeD.blockCrossingEdges(edges);
                    buildInfo = buildInfo(NodeA, 4);
                    System.out.println(buildInfo
                            + " with value 1: ambiguous square (builded bridge connected to existing edge)");
                    buildedBridge = true;
                } else if ((!b0 && a0 && c0 && d0) || (!d0 && a0 && b0 && c0)) {
                    EdgeA.incrementNBridges();
                    EdgeA.blockCrossingEdges(edges);
                    buildInfo = buildInfo(NodeA, incDir);
                    System.out.println(buildInfo
                            + " with value 1: ambiguous square (builded bridge connected to existing edge)");
                    EdgeC.incrementNBridges();
                    EdgeC.blockCrossingEdges(edges);
                    buildInfo = buildInfo(NodeD, incDir);
                    System.out.println(buildInfo
                            + " with value 1: ambiguous square (builded bridge connected to existing edge)");
                    buildedBridge = true;
                }
            }
        }
        return buildedBridge;
    }

    private static boolean checkForPossibleBridgesOutsideOfSquare(Vector<Node> squareNodes) {
        boolean possibleBridgesOutsideOfSquare = false;
        for (Node node : squareNodes) {
            for (int dir = 0; dir < 6; dir++) {
                if (node.getConnectedEdge(dir) != null) {
                    if (node.getConnectedEdge(dir).getNPossibleBridges() > 0
                            && squareNodes.contains(node.getNeighborNode(dir)) == false) {
                        possibleBridgesOutsideOfSquare = true;
                    }
                }
            }
            if (possibleBridgesOutsideOfSquare == true) {
                break;
            }
        }
        return possibleBridgesOutsideOfSquare;
    }
}
