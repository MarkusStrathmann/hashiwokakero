import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import java.util.HashMap;

public class Graph {
    // constructor
    public Graph(int[][] weightMatrix) {
        this.weightMatrix = weightMatrix;
        setnRowWeightMatrix(weightMatrix.length);
        setnColWeightMatrix(weightMatrix[0].length);
        this.nodeMatrix = new Node[getnRowWeightMatrix()][getnColWeightMatrix()];
        defineNodeTypeHashMap();
        initializeGraph();
        updateEdges();
        upddateNeighbors();
        extendEdges();
        updateEdges();
        upddateNeighbors();
        cleanGraph();
        updateEdges();
        upddateNeighbors();
    }

    private int nodeNumber = 0;
    private int edgeNumber = 0;
    private int[][] weightMatrix;
    private Node[][] nodeMatrix;
    private int nRowWeightMatrix;
    private int nColWeightMatrix;
    private Vector<Node> graph = new Vector<Node>();
    private Vector<Edge> edges = new Vector<Edge>();
    private HashMap<String, int[][]> nodeTypeHashMap = new HashMap<String, int[][]>();

    // colors
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_WHITE = "\u001B[90m";

    public int getnRowWeightMatrix() {
        return nRowWeightMatrix;
    }

    public void setnRowWeightMatrix(int nRowWeightMatrix) {
        if (nRowWeightMatrix < 2) {
            throw new IllegalArgumentException("Argument must be at least 2 but is " + nRowWeightMatrix + "!");
        } else {
            this.nRowWeightMatrix = nRowWeightMatrix;
        }
    }

    public int getnColWeightMatrix() {
        return nColWeightMatrix;
    }

    public void setnColWeightMatrix(int nColWeightMatrix) {
        if (nColWeightMatrix < 2) {
            throw new IllegalArgumentException("Argument must be at least 2 but is " + nColWeightMatrix + "!");
        } else {
            this.nColWeightMatrix = nColWeightMatrix;
        }
    }

    private void initializeGraph() {
        if (getnRowWeightMatrix() < 2 || getnColWeightMatrix() < 2) {
            throw new IllegalStateException(
                    "Unable to initialize graph. NRowWeightMatrix and nColweightMatrix must both be at least 2 but are "
                            + getnRowWeightMatrix() + " and " + getnColWeightMatrix() + "!");
        } else {
            for (int row = 0; row < getnRowWeightMatrix(); row++) {
                for (int col = 0; col < getnColWeightMatrix(); col++) {
                    nodeMatrix[row][col] = new Node();
                    nodeMatrix[row][col].setName("N-" + String.format("%03d", ++nodeNumber));
                }
            }
            boolean indented;

            for (int row = 0; row < getnRowWeightMatrix(); row++) {
                for (int col = 0; col < getnColWeightMatrix(); col++) {
                    nodeMatrix[row][col].setTargetWeight(weightMatrix[row][col]);
                    if ((row + 1) % 2 == 0) {
                        indented = true;
                    } else {
                        indented = false;
                    }

                    nodeMatrix[row][col].setConnectedEdges(connectEdges(row, col, indented));
                    graph.add(nodeMatrix[row][col]);
                }
            }
        }
    }

    public void showNodeInfo() {
        System.out.println("");
        System.out.println(
                "nodeName | actualWeight/targetWeight | connectedEdges (in direction 0 to 5) | nPossibleBridges");
        String infoString = new String();
        for (Node node : graph) {
            infoString = node.getName() + " | " + node.getActualWeight() + "/" + node.getTargetWeight() + " | ";
            for (int dir = 0; dir < 6; dir++) {
                if (node.getConnectedEdge(dir) != null) {
                    infoString = infoString + "(" + dir + ") " + node.getConnectedEdge(dir).getName() + " ";
                } else {
                    infoString = infoString + "(" + dir + ") " + "null" + " ";
                }
            }
            infoString = infoString + "  | nPB: " + node.getNPossibleBridges();
            System.out.println(infoString);
        }
    }

