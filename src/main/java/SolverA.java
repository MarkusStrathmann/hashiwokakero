import java.util.Vector;

public class SolverA extends Solver {
    // constructor
    public SolverA() {
    };

    // targetWeight == 1 && nFreeEdges == 1 (isolated node)
    public static boolean apply(Node node, Vector<Edge> edges) {
        buildedBridge = false;
        if (node.getTargetWeight() == 1 && node.getNFreeEdges() == 1) {
            for (int dir = 0; dir < 6; dir++) {
                if (node.getConnectedEdge(dir) != null
                        && node.getConnectedEdge(dir).getNPossibleBridges() != 0) {
                    node.getConnectedEdge(dir).incrementNBridges();
                    buildedBridge = true;
                    node.getConnectedEdge(dir).blockCrossingEdges(edges);
                    buildInfo = buildInfo(node, dir);
                    System.out.println(buildInfo
                            + " with value 1: targetWeight == 1 && nFreeEdges == 1 (isolated node)");
                    buildedBridge = true;
                    break;
                }
            }
        }
        return buildedBridge;
    }
}
