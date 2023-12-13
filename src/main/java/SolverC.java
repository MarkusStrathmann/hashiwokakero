import java.util.Vector;

public class SolverC extends AbstractSolver {
    // constructor
    public SolverC() {
    };

    // nFunnyEdges == nFreeEdges -1
    public static boolean apply(Node node, Vector<Edge> edges) {
        buildedBridge = false;
        if (node.getNFunnyEdges() == (node.getNFreeEdges() - 1) && node.getNFunnyEdges() != 0) {
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
        return buildedBridge;
    }
}