    public void showEdgeInfo() {
        String nodesInPath = "";
        System.out.println(
                "edge name | nBridges+nPossibleBridges | names of connected nodes | intermediate nodes (for extended edges only)");
        for (Edge edge : edges) {
            nodesInPath = " ";
            if (edge.getNodesInPath() != null) {
                for (int i = 0; i < edge.getNodesInPath().size(); i++) {
                    nodesInPath = nodesInPath + edge.getNodesInPath().get(i).getName() + " ";
                }
            }
            System.out.println(edge.getName() + " | " + edge.getnBridges() + "+" + edge.getNPossibleBridges() + " | " +
                    edge.getConnectedNodes()[0].getName()
                    + " to " + edge.getConnectedNodes()[1].getName() + " | (" + nodesInPath + ")");
        }
    }

    private void defineNodeTypeHashMap() {
        // "row_col_indented", connection in direction 0 to 5 ({0}=no connected edge,
        // {x,y}=new edge, {x,y,z}=existing edge )
        nodeTypeHashMap.put("first_first_false", new int[][] { { 0 }, { 0 }, { 0 }, { 1, 0 }, { 0, 1 }, { 0 } });
        nodeTypeHashMap.put("first_last_false", new int[][] { { 0 }, { 0, -1, 4 }, { 1, -1 }, { 1, 0 }, { 0 }, { 0 } });
        nodeTypeHashMap.put("last_first_true",
                new int[][] { { -1, 0, 3 }, { 0 }, { 0 }, { 0 }, { 0, 1 }, { -1, 1, 2 } });
        nodeTypeHashMap.put("last_first_false", new int[][] { { 0 }, { 0 }, { 0 }, { 0 }, { 0, 1 }, { -1, 0, 2 } });
        nodeTypeHashMap.put("last_last_true", new int[][] { { -1, 0, 3 }, { 0, -1, 4 }, { 0 }, { 0 }, { 0 }, { 0 } });
        nodeTypeHashMap.put("last_last_false",
                new int[][] { { -1, -1, 3 }, { 0, -1, 4 }, { 0 }, { 0 }, { 0 }, { -1, 0, 2 } });
        nodeTypeHashMap.put("first_any_false",
                new int[][] { { 0 }, { 0, -1, 4 }, { 1, -1 }, { 1, 0 }, { 0, 1 }, { 0 } });
        nodeTypeHashMap.put("last_any_true",
                new int[][] { { -1, 0, 3 }, { 0, -1, 4 }, { 0 }, { 0 }, { 0, 1 }, { -1, 1, 2 } });
        nodeTypeHashMap.put("last_any_false",
                new int[][] { { -1, -1, 3 }, { 0, -1, 4 }, { 0 }, { 0 }, { 0, 1 }, { -1, 0, 2 } });
        nodeTypeHashMap.put("any_first_true",
                new int[][] { { -1, 0, 3 }, { 0 }, { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1, 2 } });
        nodeTypeHashMap.put("any_first_false", new int[][] { { 0 }, { 0 }, { 0 }, { 1, 0 }, { 0, 1 }, { -1, 0, 2 } });
        nodeTypeHashMap.put("any_last_true", new int[][] { { -1, 0, 3 }, { 0, -1, 4 }, { 1, 0 }, { 0 }, { 0 }, { 0 } });
        nodeTypeHashMap.put("any_last_false",
                new int[][] { { -1, -1, 3 }, { 0, -1, 4 }, { 1, -1 }, { 1, 0 }, { 0 }, { -1, 0, 2 } });
        nodeTypeHashMap.put("any_any_true",
                new int[][] { { -1, 0, 3 }, { 0, -1, 4 }, { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1, 2 } });
        nodeTypeHashMap.put("any_any_false",
                new int[][] { { -1, -1, 3 }, { 0, -1, 4 }, { 1, -1 }, { 1, 0 }, { 0, 1 }, { -1, 0, 2 } });
    }

