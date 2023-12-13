import java.util.Vector;

public class SolverD extends AbstractSolver {
    // constructor
    public SolverD() {
    };

    // targetWeight > (nConnectedEdges * 2) - 2
    public static boolean apply(Node node, Vector<Edge> edges) {
        buildedBridge = false;
         if (node.getTargetWeight() > ((node.getNConnectedEdges() * 2) - 2)) {
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
        return buildedBridge;
    }
}
