import java.util.Vector;

public class SolverB extends Solver {
    // constructor
    public SolverB() {
    };

    // weightDifference == nPossibleBridges (all to be filled)
    public static boolean apply(Node node, Vector<Edge> edges) {
        buildedBridge = false;
        if (node.getNPossibleBridges() == node.getWeightDifference()) {
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
        return buildedBridge;
    }
}