    private Edge[] connectEdges(int row, int col, boolean indented) {
        Edge[] edges = new Edge[6];
        String nodeTypeRow = new String();
        String nodeTypeCol = new String();
        String nodeTypeIndented = new String();

        if (row == 0) {
            nodeTypeRow = "first";
        } else if (row == getnRowWeightMatrix() - 1) {
            nodeTypeRow = "last";
        } else {
            nodeTypeRow = "any";
        }
        if (col == 0) {
            nodeTypeCol = "first";
        } else if (col == getnColWeightMatrix() - 1) {
            nodeTypeCol = "last";
        } else {
            nodeTypeCol = "any";
        }
        if (indented == true) {
            nodeTypeIndented = "true";
        } else {
            nodeTypeIndented = "false";
        }

        String nodeType = nodeTypeRow + "_" + nodeTypeCol + "_" + nodeTypeIndented;

        int[][] connections = nodeTypeHashMap.get(nodeType);
        for (int dir = 0; dir < 6; dir++) {
            if (connections[dir].length == 1) {
                edges[dir] = null;
            } else if (connections[dir].length == 2) {
                edges[dir] = new Edge(nodeMatrix[row][col],
                        nodeMatrix[row + connections[dir][0]][col + connections[dir][1]],
                        "E-" + String.format("%03d", ++edgeNumber));
            } else {
                edges[dir] = nodeMatrix[row + connections[dir][0]][col + connections[dir][1]]
                        .getConnectedEdge(connections[dir][2]);
            }
        }
        return edges;
    }

    private void updateEdges() {
        edges.clear();
        for (Node node : graph) {
            for (int dir = 0; dir < 3; dir++) {
                if (node.getConnectedEdge(dir) != null) {
                    edges.add(node.getConnectedEdge(dir));
                }
            }
        }
        Collections.sort(edges, Comparator.comparing(s -> s.getName()));
    }

    private void upddateNeighbors() {
        for (Node node : graph) {
            for (int dir = 0; dir < 6; dir++) {
                if (node.getConnectedEdge(dir) != null) {
                    if (dir == 0 || dir == 1 || dir == 5) {
                        node.setNeighborNode(dir, node.getConnectedEdge(dir).getConnectedNodes()[0]);
                    } else {
                        node.setNeighborNode(dir, node.getConnectedEdge(dir).getConnectedNodes()[1]);
                    }
                }
            }
        }
    }

    private void extendEdges() {
        Vector<Node> wayPoints = new Vector<Node>();
        for (Node node : graph) {
            if (node.getTargetWeight() != 0) {
                for (int dir = 0; dir < 6; dir++) {
                    wayPoints.clear();
                    Node dummyNode = new Node(node);
                    while (dummyNode.getConnectedEdge(dir) != null) {
                        dummyNode = dummyNode.getNeighborNode(dir);
                        if (dummyNode.getTargetWeight() != 0) {
                            if (wayPoints.size() > 0) {
                                if (dir == 2 || dir == 3 || dir == 4) {
                                    node.setConnectedEdge(dir,
                                            new Edge(node, dummyNode, node.getConnectedEdge(dir).getName() + "x"));
                                    node.getConnectedEdge(dir).setŃodesInPath(wayPoints);
                                } else {
                                    node.setConnectedEdge(dir, dummyNode.getConnectedEdge(Solver.invertDir(dir)));
                                }
                            }
                            break;
                        }
                        wayPoints.add(dummyNode);
                    }
                }
            }
        }
    }

    private void cleanGraph() {
        Vector<Node> newGraph = new Vector<Node>();
        for (Node node : graph) { // remove unnecessary nodes from graph
            if (node.getTargetWeight() != 0) {
                newGraph.add(node);
            }
            //
            for (int dir = 0; dir < 6; dir++) {
                if (node.getNeighborNode(dir) != null && node.getNeighborNode(dir).getTargetWeight() == 0) {
                    node.setConnectedEdge(dir, null);
                }
            }
            //
        }
        graph = newGraph;
    }

