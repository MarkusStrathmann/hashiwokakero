package src.java;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class Graph {
    // constructor
    public Graph(int[][] weightMatrix) {
        this.weightMatrix = weightMatrix;
        this.nRowWeightMatrix = weightMatrix.length;
        this.nColWeightMatrix = weightMatrix[0].length;
        this.nodeMatrix = new Node[nRowWeightMatrix][nColWeightMatrix];
        initializeGraph();
        updateEdges();
        upddateNeighbors();
        extendEdges();
        cleanGraph();
    }

    private int nodeNumber = 0;
    private int edgeNumber = 0;
    private int[][] weightMatrix;
    private Node[][] nodeMatrix;
    private int nRowWeightMatrix;
    private int nColWeightMatrix;
    private Vector<Node> graph = new Vector<Node>();
    private Vector<Edge> edges = new Vector<Edge>();

    // colors
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_WHITE = "\u001B[90m";

    private void initializeGraph() {
        if (nRowWeightMatrix <= 1 || nColWeightMatrix <= 1) {
            throw new IllegalStateException(
                    "Unable to initialize graph. NRowWeightMatrix and nColweightMatrix must both be at least 2 but are "
                            + nRowWeightMatrix + " and " + nColWeightMatrix + "!");
        } else {
            for (int row = 0; row < nRowWeightMatrix; row++) {
                for (int col = 0; col < nColWeightMatrix; col++) {
                    nodeMatrix[row][col] = new Node();
                    nodeMatrix[row][col].setName("N-" + String.format("%03d", ++nodeNumber));
                }
            }
            boolean indented;

            for (int row = 0; row < nRowWeightMatrix; row++) {
                for (int col = 0; col < nColWeightMatrix; col++) {
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

    private Edge[] connectEdges(int row, int col, boolean indented) {
        Edge[] edges = new Edge[6];
        int[][][] nodeTypes = new int[][][] { // row,col (0=first/1=last) targetNode row,col (six times)
                { { 0, 0, 0 }, { 0 }, { 0 }, { 0 }, { 1, 0 }, { 0, 1 }, { 0 } }, // row=first, col=first, indented=false
                { { 0, 1, 0 }, { 0 }, { 0, -1, 4 }, { 1, -1 }, { 1, 0 }, { 0 }, { 0 } }, // row=first, col=last,
                                                                                         // indented=false
                { { 1, 0, 1 }, { -1, 0, 3 }, { 0 }, { 0 }, { 0 }, { 0, 1 }, { -1, 1, 2 } }, // row=last, col=first,
                                                                                            // indented=true
                { { 1, 0, 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0, 1 }, { -1, 0, 2 } }, // row=last, col=first,
                                                                                     // indented=false
                { { 1, 1, 1 }, { -1, 0, 3 }, { 0, -1, 4 }, { 0 }, { 0 }, { 0 }, { 0 } }, // row=last, col=last,
                                                                                         // indented=true
                { { 1, 1, 0 }, { -1, -1, 3 }, { 0, -1, 4 }, { 0 }, { 0 }, { 0 }, { -1, 0, 2 } }, // row=last, col=last,
                // indented=false
                { { 0, -1, 0 }, { 0 }, { 0, -1, 4 }, { 1, -1 }, { 1, 0 }, { 0, 1 }, { 0 } }, // row=first, col=any,
                                                                                             // indented=false
                { { 1, -1, 1 }, { -1, 0, 3 }, { 0, -1, 4 }, { 0 }, { 0 }, { 0, 1 }, { -1, 1, 2 } }, // row=last,
                                                                                                    // col=any,
                                                                                                    // indented=true
                { { 1, -1, 0 }, { -1, -1, 3 }, { 0, -1, 4 }, { 0 }, { 0 }, { 0, 1 }, { -1, 0, 2 } }, // row=last,
                                                                                                     // col=any,
                                                                                                     // indented=false
                { { -1, 0, 1 }, { -1, 0, 3 }, { 0 }, { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1, 2 } }, // row=any,
                                                                                                   // col=first,
                                                                                                   // indented=true
                { { -1, 0, 0 }, { 0 }, { 0 }, { 0 }, { 1, 0 }, { 0, 1 }, { -1, 0, 2 } }, // row=any, col=first,
                                                                                         // indented=false
                { { -1, 1, 1 }, { -1, 0, 3 }, { 0, -1, 4 }, { 1, 0 }, { 0 }, { 0 }, { 0 } }, // row=any, col=last,
                                                                                             // indented=true
                { { -1, 1, 0 }, { -1, -1, 3 }, { 0, -1, 4 }, { 1, -1 }, { 1, 0 }, { 0 }, { -1, 0, 2 } }, // row=any,
                                                                                                         // col=last,
                                                                                                         // indented=false
                { { -1, -1, 1 }, { -1, 0, 3 }, { 0, -1, 4 }, { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1, 2 } }, // row=any,
                                                                                                           // col=any,
                                                                                                           // indented=true
                { { -1, -1, 0 }, { -1, -1, 3 }, { 0, -1, 4 }, { 1, -1 }, { 1, 0 }, { 0, 1 }, { -1, 0, 2 } } // row=any,
                                                                                                            // col=any,
                                                                                                            // indented=false
        };
        int[] nodeType = new int[3];
        if (row == 0) {
            nodeType[0] = 0;
        } else if (row == nRowWeightMatrix - 1) {
            nodeType[0] = 1;
        } else {
            nodeType[0] = -1;
        }
        if (col == 0) {
            nodeType[1] = 0;
        } else if (col == nColWeightMatrix - 1) {
            nodeType[1] = 1;
        } else {
            nodeType[1] = -1;
        }
        if (indented == true) {
            nodeType[2] = 1;
        } else {
            nodeType[2] = 0;
        }
        for (int[][] element : nodeTypes) {
            if (Arrays.equals(element[0], nodeType)) {
                for (int dir = 0; dir < 6; dir++) {
                    if (element[dir + 1].length == 1) {
                        edges[dir] = null;
                    } else if (element[dir + 1].length == 2) {
                        edges[dir] = new Edge(nodeMatrix[row][col],
                                nodeMatrix[row + element[dir + 1][0]][col + element[dir + 1][1]],
                                "E-" + String.format("%03d", ++edgeNumber));
                    } else {
                        edges[dir] = nodeMatrix[row + element[dir + 1][0]][col + element[dir + 1][1]]
                                .getConnectedEdge(element[dir + 1][2]);
                    }
                }
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
                                    node.setConnectedEdge(dir, dummyNode.getConnectedEdge(invertDir(dir)));
                                }
                            }
                            break;
                        }
                        wayPoints.add(dummyNode);
                    }
                }
            }
        }
        updateEdges();
        upddateNeighbors();
    }

    private int invertDir(int direction) {
        int invertedDir;
        if (direction >= 0 && direction < 3) {
            invertedDir = direction + 3;
        } else if (direction >= 3 && direction <= 5) {
            invertedDir = direction - 3;
        } else {
            throw new IllegalArgumentException("Argument must be between 0 and 5 but is " + direction + "!");
        }
        return invertedDir;
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
        updateEdges();
        upddateNeighbors();
    }

    public void solveGraph() {
        int targetWeight;
        int actualWeight;
        int weightDifference;
        int nConnectedEdges;
        int nFreeEdges;
        int nPossibleBridges;
        int nFunnyEdges;
        boolean graphSolved = false;
        int dummySolver = 2;
        String buildInfo;
        boolean buildedBridge;
        boolean deadEnd = false;
        while (!graphSolved) {
            buildedBridge = false;
            for (Node node : graph) {
                targetWeight = node.getTargetWeight();
                actualWeight = node.getActualWeight();
                weightDifference = node.getWeightDifference();
                nFreeEdges = node.getNFreeEdges();
                nConnectedEdges = node.getNConnectedEdges();
                nPossibleBridges = node.getNPossibleBridges();
                nFunnyEdges = node.getNFunnyEdges();
                if (targetWeight != actualWeight) {

                    // targetWeight == 1 && nFreeEdges == 1 (isolated node)
                    if (targetWeight == 1 && nFreeEdges == 1) {
                        for (int dir = 0; dir < 6; dir++) {
                            if (node.getConnectedEdge(dir) != null
                                    && node.getConnectedEdge(dir).getNPossibleBridges() != 0) {
                                node.getConnectedEdge(dir).incrementNBridges();
                                buildedBridge = true;
                                node.getConnectedEdge(dir).blockCrossingEdges(edges);
                                buildInfo = buildInfo(node, dir);
                                System.out.println(buildInfo
                                        + " with value 1: targetWeight == 1 && nFreeEdges == 1 (isolated node)");
                                break;
                            }
                        }
                    }
                    // weightDifference == nPossibleBridges (all to be filled)
                    else if (nPossibleBridges == weightDifference) {
                        for (int dir = 0; dir < 6; dir++) {
                            int repetitions = 2;
                            while (repetitions > 0) {
                                if (node.getConnectedEdge(dir) != null
                                        && node.getConnectedEdge(dir).getNPossibleBridges() != 0) {
                                    if (node.getNeighborNode(dir).getWeightDifference() > 0) {
                                        node.getConnectedEdge(dir).incrementNBridges();
                                        buildedBridge = true;
                                        node.getConnectedEdge(dir).blockCrossingEdges(edges);
                                        buildInfo = buildInfo(node, dir);
                                        System.out.println(
                                                buildInfo
                                                        + " with value 1: weightDifference == nPossibleBridges (all to be filled)");
                                    }
                                }
                                repetitions--;
                            }
                        }
                    }

                    // nFunnyEdges == nFreeEdges -1
                    else if (nFunnyEdges == (nFreeEdges - 1) && nFunnyEdges != 0) {
                        for (int dir = 0; dir < 6; dir++) {
                            if (node.getConnectedEdge(dir) != null
                                    && node.getConnectedEdge(dir).getNPossibleBridges() == 2) {
                                node.getConnectedEdge(dir).incrementNBridges();
                                buildedBridge = true;
                                node.getConnectedEdge(dir).blockCrossingEdges(edges);
                                buildInfo = buildInfo(node, dir);
                                System.out.println(buildInfo
                                        + " with value 1: nFunnyEdges == nFreeEdges -1 (filled not funny edge)");
                            }
                        }
                    }

                    // targetWeight > (nConnectedEdges * 2) - 2
                    else if (targetWeight > ((nConnectedEdges * 2) - 2)) {
                        for (int dir = 0; dir < 6; dir++) {
                            if (node.getConnectedEdge(dir) != null
                                    && node.getConnectedEdge(dir).getnBridges() == 0) {
                                node.getConnectedEdge(dir).incrementNBridges();
                                buildedBridge = true;
                                node.getConnectedEdge(dir).blockCrossingEdges(edges);
                                buildInfo = buildInfo(node, dir);
                                System.out.println(buildInfo
                                        + " with value 1: targetWeight > (nConnectedEdges * 2) - 2 (every edge filled once)");
                            }
                        }
                    }

                    // common new bridge in all plausible solutions
                    else if (deadEnd) { // deadEnd is a flag for an iteration with no new bridges builded
                        Vector<int[]> trialPatterns = new Vector<int[]>();
                        Vector<int[]> plausibleSolutions = new Vector<int[]>();

                        // get n possible bridges per direction
                        int[] nPossibleBridgesPerDirection = new int[6];
                        for (int dir = 0; dir < 6; dir++) {
                            if (node.getConnectedEdge(dir) != null) {
                                if (node.getConnectedEdge(dir).getNPossibleBridges() > 0) {
                                    if (node.getConnectedEdge(dir).getNPossibleBridges() == 1) {
                                        nPossibleBridgesPerDirection[dir] = 1;

                                    } else if (node.getConnectedEdge(dir).getNPossibleBridges() == 2) {
                                        nPossibleBridgesPerDirection[dir] = 2;
                                    }
                                } else {
                                    nPossibleBridgesPerDirection[dir] = 0;
                                }
                            } else {
                                nPossibleBridgesPerDirection[dir] = 0;
                            }
                        }

                        for (int dir0 = nPossibleBridgesPerDirection[0]; dir0 > -1; dir0--) {
                            for (int dir1 = nPossibleBridgesPerDirection[1]; dir1 > -1; dir1--) {
                                for (int dir2 = nPossibleBridgesPerDirection[2]; dir2 > -1; dir2--) {
                                    for (int dir3 = nPossibleBridgesPerDirection[3]; dir3 > -1; dir3--) {
                                        for (int dir4 = nPossibleBridgesPerDirection[4]; dir4 > -1; dir4--) {
                                            for (int dir5 = nPossibleBridgesPerDirection[5]; dir5 > -1; dir5--) {
                                                if ((dir0 + dir1 + dir2 + dir3 + dir4 + dir5) == weightDifference) {
                                                    trialPatterns.add(new int[] { dir0, dir1, dir2, dir3, dir4, dir5 });
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // check plausibility
                        boolean patternIsPlausible;
                        Node neighborNode;
                        int nSurroundingBridges;
                        Edge edgeOfNeighborNode;
                        for (int[] pattern : trialPatterns) {
                            patternIsPlausible = true;
                            for (int dir = 0; dir < 6; dir++) {
                                if (node.getConnectedEdge(dir) != null) {
                                    neighborNode = node.getNeighborNode(dir);
                                    nSurroundingBridges = 0;
                                    for (int innerDir = 0; innerDir < 6; innerDir++) {
                                        edgeOfNeighborNode = neighborNode.getConnectedEdge(innerDir);
                                        if (edgeOfNeighborNode != null) {
                                            if (innerDir != invertDir(dir)) {
                                                nSurroundingBridges += edgeOfNeighborNode.getnBridges();
                                            } else {
                                                nSurroundingBridges += edgeOfNeighborNode.getnBridges()
                                                        + pattern[dir];
                                            }
                                        }
                                    }
                                    if (neighborNode.getTargetWeight() < nSurroundingBridges) {
                                        patternIsPlausible = false;
                                    }
                                }
                            }
                            if (patternIsPlausible == true) {
                                plausibleSolutions.add(pattern);
                            }
                        }

                        // check for edges that contain new bridges in all plausible solutions
                        for (int dir = 0; dir < 6; dir++) {
                            boolean allSolutionsBuildBridge = true;
                            for (int[] plausibleSolution : plausibleSolutions) {
                                if (plausibleSolution[dir] == 0) {
                                    allSolutionsBuildBridge = false;
                                    break;
                                }
                            }
                            if (allSolutionsBuildBridge == true) {
                                System.out.println(node.getName() + " " + dir);
                                node.getConnectedEdge(dir).incrementNBridges();
                                buildedBridge = true;
                                node.getConnectedEdge(dir).blockCrossingEdges(edges);
                                buildInfo = buildInfo(node, dir);
                                System.out.println(buildInfo
                                        + " with value 1: (common new bridge in all plausible solutions)");
                            }
                        }

                    }

                }
            }

            // check if solved
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
                deadEnd = true;
                dummySolver--;
            } else {
                deadEnd = false;
                if (dummySolver < 2) {
                    dummySolver = 2;
                }
            }
        }
    }

    private String buildInfo(Node node, int direction) {
        String builInfo = " -> builded bridge on edge " + node.getConnectedEdge(direction).getName() + " ("
                + node.getConnectedEdge(direction).getConnectedNodes()[0].getName() + " to "
                + node.getConnectedEdge(direction).getConnectedNodes()[1].getName() + ")";
        return builInfo;
    }

    public void plotGraph() {
        if (nRowWeightMatrix <= 0 || nColWeightMatrix <= 0) {
            throw new IllegalStateException(
                    "Unable to plot graph. NRowWeightMatrix and nColweightMatrix must both be at least 2 but are "
                            + nRowWeightMatrix + " and " + nColWeightMatrix + "!");
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
            for (int row = 0; row < nRowWeightMatrix; row++) {
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
                for (int col = 0; col < nColWeightMatrix; col++) {
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
                    } else if (col != nColWeightMatrix - 1) {
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
                        } else if (row != nRowWeightMatrix - 1) {
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
                    } else if (row != nRowWeightMatrix - 1 && !(oddRow && col == nColWeightMatrix - 1)) {
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