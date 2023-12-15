import java.util.Vector;

public class SolverE extends AbstractSolver {
    // constructor
    public SolverE() {
    };

    // search for common new bridge in all plausible solutions
    public static boolean apply(Node node, Vector<Edge> edges) {
        buildedBridge = false;
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

        // get all possible patterns to reach targetWeight of node
        for (int dir0 = nPossibleBridgesPerDirection[0]; dir0 > -1; dir0--) {
            for (int dir1 = nPossibleBridgesPerDirection[1]; dir1 > -1; dir1--) {
                for (int dir2 = nPossibleBridgesPerDirection[2]; dir2 > -1; dir2--) {
                    for (int dir3 = nPossibleBridgesPerDirection[3]; dir3 > -1; dir3--) {
                        for (int dir4 = nPossibleBridgesPerDirection[4]; dir4 > -1; dir4--) {
                            for (int dir5 = nPossibleBridgesPerDirection[5]; dir5 > -1; dir5--) {
                                if ((dir0 + dir1 + dir2 + dir3 + dir4 + dir5) == node.getWeightDifference()) {
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
        if (plausibleSolutions.size() != 0) {
            for (int dir = 0; dir < 6; dir++) {
                boolean allSolutionsBuildBridge = true;
                for (int[] plausibleSolution : plausibleSolutions) {
                    if (plausibleSolution[dir] == 0) {
                        allSolutionsBuildBridge = false;
                        break;
                    }
                }
                if (allSolutionsBuildBridge == true) {
                    node.getConnectedEdge(dir).incrementNBridges();
                    buildedBridge = true;
                    node.getConnectedEdge(dir).blockCrossingEdges(edges);
                    buildInfo = buildInfo(node, dir);
                    System.out.println(buildInfo
                            + " with value 1: (common new bridge in all plausible solutions)");
                }
            }
        }

        return buildedBridge;
    }
}