    public Vector<Node> solveGraph() {
        boolean graphSolved = false;
        int dummySolver = 2;
        boolean buildedBridge;
        while (!graphSolved) {
            buildedBridge = false;
            for (Node node : graph) {
                if (node.getTargetWeight() != node.getActualWeight()) {
                    // try every solver until one can be applied to build at least one bridge

                    // targetWeight == 1 && nFreeEdges == 1 (isolated node)
                    buildedBridge = SolverA.apply(node, edges);
                    if (buildedBridge == true) {
                        break;
                    }

                    // weightDifference == nPossibleBridges (all to be filled)
                    buildedBridge = SolverB.apply(node, edges);
                    if (buildedBridge == true) {
                        break;
                    }

                    // nFunnyEdges == nFreeEdges -1
                    buildedBridge = SolverC.apply(node, edges);
                    if (buildedBridge == true) {
                        break;
                    }

                    // targetWeight > (nConnectedEdges * 2) - 2
                    buildedBridge = SolverD.apply(node, edges);
                    if (buildedBridge == true) {
                        break;
                    }

                    // check for edges that contain new bridges in all plausible solutions
                    buildedBridge = SolverE.apply(node, edges);
                }
            }
            // check if graph is solved
            graphSolved = true;
            for (Node node : graph) {
                if (node.getActualWeight() != node.getTargetWeight()) {
                    graphSolved = false;
                    break;
                }
            }
            if (graphSolved == true) {
                System.out.println(" ");
                System.out.println("Solved :)");
                break;
            }
            if (dummySolver == 0) {
                System.out.println(" ");
                System.out.println("Not solved :(");
                break;
            }
            if (buildedBridge == false) {
                System.out.println("No brige builded during last iteration ...");
                dummySolver--;
            } else {
                if (dummySolver < 2) {
                    dummySolver = 2;
                }
            }
        }
        return graph;
    }

    public void plotGraph() {
        if (getnRowWeightMatrix() < 2 || getnColWeightMatrix() < 2) {
            throw new IllegalStateException(
                    "Unable to plot graph. NRowWeightMatrix and nColweightMatrix must both be at least 2 but are "
                            + getnRowWeightMatrix() + " and " + getnColWeightMatrix() + "!");
        } else {
            int targetWeight;
            Node node;
            boolean oddRow;
            String midLayerA = "";
            String midLayerB = "";
            String midLayerC = "";
            Vector<Node> wayPointsV = new Vector<Node>();
            Vector<Node> wayPointsD = new Vector<Node>();
            Vector<Node> wayPointsI = new Vector<Node>();
            for (int row = 0; row < getnRowWeightMatrix(); row++) {
                if (row % 2 != 0) {
                    midLayerA = " ";
                    midLayerB = " ";
                    midLayerC = " ";
                    oddRow = true;
                    System.out.print("     ");
                } else {
                    oddRow = false;
                    midLayerA = " ";
                    midLayerB = " ";
                    midLayerC = " ";
                }
                ;
                for (int col = 0; col < getnColWeightMatrix(); col++) {
                    node = nodeMatrix[row][col];
                    targetWeight = node.getTargetWeight();
                    if (targetWeight < 10) {
                        System.out.print(" " + targetWeight);
                    } else {
                        System.out.print(targetWeight);
                    }

                    // dir 1|4
                    if (node.getConnectedEdge(4) != null && node.getConnectedEdge(4).getnBridges() == 1) {
                        System.out.print(ANSI_YELLOW + "  — — — " + ANSI_RESET);
                        if (node.getConnectedEdge(4).getName().contains("x")) {
                            for (Node wayPoint : node.getConnectedEdge(4).getNodesInPath()) {
                                wayPointsV.add(wayPoint);
                            }
                        }
                    } else if (node.getConnectedEdge(4) != null && node.getConnectedEdge(4).getnBridges() == 2) {
                        System.out.print(ANSI_RED + "  — — — " + ANSI_RESET);
                        if (node.getConnectedEdge(4).getName().contains("x")) {
                            for (Node wayPoint : node.getConnectedEdge(4).getNodesInPath()) {
                                wayPointsV.add(wayPoint);
                            }
                        }
                    } else if (wayPointsV.contains(node)) {
                        System.out.print(ANSI_BLUE + "  — — — " + ANSI_RESET);
                        wayPointsV.remove(node);
                    } else if (col != getnColWeightMatrix() - 1) {
                        System.out.print(ANSI_WHITE + "  — — — " + ANSI_RESET);
                    }

                    // dir 2|5
                    if (oddRow || col != 0) {
                        if (node.getConnectedEdge(2) != null && node.getConnectedEdge(2).getnBridges() == 1) {
                            midLayerA = midLayerA + ANSI_YELLOW + "   / " + ANSI_RESET;
                            midLayerB = midLayerB + ANSI_YELLOW + "  /  " + ANSI_RESET;
                            midLayerC = midLayerC + ANSI_YELLOW + " /   " + ANSI_RESET;
                            if (node.getConnectedEdge(2).getName().contains("x")) {
                                for (Node wayPoint : node.getConnectedEdge(2).getNodesInPath()) {
                                    wayPointsI.add(wayPoint);
                                }
                            }
                        } else if (node.getConnectedEdge(2) != null && node.getConnectedEdge(2).getnBridges() == 2) {
                            midLayerA = midLayerA + ANSI_RED + "   / " + ANSI_RESET;
                            midLayerB = midLayerB + ANSI_RED + "  /  " + ANSI_RESET;
                            midLayerC = midLayerC + ANSI_RED + " /   " + ANSI_RESET;
                            if (node.getConnectedEdge(2).getName().contains("x")) {
                                for (Node wayPoint : node.getConnectedEdge(2).getNodesInPath()) {
                                    wayPointsI.add(wayPoint);
                                }
                            }
                        } else if (wayPointsI.contains(node)) {
                            midLayerA = midLayerA + ANSI_BLUE + "   / " + ANSI_RESET;
                            midLayerB = midLayerB + ANSI_BLUE + "  /  " + ANSI_RESET;
                            midLayerC = midLayerC + ANSI_BLUE + " /   " + ANSI_RESET;
                            wayPointsI.remove(node);
                        } else if (row != getnRowWeightMatrix() - 1) {
                            midLayerA = midLayerA + ANSI_WHITE + "   / " + ANSI_RESET;
                            midLayerB = midLayerB + ANSI_WHITE + "  /  " + ANSI_RESET;
                            midLayerC = midLayerC + ANSI_WHITE + " /   " + ANSI_RESET;
                        }
                    }

                    // dir 0|3
                    if (node.getConnectedEdge(3) != null && node.getConnectedEdge(3).getnBridges() == 1) {
                        midLayerA = midLayerA + ANSI_YELLOW + " \\   " + ANSI_RESET;
                        midLayerB = midLayerB + ANSI_YELLOW + "  \\  " + ANSI_RESET;
                        midLayerC = midLayerC + ANSI_YELLOW + "   \\ " + ANSI_RESET;
                        if (node.getConnectedEdge(3).getName().contains("x")) {
                            for (Node wayPoint : node.getConnectedEdge(3).getNodesInPath()) {
                                wayPointsD.add(wayPoint);
                            }
                        }
                    } else if (node.getConnectedEdge(3) != null && node.getConnectedEdge(3).getnBridges() == 2) {
                        midLayerA = midLayerA + ANSI_RED + " \\   " + ANSI_RESET;
                        midLayerB = midLayerB + ANSI_RED + "  \\  " + ANSI_RESET;
                        midLayerC = midLayerC + ANSI_RED + "   \\ " + ANSI_RESET;
                        if (node.getConnectedEdge(3).getName().contains("x")) {
                            for (Node wayPoint : node.getConnectedEdge(3).getNodesInPath()) {
                                wayPointsD.add(wayPoint);
                            }
                        }
                    } else if (wayPointsD.contains(node)) {
                        midLayerA = midLayerA + ANSI_BLUE + " \\   " + ANSI_RESET;
                        midLayerB = midLayerB + ANSI_BLUE + "  \\  " + ANSI_RESET;
                        midLayerC = midLayerC + ANSI_BLUE + "   \\ " + ANSI_RESET;
                        wayPointsI.remove(node);
                    } else if (row != getnRowWeightMatrix() - 1 && !(oddRow && col == nColWeightMatrix - 1)) {
                        midLayerA = midLayerA + ANSI_WHITE + " \\   " + ANSI_RESET;
                        midLayerB = midLayerB + ANSI_WHITE + "  \\  " + ANSI_RESET;
                        midLayerC = midLayerC + ANSI_WHITE + "   \\ " + ANSI_RESET;
                    }
                }
                System.out.println("");
                if (oddRow) {
                    System.out.println(midLayerA);
                    System.out.println(midLayerB);
                    System.out.println(midLayerC);
                } else {
                    System.out.println(midLayerA);
                    System.out.println(midLayerB);
                    System.out.println(midLayerC);
                }
            }
            System.out.println("Legend:");
            System.out.println(ANSI_YELLOW + "####" + ANSI_RESET + " single bridge");
            System.out.println(ANSI_RED + "####" + ANSI_RESET + " double bridge");
            System.out.println(ANSI_BLUE + "####" + ANSI_RESET + " extended bridge");
            System.out.println("");
        }
    }
}